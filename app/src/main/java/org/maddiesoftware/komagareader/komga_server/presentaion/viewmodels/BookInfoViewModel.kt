package org.maddiesoftware.komagareader.komga_server.presentaion.viewmodels

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

    private var bookId: String? = null
    private var groupType: String? = null
    private var readListId: String? = null
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
}