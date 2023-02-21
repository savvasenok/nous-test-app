package xyz.savvamirzoyan.nous.data_gallery

import xyz.savvamirzoyan.nous.shared_data.NousNewsItemLocal
import javax.inject.Inject

interface NousNewsItemCloudToLocalMapper {

    fun map(model: NousNewsItemCloud): NousNewsItemLocal

    class Base @Inject constructor() : NousNewsItemCloudToLocalMapper {

        override fun map(model: NousNewsItemCloud) = NousNewsItemLocal(
            factId = model.id,
            pictureUrl = model.pictureUrl,
            title = model.title,
            description = model.description
        )
    }
}
