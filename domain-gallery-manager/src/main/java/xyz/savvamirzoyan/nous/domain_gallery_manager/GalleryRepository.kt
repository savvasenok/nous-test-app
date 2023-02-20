package xyz.savvamirzoyan.nous.domain_gallery_manager

import xyz.savvamirzoyan.nous.core.ResultWrap

interface GalleryRepository {

    suspend fun fetchImages(): ResultWrap<List<GalleryImageDomain>>
}