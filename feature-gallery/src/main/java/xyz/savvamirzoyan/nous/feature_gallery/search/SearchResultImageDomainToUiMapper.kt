package xyz.savvamirzoyan.nous.feature_gallery.search

import xyz.savvamirzoyan.nous.domain_gallery_manager.SearchResultImageDomain
import xyz.savvamirzoyan.nous.shared_app.ui_state.TextState
import javax.inject.Inject

interface SearchResultImageDomainToUiMapper {

    fun map(model: SearchResultImageDomain): SearchResultImageUi

    class Base @Inject constructor() : SearchResultImageDomainToUiMapper {

        override fun map(model: SearchResultImageDomain) = SearchResultImageUi(
            pictureUrl = model.pictureUrl,
            title = TextState(model.title),
            description = TextState(model.description),
            titleSearchResultIndexStart = model.titleSearchResultRange?.first ?: 0,
            titleSearchResultIndexEnd = model.titleSearchResultRange?.last ?: 0,
            descriptionSearchResultIndexStart = model.descriptionSearchResultRange?.first ?: 0,
            descriptionSearchResultIndexEnd = model.descriptionSearchResultRange?.last ?: 0,
        )
    }
}
