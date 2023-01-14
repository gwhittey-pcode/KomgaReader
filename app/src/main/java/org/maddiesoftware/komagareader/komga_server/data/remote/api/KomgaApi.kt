package org.maddiesoftware.komagareader.komga_server.data.remote.api

import org.maddiesoftware.komagareader.komga_server.data.remote.dto.*
import retrofit2.http.*

interface KomgaServerApi {
    //get library list
    @GET("libraries")
    suspend fun getLibraries():List<LibraryDto>
    //    Series Controller
    @GET("series")
    suspend fun getAllSeries(
        @Query("search") search: String? = null,
        @Query("library_id") libraryId: String? = null,
        @Query("collection_id") collectionId: List<String>? = null,
        @Query("status") status: List<String>? = null,
        @Query("read_status") readStatus: List<String>? = null,
        @Query("publisher") publisher: List<String>? = null,
        @Query("language") language: List<String>? = null,
        @Query("genre") genre: List<String>? = null,
        @Query("tag") tag: List<String>? = null,
        @Query("age_rating") ageRating: List<String>? = null,
        @Query("release_year") releaseYear: List<String>? = null,
        @Query("unpaged") unpaged: Boolean? = null,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
        @Query("sort") sort: List<String>? = null,
        @Query("author") author: List<String>? = null
    ): ResponseItems<SeriesDto>

    @GET("series/{seriesId}")
    suspend fun getSeriesById(@Path("seriesId") seriesId: String): SeriesDto

    @GET("series/{seriesId}/books")
    suspend fun getBooksFromSeries(
        @Path("seriesId") seriesId: String,
        @Query("media_status") mediaStatus: List<String>? = null,
        @Query("read_status") readStatus: List<String>? = null,
        @Query("tag") tag: List<String>? = null,
        @Query("unpaged") unpaged: Boolean? = null,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
        @Query("sort") sort: List<String>? = null,
        @Query("author") author: List<String>? = null
    ): ResponseItems<BookDto>



    @Headers("X-Auth-Token:''")
    @GET("series/latest")
    suspend fun getLatest(
        @Query("library_id") libraryId: String? = null,
        @Query("unpaged") unpaged: Boolean? = null,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null
    ): PageSeriesDto?


    @GET("series/new")
    suspend fun getNew(
        @Query("library_id") libraryId: String? = null,
        @Query("unpaged") unpaged: Boolean? = null,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null
    ): PageSeriesDto?

    @GET("series/updated")
    suspend fun getUpdatedSeries(
        @Query("library_id") libraryId: String? = null,
        @Query("unpaged") unpaged: Boolean? = null,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null
    ): PageSeriesDto?

    @POST("series/{seriesId}/read-progress")
    suspend fun markAsRead(@Path("seriesId") seriesId: String)

    @DELETE("series/{seriesId}/read-progress")
    suspend fun deleteReadProgress(@Path("seriesId") seriesId: String)


// Reading Lists Contoller

//Books Controller
    @GET("books")
    suspend fun getAllBooks(
    @Query("library_id") libraryId: String? = null,
    @Query("media_status") mediaStatus: List<String>? = null,
    @Query("read_status") readStatus: List<String>? = null,
    @Query("tag") tag: List<String>? = null,
    @Query("unpaged") unpaged: Boolean? = null,
    @Query("page") page: Int? = null,
    @Query("size") size: Int? = null,
    @Query("sort") sort: List<String>? = null,
    @Query("author") author: List<String>? = null
    ):PageBookDto


    @GET("books/ondeck")
    suspend fun getOnDeckBooks(
        @Query("page") page: Int? = null,
        @Query("library_id") libraryId: String? = null,
    ):PageBookDto

    @GET("books/{bookId}")
    suspend fun getBookById(
        @Path("bookId") bookId: String
    ):BookDto

    @GET("books/{bookId}/pages")
    suspend fun getPages(
        @Path("bookId") bookId: String
    ):List<PageDto>
}



