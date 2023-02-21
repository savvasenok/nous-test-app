package xyz.savvamirzoyan.nous.data_gallery

import retrofit2.http.GET
import xyz.savvamirzoyan.nous.shared_data.ApiResults

interface NousNewsApi {

    @GET("download")
    suspend fun getNews(): ApiResults<NousNewsItemCloud>
}