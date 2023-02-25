package org.maddiesoftware.komagareader.komga_server.presentaion.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.maddiesoftware.komagareader.core.util.Resource
import org.maddiesoftware.komagareader.komga_server.domain.repository.ApiRepository
import org.maddiesoftware.komagareader.komga_server.presentaion.state.BookInfoState
import javax.inject.Inject

@HiltViewModel
class BookInfoViewModel @Inject constructor(
    private val apiRepository: ApiRepository,
    savedStateHandle: SavedStateHandle,
): ViewModel()  {

    var bookId: String? = null
    var groupType: String? = null
    var readListId: String? = null
    var hasPrevBook:Boolean = false
    var hasNextBook: Boolean = false
    var state by mutableStateOf(BookInfoState())

    init {
        savedStateHandle.get<String>(key = "bookId")?.let { it ->
            bookId = it
            if(bookId == "null"){
                bookId = null
            }
        }
        savedStateHandle.get<String>(key = "groupType")?.let { it ->
            groupType = it
            if(groupType == "null"){
                groupType = null
            }
        }
        savedStateHandle.get<String>(key = "readListId")?.let { it ->
            readListId = it
            if(readListId == "null"){
                readListId = null
            }
        }
        getBookById()
        Log.d("BookInfo", "groupType = $groupType")
         if(groupType == "Read List"){
             getPreviousBookInReadList()
             getNextBookInReadList()
         }else{
             getPreviousBookInSeries()
             getNextBookInSeries()
         }

    }

    private fun getBookById(){
        viewModelScope.launch {
                      state = state.copy(isLoading = true)
            val bookByIdResult = async { apiRepository.getBookById(bookId = bookId.toString()) }
            when(val result = bookByIdResult.await()) {
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

    private fun getPreviousBookInReadList(){
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val previousBookInReadListResult = async { apiRepository.getPreviousBookInReadList(bookId = bookId.toString(), readListId = readListId.toString()) }
            when(val result = previousBookInReadListResult.await()) {
                is Resource.Success -> {
                    state = state.copy(
                        prevBookInfo = result.data,

                    )
                    hasPrevBook = true
                }
                is Resource.Error -> {
                    if(result.message == "404 Error"){
                        hasPrevBook = false
                    }
                }
                else -> Unit
            }
        }
    }

    private fun getNextBookInReadList(){
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val nextBookInReadListResult = async { apiRepository.getNextBookInReadList(bookId = bookId.toString(), readListId = readListId.toString()) }
            when(val result = nextBookInReadListResult.await()) {
                is Resource.Success -> {
                    state = state.copy(
                        nextBookInfo = result.data,
                    )
                    hasNextBook = true
                    Log.d("BookInfo", "state.hasNextBook  = $hasNextBook ")
                }
                is Resource.Error -> {
                    if(result.message == "404 Error"){
                        hasNextBook = false
                    }

                }
                else -> Unit
            }
        }
    }
    private fun getPreviousBookInSeries(){
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val previousBookInSeriesResult = async { apiRepository.getPreviousBookInSeries(bookId = bookId.toString()) }
            when(val result = previousBookInSeriesResult.await()) {
                is Resource.Success -> {
                    state = state.copy(
                        prevBookInfo = result.data,
                    )
                    hasPrevBook = true
                    Log.d("BookInfo", "state.hasNextBook  = $hasNextBook ")
                }
                is Resource.Error -> {
                    if(result.message == "404 Error"){
                        hasPrevBook = false
                    }

                }
                else -> Unit
            }
        }
    }
    private fun getNextBookInSeries(){
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val nextBookInSeriesResult = async { apiRepository.getNextBookInSeries(bookId = bookId.toString()) }
            when(val result = nextBookInSeriesResult.await()) {
                is Resource.Success -> {
                    state = state.copy(
                        nextBookInfo = result.data,
                    )
                    hasNextBook = true
                    Log.d("BookInfo", "state.hasNextBook  = $hasNextBook ")
                }
                is Resource.Error -> {
                    if(result.message == "404 Error"){
                        hasNextBook = false
                    }

                }
                else -> Unit
            }
        }
    }
}