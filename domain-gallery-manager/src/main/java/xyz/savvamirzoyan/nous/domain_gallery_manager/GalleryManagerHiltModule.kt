package xyz.savvamirzoyan.nous.domain_gallery_manager

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class GalleryManagerHiltModule {

    @Binds
    abstract fun bindGalleryManagerInteractor(base: GalleryManagerInteractor.Base): GalleryManagerInteractor
}