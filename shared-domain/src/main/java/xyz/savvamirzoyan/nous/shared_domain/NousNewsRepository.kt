package xyz.savvamirzoyan.nous.shared_domain

import xyz.savvamirzoyan.nous.core.ResultWrap

interface NousNewsRepository {

    suspend fun fetchNews(): ResultWrap<List<NousNewsItemDomain>>
    suspend fun getNewsItem(newsItemId: Long): ResultWrap<NousNewsItemDomain>
}