package xyz.savvamirzoyan.nous.domain_gallery_manager

import kotlinx.coroutines.flow.Flow
import xyz.savvamirzoyan.nous.core.ResultWrap

interface GalleryRepository {

    suspend fun fetchImages(): Flow<ResultWrap<List<GalleryImageDomain>>>
}