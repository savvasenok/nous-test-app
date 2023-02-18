package xyz.savvamirzoyan.nous.domain_gallery_manager

import xyz.savvamirzoyan.nous.core.Model
import xyz.savvamirzoyan.nous.core.PictureUrl

data class SearchResultImageDomain(
    val id: Int,
    val pictureUrl: PictureUrl,
    val title: String,
    val description: String,
    val titleSearchResultRange: IntRange?,
    val descriptionSearchResultRange: IntRange?,
) : Model.Ui