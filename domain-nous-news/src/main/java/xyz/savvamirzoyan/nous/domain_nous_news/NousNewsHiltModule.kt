package xyz.savvamirzoyan.nous.domain_nous_news

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class NousNewsHiltModule {

    @Binds
    abstract fun bindNousNewsManagerInteractor(base: NousNewsManagerInteractor.Base): NousNewsManagerInteractor
}