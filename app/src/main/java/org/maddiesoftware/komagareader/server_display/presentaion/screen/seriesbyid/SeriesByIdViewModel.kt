package org.maddiesoftware.komagareader.server_display.presentaion.screen.seriesbyid

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
import org.maddiesoftware.komagareader.server_display.domain.model.Book
import org.maddiesoftware.komagareader.server_display.domain.repository.ApiRepository
import org.maddiesoftware.komagareader.server_display.domain.usecase.AllSeriesUseCases
import javax.inject.Inject

@HiltViewModel
class SeriesByIdViewModel @Inject constructor(
    private val apiRepository: ApiRepository,
    allSeriesUseCases: AllSeriesUseCases,
    savedStateHandle: SavedStateHandle,

    ): ViewModel()  {
    private var seriesId: String? = null
    private val _bookState: MutableStateFlow<PagingData<Book>> =
        MutableStateFlow(value = PagingData.empty())
    val bookState: StateFlow<PagingData<Book>>
        get() = _bookState
    var state by mutableStateOf(SeriesByIdState())
    init {
        savedStateHandle.get<String>(key = "seriesId")?.let { it ->
            seriesId = it
            if(seriesId == "null"){
                seriesId = null
            }
        }
        viewModelScope.launch {
            allSeriesUseCases.getBooksFromSeries.invoke(pageSize = PAGE_SIZE, seriesId = seriesId)
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect {
                    _bookState.value = it
                }
        }
        getSeriesById()

    }
    private fun getSeriesById(){
        viewModelScope.launch {
            Log.d("komga-launch", "Launch")
            state = state.copy(isLoading = true)
            val seriesByIdResult = async { apiRepository.getSeriesById(seriesId = seriesId.toString()) }
            when(val result = seriesByIdResult.await()) {
                is Resource.Success -> {

//                    Log.d("komga1", "result = ${result.data} ")
                    state = state.copy(
                        seriesInfo = result.data,
                        isLoading = false,
                        error = null
                    )
                }
                is Resource.Error -> {
                    state = state.copy(
                        isLoading = false,
                        error = result.message,
                        seriesInfo = null
                    )
                    Log.d("komga1", "error = $result.message ")
                }
                else -> Unit
            }
        }
    }
}
