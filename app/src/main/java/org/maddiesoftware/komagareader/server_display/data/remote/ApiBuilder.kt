package org.maddiesoftware.komagareader.server_display.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.maddiesoftware.komagareader.core.util.ServerInfoSingleton.url

import org.maddiesoftware.komagareader.server_display.data.remote.client.BasicAuthInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class ApiBuilder @Inject constructor() {

    fun <Api> builder(api: Class<Api>): Api {
        val baseUrl = "${url}api/v1/"
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(getClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api)
    }

    private fun getClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(BasicAuthInterceptor())
            .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC })
            .build()
    }
}