package xyz.savvamirzoyan.nous.domain_gallery_manager

import xyz.savvamirzoyan.nous.core.ID
import xyz.savvamirzoyan.nous.core.Model
import xyz.savvamirzoyan.nous.core.PictureUrl

data class GalleryImageDomain(
    val id: ID,
    val pictureUrl: PictureUrl?,
    val title: String?,
    val description: String?
) : Model.Domain