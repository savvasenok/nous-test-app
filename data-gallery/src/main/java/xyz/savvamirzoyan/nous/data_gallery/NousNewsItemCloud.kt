package xyz.savvamirzoyan.nous.data_gallery

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.savvamirzoyan.nous.core.ID
import xyz.savvamirzoyan.nous.core.Model
import xyz.savvamirzoyan.nous.core.PictureUrl

@Serializable
data class NousNewsItemCloud(
    val id: ID,
    @SerialName("imageUrl") val pictureUrl: PictureUrl,
    val title: String,
    val description: String
) : Model.Data.Cloud