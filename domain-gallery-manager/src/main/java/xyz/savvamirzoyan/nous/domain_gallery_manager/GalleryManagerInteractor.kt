package xyz.savvamirzoyan.nous.domain_gallery_manager

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

interface GalleryManagerInteractor {

    val imagesFlow: Flow<List<GalleryImageDomain>>
    val searchResultImagesFlow: Flow<List<SearchResultImageDomain>>

    suspend fun requestImages()
    suspend fun search(searchRequest: String)

    class Base @Inject constructor() : GalleryManagerInteractor {

        private val _currentSearchRequestFlow = MutableStateFlow("")

        override val imagesFlow: Flow<List<GalleryImageDomain>> = flow {}

        override val searchResultImagesFlow: Flow<List<SearchResultImageDomain>> = combine(
            _currentSearchRequestFlow, imagesFlow
        ) { searchRequest: String, images: List<GalleryImageDomain> ->

            if (searchRequest.isNotBlank()) {
                images
                    .filter { image ->
                        image.title.lowercase().contains(searchRequest.lowercase())
                                || image.description.lowercase().contains(searchRequest.lowercase())
                    }
                    .map { image ->
                        val titleSearchResultStartIndex = image.title.indexOf(searchRequest, ignoreCase = true)
                        val titleSearchResultRange: IntRange? = if (titleSearchResultStartIndex == -1) null
                        else titleSearchResultStartIndex..titleSearchResultStartIndex + searchRequest.length

                        val descriptionSearchResultStartIndex =
                            image.description.indexOf(searchRequest, ignoreCase = true)
                        val descriptionSearchResultRange: IntRange? =
                            if (descriptionSearchResultStartIndex == -1) null
                            else descriptionSearchResultStartIndex..descriptionSearchResultStartIndex + searchRequest.length

                        SearchResultImageDomain(
                            id = image.id,
                            pictureUrl = image.pictureUrl,
                            title = image.title,
                            description = image.description,
                            titleSearchResultRange = titleSearchResultRange,
                            descriptionSearchResultRange = descriptionSearchResultRange
                        )
                    }
            } else emptyList()
        }.flowOn(Dispatchers.Default)

        override suspend fun requestImages() = Unit // TODO: send request to data layer
        override suspend fun search(searchRequest: String) = _currentSearchRequestFlow.emit(searchRequest)
    }
}