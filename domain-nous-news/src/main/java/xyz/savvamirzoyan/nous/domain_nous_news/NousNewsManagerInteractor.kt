package xyz.savvamirzoyan.nous.domain_nous_news

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import xyz.savvamirzoyan.nous.core.ResultWrap
import xyz.savvamirzoyan.nous.shared_domain.NousNewsItemDomain
import xyz.savvamirzoyan.nous.shared_domain.NousNewsRepository
import javax.inject.Inject

interface NousNewsManagerInteractor {

    val newsFlow: Flow<ResultWrap<List<NousNewsItemDomain>>>
    val searchResultNewsFlow: Flow<ResultWrap<List<SearchResultNewsItemDomain>>>

    suspend fun requestNews()
    suspend fun search(searchRequest: String)
    suspend fun getNewsIdAtIndex(index: Int): Long
    suspend fun getSearchResultNewsIdAtIndex(index: Int): Long

    class Base @Inject constructor(
        private val galleryRepository: NousNewsRepository
    ) : NousNewsManagerInteractor {

        private val _currentSearchRequestFlow = MutableStateFlow("")

        private val _imagesFlow =
            MutableStateFlow<ResultWrap<List<NousNewsItemDomain>>>(ResultWrap.Success(emptyList()))
        override val newsFlow: Flow<ResultWrap<List<NousNewsItemDomain>>> = _imagesFlow

        override val searchResultNewsFlow: Flow<ResultWrap<List<SearchResultNewsItemDomain>>> = combine(
            _currentSearchRequestFlow, newsFlow
        ) { searchRequest: String, images: ResultWrap<List<NousNewsItemDomain>> ->

            if (searchRequest.isBlank()) ResultWrap.Success(listOf())
            else when (images) {
                is ResultWrap.Failure -> ResultWrap.Failure(images.error)
                is ResultWrap.Success -> images.data
                    .filter { image ->
                        image.title.lowercase().contains(searchRequest.lowercase())
                                || image.description.lowercase().contains(searchRequest.lowercase())
                    }
                    .map { image ->
                        val titleSearchResultStartIndex =
                            image.title.indexOf(searchRequest, ignoreCase = true)
                        val titleSearchResultRange: IntRange? =
                            if (titleSearchResultStartIndex == -1) null
                            else titleSearchResultStartIndex..titleSearchResultStartIndex + searchRequest.length

                        val descriptionSearchResultStartIndex =
                            image.description.indexOf(searchRequest, ignoreCase = true)
                        val descriptionSearchResultRange: IntRange? =
                            if (descriptionSearchResultStartIndex == -1) null
                            else descriptionSearchResultStartIndex..descriptionSearchResultStartIndex + searchRequest.length

                        SearchResultNewsItemDomain(
                            id = image.id,
                            pictureUrl = image.pictureUrl,
                            title = image.title,
                            description = image.description,
                            titleSearchResultRange = titleSearchResultRange,
                            descriptionSearchResultRange = descriptionSearchResultRange
                        )
                    }
                    .let { ResultWrap.Success(it) }
            }
        }.flowOn(Dispatchers.Default)

        override suspend fun requestNews() = galleryRepository.fetchNews().let { _imagesFlow.emit(it) }
        override suspend fun search(searchRequest: String) = _currentSearchRequestFlow.emit(searchRequest)

        override suspend fun getNewsIdAtIndex(index: Int): Long = newsFlow.firstOrNull()
            ?.get()
            ?.get(index)
            ?.id ?: -1

        override suspend fun getSearchResultNewsIdAtIndex(index: Int): Long = searchResultNewsFlow.firstOrNull()
            ?.get()
            ?.get(index)
            ?.id ?: -1
    }
}