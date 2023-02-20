package xyz.savvamirzoyan.nous.domain_gallery_manager

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import xyz.savvamirzoyan.nous.core.ResultWrap
import javax.inject.Inject

interface GalleryManagerInteractor {

    val imagesFlow: Flow<ResultWrap<List<GalleryImageDomain>>>
    val searchResultImagesFlow: Flow<ResultWrap<List<SearchResultImageDomain>>>

    suspend fun requestImages()
    suspend fun search(searchRequest: String)

    class Base @Inject constructor(
        private val galleryRepository: GalleryRepository
    ) : GalleryManagerInteractor {

        private val _currentSearchRequestFlow = MutableStateFlow("")

        private val _imagesFlow =
            MutableStateFlow<ResultWrap<List<GalleryImageDomain>>>(ResultWrap.Success(emptyList()))
        override val imagesFlow: Flow<ResultWrap<List<GalleryImageDomain>>> = _imagesFlow

        override val searchResultImagesFlow: Flow<ResultWrap<List<SearchResultImageDomain>>> = combine(
            _currentSearchRequestFlow, imagesFlow
        ) { searchRequest: String, images: ResultWrap<List<GalleryImageDomain>> ->

            if (searchRequest.isBlank()) ResultWrap.Success(listOf())
            else when (images) {
                is ResultWrap.Failure -> ResultWrap.Failure(images.error)
                is ResultWrap.Success -> images.data
                    .filter { image ->
                        image.title?.lowercase()?.contains(searchRequest.lowercase()) == true
                                || image.description?.lowercase()?.contains(searchRequest.lowercase()) == true
                    }
                    .map { image ->
                        val titleSearchResultStartIndex =
                            image.title?.indexOf(searchRequest, ignoreCase = true) ?: -1
                        val titleSearchResultRange: IntRange? =
                            if (titleSearchResultStartIndex == -1) null
                            else titleSearchResultStartIndex..titleSearchResultStartIndex + searchRequest.length

                        val descriptionSearchResultStartIndex =
                            image.description?.indexOf(searchRequest, ignoreCase = true) ?: -1
                        val descriptionSearchResultRange: IntRange? =
                            if (descriptionSearchResultStartIndex == -1) null
                            else descriptionSearchResultStartIndex..descriptionSearchResultStartIndex + searchRequest.length

                        SearchResultImageDomain(
                            id = image.id,
                            pictureUrl = image.pictureUrl ?: "", // no particular handle of nullable values
                            title = image.title ?: "",
                            description = image.description ?: "",
                            titleSearchResultRange = titleSearchResultRange,
                            descriptionSearchResultRange = descriptionSearchResultRange
                        )
                    }
                    .let { ResultWrap.Success(it) }
            }
        }.flowOn(Dispatchers.Default)

        override suspend fun requestImages() = galleryRepository.fetchImages().let { _imagesFlow.emit(it) }
        override suspend fun search(searchRequest: String) = _currentSearchRequestFlow.emit(searchRequest)
    }
}