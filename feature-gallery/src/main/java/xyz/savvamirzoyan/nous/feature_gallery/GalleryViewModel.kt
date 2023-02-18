package xyz.savvamirzoyan.nous.feature_gallery

import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import xyz.savvamirzoyan.nous.domain_gallery_manager.GalleryManagerInteractor
import xyz.savvamirzoyan.nous.shared_app.CoreViewModel
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val interactor: GalleryManagerInteractor,
    private val galleryImageDomainToUiMapper: GalleryImageDomainToUiMapper,
    private val searchResultImageDomainToUiMapper: SearchResultImageDomainToUiMapper
) : CoreViewModel() {

    val galleryImagesFlow: Flow<List<GalleryImageUi>> = interactor.imagesFlow
        .map { list -> list.map { galleryImageDomainToUiMapper.map(it) } }
    val searchResultImagesFlow: Flow<List<SearchResultImageUi>> = interactor.searchResultImagesFlow
        .map { list -> list.map { searchResultImageDomainToUiMapper.map(it) } }

    fun onRefresh() {
        viewModelScope.launch {
            interactor.requestImages()
        }
    }

    fun onSearch(searchRequest: String) {
        viewModelScope.launch {
            interactor.search(searchRequest)
        }
    }

    fun onGalleryImageClick(clickedIndex: Int) {
        Log.d("SPAMEGGS", "clickedIndex: $clickedIndex")
    }

    fun onSearchResultImageClick(clickedIndex: Int) {
        Log.d("SPAMEGGS", "onSearchResultImageClick(clickedIndex:$clickedIndex)")
    }
}
