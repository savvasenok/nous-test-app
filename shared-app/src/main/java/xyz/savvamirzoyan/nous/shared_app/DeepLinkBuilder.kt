package xyz.savvamirzoyan.nous.shared_app

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeepLinkBuilder @Inject constructor() {

    fun newsItemDetailsDeepLink(newsItemId: Long) = "nous-app://news-item?news-item-id=$newsItemId"
}