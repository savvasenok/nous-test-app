package xyz.savvamirzoyan.nous.data_gallery

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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
    private val nousFactToGalleryDomainMapper: NousFactToGalleryDomainMapper
) : GalleryRepository {

    override suspend fun fetchImages(): Flow<ResultWrap<List<GalleryImageDomain>>> = flow {
        val fromDB = nousFactsDB.selectAll()

        Log.d("SPAMEGGS", "fromDB: ${fromDB.joinToString()}")

        if (fromDB.isNotEmpty()) emit(ResultWrap.Success(fromDB.map { nousFactToGalleryDomainMapper.map(it) }))

        try {
            val fromApi = (nousFactsApi.getImages().items
                ?.map { nousFactToGalleryDomainMapper.map(it) }
                ?: emptyList())
                .also { /* TODO: cache to local DB */ }
                .let { if (it.isEmpty()) ResultWrap.Failure(ErrorEntity.NoData) else ResultWrap.Success(it) }

            Log.d("SPAMEGGS", "fromApi: ${fromApi.get()}")

            emit(fromApi)
        } catch (e: IOException) {
            emit(ResultWrap.Failure(ErrorEntity.NoConnection))
        } catch (e: Exception) {
            emit(ResultWrap.Failure(ErrorEntity.ServerError))
        }
    }
}