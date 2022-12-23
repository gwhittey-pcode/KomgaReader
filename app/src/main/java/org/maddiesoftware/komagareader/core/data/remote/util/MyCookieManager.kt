package org.maddiesoftware.komgareader.data.remote.util

import android.util.Log
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import okhttp3.Cookie
import okhttp3.Interceptor
import okhttp3.Response
import okio.IOException
package org.maddiesoftware.komagareader.core.domain.model.XAuthToken

/**
 * This Interceptor add all received Cookies to the app DefaultPreferences.
 * Your implementation on how to save the Cookies on the Preferences MAY VARY.
 *
 *
 * Created by tsuharesu on 4/1/15.
 */


class ReceivedCookiesInterceptor() : Interceptor {
    private var xAuthToken: String = ""
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse: Response = chain.proceed(chain.request())
        if (!originalResponse.headers("X-Auth-Token").isEmpty()) {
            val cookies: HashSet<String> = HashSet()
            for (header in originalResponse.headers("Set-Cookie")) {
                cookies.add(header)
            }
            xAuthToken = originalResponse.headers("X-Auth-Token").toString()
            Log.d("komga133", " cookie = $xAuthToken")
        }
        return originalResponse
    }

}

//class CookiesInterceptor: Interceptor {
//
//    companion object {
//        const val COOKIE_KEY = "Cookie"
//        const val SET_COOKIE_KEY = "Set-Cookie"
//    }
//
//    fun clearCookie() {
//        cookie = null
//    }
//
//    private var cookie: String? = null
//
//    override fun intercept(chain: Interceptor.Chain): Response {
//        Log.d("komga133", "Started intercept")
//        val request = chain.request()
//        val requestBuilder = request.newBuilder()
//        cookie?.let { requestBuilder.addHeader(COOKIE_KEY, it) }
//
//        val response = chain.proceed(requestBuilder.build())
//        Log.d("komga133", "$response.headers")
//        response.headers
//            .toMultimap()[SET_COOKIE_KEY]
//            ?.filter { !it.contains("HttpOnly") }
//            ?.getOrNull(0)
//            ?.also {
//                cookie = it
//                Log.d("komga133", "cookie = $cookie")
//            }
//
//        return response
//    }
//
//}