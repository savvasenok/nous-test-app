package xyz.savvamirzoyan.nous.feature_gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import xyz.savvamirzoyan.nous.feature_gallery.databinding.FragmentGalleryBinding
import xyz.savvamirzoyan.nous.shared_app.CoreFragment
import xyz.savvamirzoyan.nous.shared_app.CoreRecyclerViewAdapter

@AndroidEntryPoint
class GalleryFragment : CoreFragment<FragmentGalleryBinding>() {

    private val viewModel: GalleryViewModel by viewModels()

    // lazy-init required, bc fragment is detached in this moment and its impossible to get VM
    private val adapterGalleryImages by lazy {
        CoreRecyclerViewAdapter<GalleryImageUi>(
            fingerprints = listOf(GalleryImageFingerprint(viewModel::onGalleryImageClick))
        )
    }

    private val adapterSearchResultImages by lazy {
        CoreRecyclerViewAdapter<SearchResultImageUi>(
            fingerprints = listOf(SearchResultImageFingerprint(viewModel::onSearchResultImageClick))
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentGalleryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupFlowListeners()
    }

    private fun setupViews() {
        binding.appbar.doOnPreDraw {
            binding.rvImages.setPadding(0, it.height, 0, it.height)
            binding.rvImages.scrollTo(0, 0)
        }
        binding.rvImages.adapter = adapterGalleryImages
        binding.rvSearchResultImages.adapter = adapterSearchResultImages

        binding.swipeRefreshLayout.setOnRefreshListener { viewModel.onRefresh() }

        binding.searchView.editText.addTextChangedListener { viewModel.onSearch(it?.toString() ?: "") }
    }

    private fun setupFlowListeners() {
        collect(viewModel.galleryImagesFlow) {
            adapterGalleryImages.update(it)
            binding.swipeRefreshLayout.isRefreshing = false
        }

        collect(viewModel.searchResultImagesFlow) {
            adapterSearchResultImages.update(it)
        }
    }
}