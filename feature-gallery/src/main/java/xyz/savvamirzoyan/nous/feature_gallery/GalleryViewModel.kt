package xyz.savvamirzoyan.nous.feature_gallery

import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.launch
import xyz.savvamirzoyan.nous.core.Model
import xyz.savvamirzoyan.nous.core.ResultWrap
import xyz.savvamirzoyan.nous.domain_gallery_manager.GalleryManagerInteractor
import xyz.savvamirzoyan.nous.feature_gallery.gallery.GalleryImageDomainToUiMapper
import xyz.savvamirzoyan.nous.feature_gallery.search.SearchResultImageDomainToUiMapper
import xyz.savvamirzoyan.nous.feature_gallery.search.SearchResultImageUi
import xyz.savvamirzoyan.nous.shared_app.CoreViewModel
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val interactor: GalleryManagerInteractor,
    private val galleryImageDomainToUiMapper: GalleryImageDomainToUiMapper,
    private val searchResultImageDomainToUiMapper: SearchResultImageDomainToUiMapper
) : CoreViewModel() {

    init {
        onRefresh()
    }

    private val _galleryNoDataFlow = MutableSharedFlow<Unit>(replay = 1)
    val galleryImagesFlow: Flow<List<Model.Ui>> = merge(
        _galleryNoDataFlow.map { listOf(NoDataUi(isTryAgainButtonEnabled = false)) },
        interactor.imagesFlow
            .map { resultWrap ->
                when (resultWrap) {
                    is ResultWrap.Success -> resultWrap.data.map { galleryImageDomainToUiMapper.map(it) }
                    is ResultWrap.Failure -> listOf(NoDataUi(isTryAgainButtonEnabled = true))
                }
            }
    )
    val searchResultImagesFlow: Flow<List<SearchResultImageUi>> = interactor.searchResultImagesFlow
        .map { resultWrap ->
            when (resultWrap) {
                is ResultWrap.Success -> resultWrap.data.map { searchResultImageDomainToUiMapper.map(it) }
                is ResultWrap.Failure -> emptyList()
            }
        }

    private suspend fun requestImages() {
        interactor.requestImages()
    }

    fun onRefresh() {
        viewModelScope.launch { requestImages() }
    }

    fun onTryAgain() {
        viewModelScope.launch {
            _galleryNoDataFlow.emit(Unit)
            delay(3000)
            requestImages()
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
