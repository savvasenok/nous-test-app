package xyz.savvamirzoyan.nous.data_gallery

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import xyz.savvamirzoyan.nous.domain_gallery_manager.GalleryRepository

@InstallIn(SingletonComponent::class)
@Module
abstract class DataGalleryHiltModule {

    @Binds
    abstract fun bindNousFactToGalleryDomainMapper(base: NousFactToGalleryDomainMapper.Base): NousFactToGalleryDomainMapper

    @Binds
    abstract fun bindGalleryRepository(impl: GalleryRepositoryImpl): GalleryRepository

    companion object {

        @Provides
        fun provideTelegramBotApi(retrofit: Retrofit): NousFactsApi =
            retrofit.create(NousFactsApi::class.java)
    }
}