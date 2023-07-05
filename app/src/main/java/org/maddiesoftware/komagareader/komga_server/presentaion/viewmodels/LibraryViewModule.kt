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
import org.maddiesoftware.komagareader.komga_server.presentaion.state.LibaryState
import javax.inject.Inject

@HiltViewModel
class LibraryViewModule @Inject constructor(
    private val repository: ApiRepository,
    private val savedStateHandle: SavedStateHandle,
): ViewModel() {
    var state by mutableStateOf(LibaryState())

    init {
        getLibraryList()
    }

    private fun getLibraryList(){
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val libraryListResults = async { repository.getAllLibraries() }
            when(val result = libraryListResults.await()) {
                is Resource.Success -> {
                    Log.d("komga1", "Resource.success ")
                    Log.d("komga1", "result = ${result.data} ")
                    state = state.copy(
                        libraryList = result.data,
                        isLoading = false,
                        error = null
                    )
                }
                is Resource.Error -> {
                    state = state.copy(
                        isLoading = false,
                        error = result.message,
                        libraryList = null
                    )
                    Log.d("komga1", "error = $result.message ")
                }
                else -> Unit
            }
        }
    }
}