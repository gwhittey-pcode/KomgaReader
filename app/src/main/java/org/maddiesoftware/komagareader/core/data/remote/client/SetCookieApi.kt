package org.maddiesoftware.komgareader.data.remote.client

import retrofit2.http.GET
import retrofit2.http.Headers

interface SetCookieApi {
    @Headers("X-Auth-Token: ")
    @GET("/api/v1/login/set-cookie")
    fun getSession(
    )

}