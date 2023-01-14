package org.maddiesoftware.komagareader

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import dagger.hilt.android.HiltAndroidApp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.maddiesoftware.komagareader.komga_server.data.remote.client.BasicAuthInterceptor

@HiltAndroidApp
class KomgaReaderApp:Application(), ImageLoaderFactory {
    /**
     * Create the singleton [ImageLoader].
     * This is used by [AsyncImage] to load images in the app.
     */

    override fun newImageLoader(): ImageLoader = ImageLoader.Builder(applicationContext)
        .okHttpClient {
            OkHttpClient.Builder()
                // This header will be added to every image request.
                .addInterceptor(BasicAuthInterceptor())
                .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC })
                .build()
        }
        .build()
}
