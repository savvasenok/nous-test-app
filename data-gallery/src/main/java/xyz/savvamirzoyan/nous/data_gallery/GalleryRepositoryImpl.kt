package xyz.savvamirzoyan.nous.data_gallery

import kotlinx.coroutines.delay
import xyz.savvamirzoyan.nous.core.ErrorEntity
import xyz.savvamirzoyan.nous.core.ResultWrap
import xyz.savvamirzoyan.nous.shared_data.NousFactsDAO
import xyz.savvamirzoyan.nous.shared_domain.NousNewsItemDomain
import java.io.IOException
import javax.inject.Inject

class GalleryRepositoryImpl @Inject constructor(
    private val nousNewsApi: NousNewsApi,
    private val nousFactsDB: NousFactsDAO,
    private val nousNewsItemDataToDomainMapper: NousNewsItemDataToDomainMapper,
    private val nousNewsItemCloudToLocalMapper: NousNewsItemCloudToLocalMapper
) : xyz.savvamirzoyan.nous.shared_domain.NousNewsRepository {

    override suspend fun fetchNews(): ResultWrap<List<NousNewsItemDomain>> = try {

        delay(3000)

        (nousNewsApi.getNews().items
            ?.also { list -> cacheToDB(list) }
            ?.map { nousNewsItemDataToDomainMapper.map(it) } ?: emptyList())
            .let { if (it.isEmpty()) ResultWrap.Failure(ErrorEntity.NoData) else ResultWrap.Success(it) }

    } catch (_: IOException) {
        val fromDB = getNewsItemDomainFromDB()
        if (fromDB.isNotEmpty()) ResultWrap.Success(fromDB)
        else ResultWrap.Failure(ErrorEntity.NoConnection)
    } catch (_: Exception) {
        val fromDB = getNewsItemDomainFromDB()
        if (fromDB.isNotEmpty()) ResultWrap.Success(fromDB)
        else ResultWrap.Failure(ErrorEntity.NoData)
    }

    override suspend fun getNewsItem(newsItemId: Long): ResultWrap<NousNewsItemDomain> = getNewsItemDomainFromDB()
        .find { it.id == newsItemId }
        ?.let { ResultWrap.Success(it) }
        ?: ResultWrap.Failure(ErrorEntity.NoData)

    private suspend fun getNewsItemDomainFromDB(): List<NousNewsItemDomain> = nousFactsDB.selectAll()
        .let { list -> list.map { nousNewsItemDataToDomainMapper.map(it) } }

    private suspend fun cacheToDB(list: List<NousNewsItemCloud>) = list
        .map { nousNewsItemCloudToLocalMapper.map(it) }
        .also { nousFactsDB.insert(*it.toTypedArray()) }
}