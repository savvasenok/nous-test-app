package xyz.savvamirzoyan.nous.shared_data

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import xyz.savvamirzoyan.nous.core.ErrorEntity
import xyz.savvamirzoyan.nous.core.PictureUrl
import xyz.savvamirzoyan.nous.core.ResultWrap
import java.io.File
import java.io.IOException
import java.net.URL
import javax.inject.Inject

interface PictureDownloader {

    suspend fun saveTemporaryPicture(title: String, pictureUrl: PictureUrl): ResultWrap<Uri>

    class Base @Inject constructor(
        @ApplicationContext private val context: Context
    ) : PictureDownloader {

        override suspend fun saveTemporaryPicture(title: String, pictureUrl: PictureUrl): ResultWrap<Uri> =
            withContext(Dispatchers.IO) {

                delay(3000)

                val file = File.createTempFile(title, ".png", context.externalCacheDir).apply {
                    createNewFile()
                    deleteOnExit()
                }

                try {
                    val inputStream = URL(pictureUrl).openStream()
                    file.outputStream().use { outputStream ->
                        inputStream.copyTo(outputStream)
                    }
                    ResultWrap.Success(file)
                } catch (_: IOException) {
                    ResultWrap.Failure(ErrorEntity.NoConnection)
                } catch (_: Exception) {
                    ResultWrap.Failure(ErrorEntity.Unknown)
                }
            }.map { FileProvider.getUriForFile(context, "${context.packageName}.provider", it) }
    }
}