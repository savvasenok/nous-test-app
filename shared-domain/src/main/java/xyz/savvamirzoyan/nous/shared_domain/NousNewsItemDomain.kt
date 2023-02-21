package xyz.savvamirzoyan.nous.shared_domain

import xyz.savvamirzoyan.nous.core.ID
import xyz.savvamirzoyan.nous.core.Model
import xyz.savvamirzoyan.nous.core.PictureUrl

data class NousNewsItemDomain(
    val id: ID,
    val pictureUrl: PictureUrl,
    val title: String,
    val description: String
) : Model.Domain