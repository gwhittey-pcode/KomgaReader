package org.maddiesoftware.komagareader.server_display.data.remote.client

import okhttp3.Credentials
import okhttp3.Interceptor
import org.maddiesoftware.komagareader.core.util.ServerInfoSingleton


class BasicAuthInterceptor(
//    username: String?, password: String?
) : Interceptor {
    val userName = ServerInfoSingleton.userName
    var password = ServerInfoSingleton.password
    private var credentials: String = Credentials.basic(userName, password)

    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        var request = chain.request()
        request = request.newBuilder().header("Authorization", credentials).build()
        return chain.proceed(request)
    }
}