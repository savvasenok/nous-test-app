package xyz.savvamirzoyan.nous.feature_gallery.gallery

import xyz.savvamirzoyan.nous.shared_domain.NousNewsItemDomain
import javax.inject.Inject

// This mapper is currently simple, but its useful to have single source of all possible mappings variations
// in one place. E.g. from domain-layer these are the same models, but for different UI states it has to be
// mapped the different way (overlay color, picture-size etc)
interface GalleryImageDomainToUiMapper {

    fun map(model: NousNewsItemDomain): GalleryImageUi

    class Base @Inject constructor() : GalleryImageDomainToUiMapper {

        override fun map(model: NousNewsItemDomain) = GalleryImageUi(
            pictureUrl = model.pictureUrl ?: ""
        )
    }
}