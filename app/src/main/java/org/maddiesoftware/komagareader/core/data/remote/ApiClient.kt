package org.maddiesoftware.komagareader.core.data.remote

import okhttp3.OkHttpClient
// import org.maddiesoftware.komgareader.feature_reader.data.remote.client.KomgaBookApi
import org.maddiesoftware.komgareader.utility.BasicAuthInterceptor
import retrofit2.Retrofit

import retrofit2.converter.moshi.MoshiConverterFactory
const val url_stored = "http://192.168.1.69:8080"
object ApiClient {
    private val BASE_URL = "$url_stored/api/v1/"
    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(BasicAuthInterceptor("gerard.whittey@gmail.com", "1872"))
        .build()
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }
//    val komgaApi: KomgaBookApi by lazy {
//        retrofit.create(KomgaBookApi :: class.java)
//    }
}
