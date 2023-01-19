package org.maddiesoftware.komagareader.komga_server.presentaion.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.maddiesoftware.komagareader.core.util.PAGE_SIZE
import org.maddiesoftware.komagareader.core.util.Resource
import org.maddiesoftware.komagareader.komga_server.domain.model.Book
import org.maddiesoftware.komagareader.komga_server.domain.model.ReadList
import org.maddiesoftware.komagareader.komga_server.domain.repository.ApiRepository
import org.maddiesoftware.komagareader.komga_server.domain.use_case.readlists.ReadListUseCases
import org.maddiesoftware.komagareader.komga_server.presentaion.state.ReadListByIdState
import javax.inject.Inject

@HiltViewModel
class ReadListByIdViewModel @Inject constructor(
    private val apiRepository: ApiRepository,
    private val readListUseCases: ReadListUseCases,
    savedStateHandle: SavedStateHandle,

    ): ViewModel()  {
    private var readListId: String? = null
    private val _bookState: MutableStateFlow<PagingData<Book>> =
        MutableStateFlow(value = PagingData.empty())
    val bookState: StateFlow<PagingData<Book>>
        get() = _bookState
    var state by mutableStateOf(ReadListByIdState())
    init {
        savedStateHandle.get<String>(key = "readListId")?.let { it ->
            readListId = it
            if(readListId == "null"){
                readListId = null
            }
        }
        getBooksFromReadList()
        getReadListById()
    }

    private fun getBooksFromReadList(){
        viewModelScope.launch {
            readListUseCases.getBooksFromReadList.invoke(pageSize = PAGE_SIZE, readListId = readListId)
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect {
                    _bookState.value = it
                }
        }

    }

    private fun getReadListById(){
        viewModelScope.launch {
            Log.d("komga-launch", "Launch")
            state = state.copy(isLoading = true)
            val readListByIdResult = async { apiRepository.getReadListById(readListId = readListId.toString()) }
            when(val result = readListByIdResult.await()) {
                is Resource.Success -> {
                    state = state.copy(
                        readListInfo = result.data,
                        isLoading = false,
                        error = null
                    )
                }
                is Resource.Error -> {
                    state = state.copy(
                        isLoading = false,
                        error = result.message,
                        readListInfo = null
                    )
                    Log.d("komga1", "error = $result.message ")
                }
                else -> Unit
            }
        }
    }

     fun calculateBookIndexInReadList(bookId:String):Int {
         Log.d("REadListDialog","**************** calculateBookIndexInReadList state.readListInfo=${state.readListInfo} ***************")
         return if (state.readListInfo != null) {
             Log.d("REadListDialog","ReadListInfo!null")
             val readListInfo:ReadList = state.readListInfo!!
             val index = readListInfo.bookIds?.indexOf(bookId)
             Log.d("REadListDialog","readListInfo.bookIds?.indexOf(bookId)=${readListInfo.bookIds?.indexOf(bookId)} index=$index ")
             index?.toInt() ?: 0
         }else 0
    }
}