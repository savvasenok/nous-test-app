package xyz.savvamirzoyan.nous.feature_details

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import xyz.savvamirzoyan.nous.core.ErrorEntity
import xyz.savvamirzoyan.nous.core.ResultWrap
import xyz.savvamirzoyan.nous.domain_news_details.NewsDetailsInteractor
import xyz.savvamirzoyan.nous.shared_app.CoreViewModel
import xyz.savvamirzoyan.nous.shared_data.PictureDownloader
import xyz.savvamirzoyan.nous.shared_domain.NousNewsItemDomain

class NewsItemDetailsViewModel @AssistedInject constructor(
    @Assisted private val newsItemId: Long,
    private val interactor: NewsDetailsInteractor,
    private val pictureDownloader: PictureDownloader,
    private val newsItemDomainToDetailsUiMapper: NewsItemDomainToDetailsUiMapper
) : CoreViewModel() {

    private var newsItem: NousNewsItemDomain? = null

    private val _detailsFlow = MutableStateFlow<NewsItemDetailsUi?>(null)
    val detailsFlow = _detailsFlow.filterNotNull()

    init {
        viewModelScope.launch {
            interactor.getNewsItem(newsItemId)
                .also {
                    if (it is ResultWrap.Failure) {
                        // TODO: handle errors. maybe show toast or smth
                    }
                }
                .get()
                ?.let {
                    newsItem = it
                    newsItemDomainToDetailsUiMapper.map(it)
                }
                ?.let { _detailsFlow.emit(it) }
        }
    }

    fun onShareViaEmailButtonClick() {
        viewModelScope.launch {
            newsItem?.also { item ->
                pictureDownloader.saveTemporaryPicture(item.title, item.pictureUrl)
                    .onError { error ->
                        when (error) {
                            ErrorEntity.NoConnection -> viewModelScope.launch { showNoConnectionAlert() }
                            ErrorEntity.Unknown -> viewModelScope.launch { showUnknownErrorAlert() }
                            else -> {}
                        }
                    }
                    .map { uri -> buildEmailIntent(uri, item.title, item.description) }
                    .also { result -> result.get()?.let { intent -> navigate(intent) } }
            }
        }
    }

    private fun buildEmailIntent(uri: Uri, title: String, text: String) = Intent(Intent.ACTION_SEND)
        .apply {
            type = "application/octet-stream"
            putExtra(Intent.EXTRA_SUBJECT, title)
            putExtra(Intent.EXTRA_TEXT, text)
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

    @AssistedFactory
    interface Factory {
        fun create(newsItemId: Long): NewsItemDetailsViewModel
    }

    companion object {

        @Suppress("UNCHECKED_CAST")
        fun provideFactory(
            factory: Factory,
            newsItemId: Long
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>) = factory.create(newsItemId) as T
        }
    }
}