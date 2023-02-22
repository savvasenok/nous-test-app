package xyz.savvamirzoyan.nous.data_gallery

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import xyz.savvamirzoyan.nous.shared_domain.NousNewsRepository
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class DataGalleryHiltModule {

    @Singleton
    @Binds
    abstract fun bindNousNewsItemDataToDomainMapper(base: NousNewsItemDataToDomainMapper.Base): NousNewsItemDataToDomainMapper

    @Singleton
    @Binds
    abstract fun bindGalleryRepository(impl: GalleryRepositoryImpl): NousNewsRepository

    @Singleton
    @Binds
    abstract fun bindNousNewsItemCloudToLocalMapper(base: NousNewsItemCloudToLocalMapper.Base): NousNewsItemCloudToLocalMapper

    companion object {

        @Singleton
        @Provides
        fun provideNousNewsApi(retrofit: Retrofit): NousNewsApi =
            retrofit.create(NousNewsApi::class.java)
    }
}