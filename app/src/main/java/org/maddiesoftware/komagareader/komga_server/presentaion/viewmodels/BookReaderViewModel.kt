package org.maddiesoftware.komagareader.komga_server.presentaion.viewmodels

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.imageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.maddiesoftware.komagareader.core.data.local.ReaderPreferenceSingleton.useDblPageSplit
import org.maddiesoftware.komagareader.core.util.Resource
import org.maddiesoftware.komagareader.komga_server.data.DataStoreManager
import org.maddiesoftware.komagareader.komga_server.domain.repository.ApiRepository
import org.maddiesoftware.komagareader.komga_server.persitance.PreferenceKeys
import org.maddiesoftware.komagareader.komga_server.presentaion.state.BookReaderState
import javax.inject.Inject


@HiltViewModel
class BookReaderViewModel @Inject constructor(
    private val apiRepository: ApiRepository,
    savedStateHandle: SavedStateHandle,
    private val dataStore: DataStoreManager
) : ViewModel() {

    private var bookId: String? = null
    private var groupType: String? = null
    var state by mutableStateOf(BookReaderState())

    init {
        savedStateHandle.get<String>(key = "bookId")?.let { it ->
            bookId = it
            if (bookId == "null") {
                bookId = null
            }
        }
        savedStateHandle.get<String>(key = "groupType")?.let { it ->
            groupType = it
            if (groupType == "null") {
                groupType = null
            }
        }
        readUseDblPageSplit()
        getBookById()
        getPages()

    }
    private fun getBookById() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val bookByIdResult = async { apiRepository.getBookById(bookId = bookId.toString()) }
            when (val result = bookByIdResult.await()) {
                is Resource.Success -> {
                    state = state.copy(
                        bookInfo = result.data,
                        isLoading = false,
                        error = null
                    )
                }
                is Resource.Error -> {
                    state = state.copy(
                        isLoading = false,
                        error = result.message,
                        bookInfo = null
                    )
                }
                else -> Unit
            }
        }
    }

    private fun getPages() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val pagesResult = async { apiRepository.getPages(bookId = bookId.toString()) }
            when (val result = pagesResult.await()) {
                is Resource.Success -> {
                    state = state.copy(
                        pagesInfo = result.data,
                        isLoading = false,
                        error = null
                    )
                }
                is Resource.Error -> {
                    state = state.copy(
                        isLoading = false,
                        error = result.message,
                        bookInfo = null
                    )

                }
                else -> Unit
            }
        }
    }

    suspend fun uriToBitmap(context: Context, uri: String?): Bitmap {
        val request = ImageRequest.Builder(context)
            .data(uri)
            .allowHardware(false) // Disable hardware bitmaps.
            .build()
        val loader = context.applicationContext.imageLoader
        val result = (loader.execute(request) as SuccessResult).drawable
        //        val resizedBitmap = Bitmap.createScaledBitmap(
//            bitmap, 80, 80, true);
        return (result as BitmapDrawable).bitmap
    }

    private fun readUseDblPageSplit() {
        viewModelScope.launch {
            dataStore.readValue(PreferenceKeys.DBL_PAGE_SPLIT) {
                useDblPageSplit = this
                state = state.copy(
                    useDblPageSplit = this,
                )
            }
        }
    }

    suspend fun updateReadProgress(page: Int, pageCount: Int, bookId: String) {
        var completed: Boolean = false
        if (page >= pageCount) completed = true
        viewModelScope.launch {
            val updateReadProgress = async {
                apiRepository.updateReadProgress(
                    bookId = bookId,
                    page = page,
                    completed = completed
                )
            }
            state = state.copy(doingUpdateReadProgress=true)
            when (val result = updateReadProgress.await()) {
                is Resource.Success -> {
                    Log.d("updateReadProgress","Sucess")
                    state = state.copy(doingUpdateReadProgress=false)
                }
                is Resource.Error -> {
                    Log.d("updateReadProgress","result = ${result.message}")
                }
                else -> Unit
            }
        }

    }

}