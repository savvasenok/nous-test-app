package xyz.savvamirzoyan.nous.shared_data

import kotlinx.serialization.Serializable
import xyz.savvamirzoyan.nous.core.Model

@Serializable
data class ApiResults<M : Model.Data.Cloud>(
    val items: List<M>? = null
)