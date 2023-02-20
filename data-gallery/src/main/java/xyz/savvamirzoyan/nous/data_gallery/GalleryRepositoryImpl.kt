package xyz.savvamirzoyan.nous.data_gallery

import xyz.savvamirzoyan.nous.core.ErrorEntity
import xyz.savvamirzoyan.nous.core.ResultWrap
import xyz.savvamirzoyan.nous.domain_gallery_manager.GalleryImageDomain
import xyz.savvamirzoyan.nous.domain_gallery_manager.GalleryRepository
import xyz.savvamirzoyan.nous.shared_data.NousFactsDAO
import java.io.IOException
import javax.inject.Inject

class GalleryRepositoryImpl @Inject constructor(
    private val nousFactsApi: NousFactsApi,
    private val nousFactsDB: NousFactsDAO,
    private val nousFactToGalleryDomainMapper: NousFactToGalleryDomainMapper,
    private val nousFactCloudToLocalMapper: NousFactCloudToLocalMapper
) : GalleryRepository {

    override suspend fun fetchImages(): ResultWrap<List<GalleryImageDomain>> =
        try {
            val fromApi = (nousFactsApi.getImages().items
                ?.also { list ->
                    list.map { nousFactCloudToLocalMapper.map(it) }
                        .also { nousFactsDB.insert(*it.toTypedArray()) }
                }
                ?.map { nousFactToGalleryDomainMapper.map(it) } ?: emptyList())
                .let { if (it.isEmpty()) ResultWrap.Failure(ErrorEntity.NoData) else ResultWrap.Success(it) }

            fromApi
        } catch (_: IOException) {
            val fromDB = getGalleryImagesDomainFromDB()
            if (fromDB.data.isNotEmpty()) fromDB else ResultWrap.Failure(ErrorEntity.NoConnection)
        } catch (_: Exception) {
            val fromDB = getGalleryImagesDomainFromDB()
            if (fromDB.data.isNotEmpty()) fromDB else ResultWrap.Failure(ErrorEntity.ServerError)
        }

    private suspend fun getGalleryImagesDomainFromDB(): ResultWrap.Success<List<GalleryImageDomain>> =
        nousFactsDB.selectAll()
            .let { list -> ResultWrap.Success(list.map { nousFactToGalleryDomainMapper.map(it) }) }
}