package xyz.savvamirzoyan.nous.feature_details

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class DetailsHiltModule {

    @Binds
    abstract fun bindNewsItemDomainToDetailsUiMapper(base: NewsItemDomainToDetailsUiMapper.Base): NewsItemDomainToDetailsUiMapper
}