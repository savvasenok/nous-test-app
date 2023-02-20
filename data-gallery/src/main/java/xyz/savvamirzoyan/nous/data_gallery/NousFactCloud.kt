package xyz.savvamirzoyan.nous.data_gallery

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.savvamirzoyan.nous.core.ID
import xyz.savvamirzoyan.nous.core.Model
import xyz.savvamirzoyan.nous.core.PictureUrl

// I suppose images with titles and descriptions are some facts about temples, paintings or ancient tools
@Serializable
data class NousFactCloud(
    val id: ID,
    @SerialName("imageUrl") val pictureUrl: PictureUrl,
    val title: String,
    val description: String
) : Model.Data.Cloud