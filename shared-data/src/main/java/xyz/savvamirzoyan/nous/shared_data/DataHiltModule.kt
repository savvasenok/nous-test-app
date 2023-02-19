@file:OptIn(ExperimentalSerializationApi::class)

package xyz.savvamirzoyan.nous.shared_data

import android.content.Context
import androidx.room.Room
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class DataModuleProvider {

    companion object {

        private val json = Json { ignoreUnknownKeys = true }
        private val contentType = "application/json".toMediaType()

        @Singleton
        @Provides
        fun provideNousDataBase(@ApplicationContext context: Context): NousDataBase = Room
            .databaseBuilder(
                context,
                NousDataBase::class.java,
                "main"
            )
            .fallbackToDestructiveMigration()
            .build()

        @Provides
        fun provideNousFactsDAO(db: NousDataBase): NousFactsDAO = db.nousFactsDao

        @Provides
        fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
            .build()

        @Provides
        fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.NOUS_API_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()

    }
}