package org.maddiesoftware.komagareader.server_display.data.remote.api

import org.maddiesoftware.komagareader.server_display.data.remote.dto.*
import org.maddiesoftware.komagareader.server_display.domain.model.PageSeries

import retrofit2.http.*

interface KomgaServerApi {
    //get library list
    @GET("libraries")
    suspend fun getLibraries():List<LibraryDto>
    //    Series Controller
    @GET("series")
    suspend fun getAllSeries(
        @Query("search") search: String?,
        @Query("library_id") libraryId: String?,
        @Query("collection_id") collectionId: List<String>?,
        @Query("status") status: List<String>?,
        @Query("read_status") readStatus: List<String>?,
        @Query("publisher") publisher: List<String>?,
        @Query("language") language: List<String>?,
        @Query("genre") genre: List<String>?,
        @Query("tag") tag: List<String>?,
        @Query("age_rating") ageRating: List<String>?,
        @Query("release_year") releaseYear: List<String>?,
        @Query("unpaged") unpaged: Boolean?,
        @Query("page") page: Int?,
        @Query("size") size: Int?,
        @Query("sort") sort: List<String>?,
        @Query("author") author: List<String>?
    ): ResponseItems<SeriesDto>

    @GET("series/{seriesId}")
    suspend fun getSeriesById(@Path("seriesId") seriesId: String)

    @GET("series/{seriesId}/books")
    suspend fun getBooksFromSeries(
        @Path("seriesId") seriesId: String,
        @Query("media_status") mediaStatus: List<String>?,
        @Query("read_status") readStatus: List<String>?,
        @Query("tag") tag: List<String>?,
        @Query("unpaged") unpaged: Boolean?,
        @Query("page") page: Int?,
        @Query("size") size: Int?,
        @Query("sort") sort: List<String>?,
        @Query("author") author: List<String>?
    ): PageBookDto



    @Headers("X-Auth-Token:''")
    @GET("series/latest")
    suspend fun getLatest(
        @Query("library_id") libraryId: String?,
        @Query("unpaged") unpaged: Boolean?,
        @Query("page") page: Int?,
        @Query("size") size: Int?
    ): PageSeriesDto?


    @GET("series/new")
    suspend fun getNew(
        @Query("library_id") libraryId: List<String>?,
        @Query("unpaged") unpaged: Boolean?,
        @Query("page") page: Int?,
        @Query("size") size: Int?
    ): PageSeriesDto?

    @GET("series/updated")
    suspend fun getUpdatedSeries(
        @Query("library_id") libraryId: List<String>?,
        @Query("unpaged") unpaged: Boolean?,
        @Query("page") page: Int?,
        @Query("size") size: Int?
    ): PageSeriesDto?

    @POST("series/{seriesId}/read-progress")
    suspend fun markAsRead(@Path("seriesId") seriesId: String?)

    @DELETE("series/{seriesId}/read-progress")
    suspend fun deleteReadProgress(@Path("seriesId") seriesId: String?)


// Reading Lists Contoller

//Books Controller
    @GET("books")
    suspend fun getAllBooks(
        @Query("media_status") mediaStatus: List<String>?,
        @Query("read_status") readStatus: List<String>?,
        @Query("tag") tag: List<String>?,
        @Query("unpaged") unpaged: Boolean?,
        @Query("page") page: Int?,
        @Query("size") size: Int?,
        @Query("sort") sort: List<String>?,
        @Query("author") author: List<String>?
    ):PageBookDto


    @GET("books/ondeck")
    suspend fun getOnDeckBooks(
    ):PageBookDto

}



