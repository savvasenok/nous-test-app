package xyz.savvamirzoyan.nous.domain_news_details

import xyz.savvamirzoyan.nous.core.ResultWrap
import xyz.savvamirzoyan.nous.shared_domain.NousNewsItemDomain
import xyz.savvamirzoyan.nous.shared_domain.NousNewsRepository
import javax.inject.Inject

interface NewsDetailsInteractor {

    suspend fun getNewsItem(newsItemId: Long): ResultWrap<NousNewsItemDomain>

    class Base @Inject constructor(
        private val nousNewsRepository: NousNewsRepository
    ) : NewsDetailsInteractor {

        override suspend fun getNewsItem(newsItemId: Long): ResultWrap<NousNewsItemDomain> =
            nousNewsRepository.getNewsItem(newsItemId)
    }
}