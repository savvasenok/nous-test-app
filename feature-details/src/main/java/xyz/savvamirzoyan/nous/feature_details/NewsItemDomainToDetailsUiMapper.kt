package xyz.savvamirzoyan.nous.feature_details

import xyz.savvamirzoyan.nous.shared_app.ui_state.TextState
import xyz.savvamirzoyan.nous.shared_domain.NousNewsItemDomain
import javax.inject.Inject

interface NewsItemDomainToDetailsUiMapper {

    fun map(model: NousNewsItemDomain): NewsItemDetailsUi

    class Base @Inject constructor() : NewsItemDomainToDetailsUiMapper {

        override fun map(model: NousNewsItemDomain) = NewsItemDetailsUi(
            pictureUrl = model.pictureUrl,
            title = TextState(model.title),
            description = TextState(model.description)
        )
    }
}
