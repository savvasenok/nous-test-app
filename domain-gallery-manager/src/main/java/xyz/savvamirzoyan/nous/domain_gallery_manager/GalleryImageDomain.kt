package xyz.savvamirzoyan.nous.domain_gallery_manager

import xyz.savvamirzoyan.nous.core.Model
import xyz.savvamirzoyan.nous.core.PictureUrl

data class GalleryImageDomain(
    val id: Int,
    val pictureUrl: PictureUrl,
    val title: String,
    val description: String
) : Model.Ui