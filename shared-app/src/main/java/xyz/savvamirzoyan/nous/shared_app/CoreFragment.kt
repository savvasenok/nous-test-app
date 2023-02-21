package xyz.savvamirzoyan.nous.shared_app

import android.app.AlertDialog
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

abstract class CoreFragment<VB : ViewBinding> : Fragment() {

    private val defaultNavOptions by lazy {
        NavOptions.Builder()
            .setEnterAnim(com.google.android.material.R.anim.abc_fade_in)
            .setExitAnim(com.google.android.material.R.anim.abc_fade_out)
            .setPopExitAnim(com.google.android.material.R.anim.abc_fade_out)
            .setPopEnterAnim(com.google.android.material.R.anim.abc_fade_in)
            .build()
    }

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
            findNavController().navigate(request = data, navOptions = defaultNavOptions)
        }

        collect(viewModel.navigationIntentFlow) {
            startActivity(it)
        }

        collect(viewModel.alertDataFlow) {
            AlertDialog.Builder(context)
                .setTitle(it.first.getString(requireContext()))
                .setMessage(it.second.getString(requireContext()))
                .setPositiveButton(android.R.string.ok) { p0, _ -> p0?.dismiss() }
                .show()
        }
    }

    fun setupFragmentWithTopInsects() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            // Apply the insets as a margin to the view. Here the system is setting
            // only the bottom, left, and right dimensions, but apply whichever insets are
            // appropriate to your layout. You can also update the view padding
            // if that's more appropriate.
            v.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                topMargin = insets.top
            }

            // Return CONSUMED if you don't want want the window insets to keep being
            // passed down to descendant views.
            WindowInsetsCompat.CONSUMED
        }
    }
}