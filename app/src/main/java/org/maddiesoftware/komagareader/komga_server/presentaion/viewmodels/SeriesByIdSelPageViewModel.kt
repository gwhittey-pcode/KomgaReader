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
import org.maddiesoftware.komagareader.komga_server.domain.repository.ApiRepository
import org.maddiesoftware.komagareader.komga_server.domain.use_case.series.AllSeriesUseCases
import org.maddiesoftware.komagareader.komga_server.presentaion.state.SeriesByIdState
import javax.inject.Inject

@HiltViewModel
class SeriesByIdSelPageViewModel @Inject constructor(
    private val apiRepository: ApiRepository,
    private val allSeriesUseCases: AllSeriesUseCases,
    savedStateHandle: SavedStateHandle,

    ): ViewModel()  {
    private var seriesId: String? = null
    private var startPos: Int = 0
    private val _bookState: MutableStateFlow<PagingData<Book>> =
        MutableStateFlow(value = PagingData.empty())
    val bookState: StateFlow<PagingData<Book>>
        get() = _bookState
    var state by mutableStateOf(SeriesByIdState())
    var thisPageSize: Int = 300
    init {
        savedStateHandle.get<String>(key = "seriesId2")?.let { it ->
            seriesId = it
            if(seriesId == "null"){
                seriesId = null
            }
        }
        getBooksFromSeries()
        getSeriesById()
    }

    private fun getBooksFromSeries(){
        Log.d("BookCount", "getBooksFromSeries")
        viewModelScope.launch {
            allSeriesUseCases.getBooksFromSeries.invoke(pageSize = thisPageSize, seriesId = seriesId,startPos=startPos)
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect {
                    _bookState.value = it
                }
        }

    }

    private fun getSeriesById(){
        viewModelScope.launch {

            state = state.copy(isLoading = true)
            val seriesByIdResult = async { apiRepository.getSeriesById(seriesId = seriesId.toString()) }
            when(val result = seriesByIdResult.await()) {
                is Resource.Success -> {
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
