package xyz.savvamirzoyan.nous.data_gallery

import xyz.savvamirzoyan.nous.shared_data.NousNewsItemLocal
import xyz.savvamirzoyan.nous.shared_domain.NousNewsItemDomain
import javax.inject.Inject

interface NousNewsItemDataToDomainMapper {

    fun map(model: NousNewsItemLocal): NousNewsItemDomain
    fun map(model: NousNewsItemCloud): NousNewsItemDomain

    class Base @Inject constructor() : NousNewsItemDataToDomainMapper {

        override fun map(model: NousNewsItemLocal) =
            NousNewsItemDomain(
                id = model.factId,
                pictureUrl = model.pictureUrl,
                title = model.title,
                description = model.description
            )

        override fun map(model: NousNewsItemCloud) = NousNewsItemDomain(
            id = model.id,
            pictureUrl = model.pictureUrl,
            title = model.title,
            description = model.description
        )
    }
}
