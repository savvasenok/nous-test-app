package xyz.savvamirzoyan.nous.domain_news_details

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class NewsDetailsHiltModule {

    @Binds
    abstract fun bindNewsDetailsInteractor(base: NewsDetailsInteractor.Base): NewsDetailsInteractor
}