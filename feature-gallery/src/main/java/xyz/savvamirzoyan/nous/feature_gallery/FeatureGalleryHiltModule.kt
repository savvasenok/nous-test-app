package xyz.savvamirzoyan.nous.feature_gallery

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.savvamirzoyan.nous.feature_gallery.gallery.GalleryImageDomainToUiMapper
import xyz.savvamirzoyan.nous.feature_gallery.search.SearchResultImageDomainToUiMapper
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class FeatureGalleryHiltModule {

    @Singleton
    @Binds
    abstract fun bindGalleryImageDomainToUiMapper(base: GalleryImageDomainToUiMapper.Base): GalleryImageDomainToUiMapper

    @Singleton
    @Binds
    abstract fun bindSearchResultImageDomainToUiMapper(base: SearchResultImageDomainToUiMapper.Base): SearchResultImageDomainToUiMapper
}