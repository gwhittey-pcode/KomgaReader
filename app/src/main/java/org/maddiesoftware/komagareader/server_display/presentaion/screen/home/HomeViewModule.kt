package org.maddiesoftware.komagareader.server_display.presentaion.screen.home

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
import org.maddiesoftware.komagareader.server_display.domain.repository.ApiRepository
import org.maddiesoftware.komagareader.server_select.domain.use_case.ServerUseCases
import javax.inject.Inject

@HiltViewModel
class HomeViewModule @Inject constructor(
    private val serverUseCases: ServerUseCases,
    private val apiRepository: ApiRepository,
    private val savedStateHandle: SavedStateHandle,

    ): ViewModel() {
    var state by mutableStateOf(HomeState())

    init {
        getKeepReading()
        getUpdatedSeries()
        getOnDeckBooks()
        getRecentlyAddedBooks()
        getNewSeries()

    }

    private fun getKeepReading(){
        viewModelScope.launch {
            Log.d("komga1", "viewModelScope.launch ")
            state = state.copy(isLoading = true)
            val keepReadingResult = async { apiRepository.getKeepReading() }
            when(val result = keepReadingResult.await()) {
                is Resource.Success -> {
                    Log.d("komga1", "Resource.success ")
//                    Log.d("komga1", "result = ${result.data} ")
                    state = state.copy(
                        getKeepReading = result.data,
                        isLoading = false,
                        error = null
                    )

                }
                is Resource.Error -> {
                    state = state.copy(
                        isLoading = false,
                        error = result.message,
                        getKeepReading = null
                    )
                    Log.d("komga1", "error = $result.message ")
                }
                else -> Unit
            }
        }
    }

    private fun getOnDeckBooks(){
        viewModelScope.launch {
            Log.d("komga1", "viewModelScope.launch ")
            state = state.copy(isLoading = true)
            val getOnDeckBooksResult = async { apiRepository.getOnDeckBooks() }
            when(val result = getOnDeckBooksResult.await()) {
                is Resource.Success -> {
                    Log.d("komga1", "Resource.success ")
//                    Log.d("komga1", "result = ${result.data} ")
                    state = state.copy(
                        getOnDeckBooks = result.data,
                        isLoading = false,
                        error = null
                    )
                }
                is Resource.Error -> {
                    state = state.copy(
                        isLoading = false,
                        error = result.message,
                        getOnDeckBooks = null
                    )
                    Log.d("komga1", "error = $result.message ")
                }
                else -> Unit
            }
        }
    }

    private fun getRecentlyAddedBooks(){
        viewModelScope.launch {
            Log.d("komga1", "viewModelScope.launch ")
            state = state.copy(isLoading = true)
            val getRecentlyAddedBooksResult = async { apiRepository.getRecentlyAddedBooks() }
            when(val result = getRecentlyAddedBooksResult.await()) {
                is Resource.Success -> {
                    Log.d("komga1", "Resource.success ")
//                    Log.d("komga1", "result = ${result.data} ")
                    state = state.copy(
                        getRecentlyAddedBooks = result.data,
                        isLoading = false,
                        error = null
                    )

                }
                is Resource.Error -> {
                    state = state.copy(
                        isLoading = false,
                        error = result.message,
                        getRecentlyAddedBooks = null
                    )
                    Log.d("komga1", "error = $result.message ")
                }
                else -> Unit
            }
        }
    }
    private fun getNewSeries(){
        viewModelScope.launch {
            Log.d("komga1", "viewModelScope.launch ")
            state = state.copy(isLoading = true)
            val newSeriesResult = async { apiRepository.getNewSeries() }
            when(val result = newSeriesResult.await()) {
                is Resource.Success -> {
                    Log.d("komga1", "Resource.success ")
//                    Log.d("komga1", "result = ${result.data} ")
                    state = state.copy(
                        getNewSeries = result.data,
                        isLoading = false,
                        error = null
                    )

                }
                is Resource.Error -> {
                    state = state.copy(
                        isLoading = false,
                        error = result.message,
                        getUpdatedSeries = null
                    )
                    Log.d("komga1", "error = $result.message ")
                }
                else -> Unit
            }
        }
    }

    private fun getUpdatedSeries(){
        viewModelScope.launch {
            Log.d("komga1", "viewModelScope.launch ")
            state = state.copy(isLoading = true)
            val updatedSeriesResult = async { apiRepository.getUpdatedSeries() }
            when(val result = updatedSeriesResult.await()) {
                is Resource.Success -> {
                    Log.d("komga1", "Resource.success ")
//                    Log.d("komga1", "result = ${result.data} ")
                    state = state.copy(
                        getUpdatedSeries = result.data,
                        isLoading = false,
                        error = null
                    )

                }
                is Resource.Error -> {
                    state = state.copy(
                        isLoading = false,
                        error = result.message,
                        getUpdatedSeries = null
                    )
                    Log.d("komga1", "error = $result.message ")
                }
                else -> Unit
            }
        }
    }


}