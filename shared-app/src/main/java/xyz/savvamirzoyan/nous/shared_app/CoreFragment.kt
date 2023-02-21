package xyz.savvamirzoyan.nous.shared_app

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

abstract class CoreFragment<VB : ViewBinding> : Fragment() {

    protected lateinit var binding: VB
    protected val coreActivity: CoreActivity
        get() = (requireActivity() as CoreActivity)

    private fun coroutine(lifecycle: Lifecycle.State, function: suspend CoroutineScope.() -> Unit) {
        lifecycleScope.launch { viewLifecycleOwner.repeatOnLifecycle(lifecycle, function) }
    }

    private fun launchWhenCreated(function: suspend CoroutineScope.() -> Unit) {
        coroutine(Lifecycle.State.CREATED, function)
    }

    private fun launchWhenStarted(function: suspend CoroutineScope.() -> Unit) {
        coroutine(Lifecycle.State.STARTED, function)
    }

    protected fun <T> collectStarted(flow: Flow<T>, function: suspend CoroutineScope.(T) -> Unit) {
        launchWhenStarted {
            flow.collect {
                function(it)
            }
        }
    }

    protected fun <T> collect(flow: Flow<T>, function: suspend CoroutineScope.(T) -> Unit) {
        launchWhenCreated {
            flow.collect {
                function(it)
            }
        }
    }

    fun setupDefaultFlows(viewModel: CoreViewModel) {
        collect(viewModel.loadingFlow) { isLoading ->
            when (isLoading) {
                true -> coreActivity.startLoading()
                false -> coreActivity.stopLoading()
            }
        }

        collect(viewModel.navigationFromDeepLinkFlow) { data ->
            findNavController().navigate(request = data)
        }

        collect(viewModel.navigationIntentFlow) {
            startActivity(it)
        }
    }
}