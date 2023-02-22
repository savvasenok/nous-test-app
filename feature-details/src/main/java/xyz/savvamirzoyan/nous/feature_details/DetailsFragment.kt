package xyz.savvamirzoyan.nous.feature_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import xyz.savvamirzoyan.nous.feature_details.databinding.FragmentDetailsBinding
import xyz.savvamirzoyan.nous.shared_app.CoreFragment
import xyz.savvamirzoyan.nous.shared_app.load
import xyz.savvamirzoyan.nous.shared_app.setText
import javax.inject.Inject

@AndroidEntryPoint
class DetailsFragment : CoreFragment<FragmentDetailsBinding>() {

    @Inject
    lateinit var viewModelFactory: NewsItemDetailsViewModel.Factory
    private val viewModel by viewModels<NewsItemDetailsViewModel>(factoryProducer = {
        NewsItemDetailsViewModel.provideFactory(
            viewModelFactory,
            (requireArguments().getString(KEY_NEWS_ITEM_ID))?.toLong() ?: -1
        )
    })

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupFlowListeners()
        setupViews()
    }

    private fun setupViews() {
        binding.toolbar.setNavigationOnClickListener { findNavController().navigateUp() }

        binding.fabSendEmail.setOnClickListener { viewModel.onShareViaEmailButtonClick() }
    }

    private fun setupFlowListeners() {

        setupDefaultFlows(viewModel)

        collect(viewModel.detailsFlow) {
            binding.ivPicture.load(it.pictureUrl)
            binding.tvTitle.setText(it.title)
            binding.tvDescription.setText(it.description)
        }
    }

    companion object {
        private const val KEY_NEWS_ITEM_ID = "KEY_NEWS_ITEM_ID"
    }
}