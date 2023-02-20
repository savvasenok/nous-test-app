package xyz.savvamirzoyan.nous.data_gallery

import xyz.savvamirzoyan.nous.shared_data.NousFactLocal
import javax.inject.Inject

interface NousFactCloudToLocalMapper {

    fun map(model: NousFactCloud): NousFactLocal

    class Base @Inject constructor() : NousFactCloudToLocalMapper {

        override fun map(model: NousFactCloud) = NousFactLocal(
            factId = model.id,
            pictureUrl = model.pictureUrl,
            title = model.title,
            description = model.description
        )
    }
}
