package xyz.savvamirzoyan.nous.data_gallery

import xyz.savvamirzoyan.nous.domain_gallery_manager.GalleryImageDomain
import javax.inject.Inject

interface NousFactToGalleryDomainMapper {

    fun map(model: xyz.savvamirzoyan.nous.shared_data.NousFactLocal): GalleryImageDomain
    fun map(model: NousFactCloud): GalleryImageDomain

    class Base @Inject constructor() : NousFactToGalleryDomainMapper {

        override fun map(model: xyz.savvamirzoyan.nous.shared_data.NousFactLocal) = GalleryImageDomain(
            id = model.factId,
            pictureUrl = model.pictureUrl,
            title = model.title,
            description = model.description
        )

        override fun map(model: NousFactCloud) = GalleryImageDomain(
            id = model.id,
            pictureUrl = model.pictureUrl,
            title = model.title,
            description = model.description
        )
    }
}
