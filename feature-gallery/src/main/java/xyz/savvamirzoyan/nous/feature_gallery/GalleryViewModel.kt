package xyz.savvamirzoyan.nous.feature_gallery

import android.util.Log
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import xyz.savvamirzoyan.nous.shared_app.CoreViewModel
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor() : CoreViewModel() {

    val galleryImagesFlow: Flow<List<GalleryImageUi>> = flow { }
    val searchResultImagesFlow: Flow<List<SearchResultImageUi>> = flow { }

    fun onRefresh() {

    }

    fun onGalleryImageClick(clickedIndex: Int) {
        Log.d("SPAMEGGS", "clickedIndex: $clickedIndex")
    }

    fun onSearchResultImageClick(clickedIndex: Int) {
        Log.d("SPAMEGGS", "onSearchResultImageClick(clickedIndex:$clickedIndex)")
    }
}
