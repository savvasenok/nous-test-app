package xyz.savvamirzoyan.nous.feature_details

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class DetailsHiltModule {

    @Singleton
    @Binds
    abstract fun bindNewsItemDomainToDetailsUiMapper(base: NewsItemDomainToDetailsUiMapper.Base): NewsItemDomainToDetailsUiMapper
}