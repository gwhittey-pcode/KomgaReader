package org.maddiesoftware.komagareader.komga_server.presentaion.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.maddiesoftware.komagareader.core.util.PAGE_SIZE
import org.maddiesoftware.komagareader.komga_server.domain.model.Book
import org.maddiesoftware.komagareader.komga_server.domain.model.Series
import org.maddiesoftware.komagareader.komga_server.domain.use_case.HomeScreenUseCases
import org.maddiesoftware.komagareader.komga_server.presentaion.state.HomeState
import javax.inject.Inject

@HiltViewModel
class HomeViewModule @Inject constructor(

    private val homeScreenUseCases: HomeScreenUseCases

) : ViewModel() {
    var state by mutableStateOf(HomeState())
    private val _keepReadingState: MutableStateFlow<PagingData<Book>> =
        MutableStateFlow(value = PagingData.empty())
    val keepReadingState: StateFlow<PagingData<Book>>
        get() = _keepReadingState

    private val _onDeckBooksState: MutableStateFlow<PagingData<Book>> =
        MutableStateFlow(value = PagingData.empty())
    val onDeckBooksState: StateFlow<PagingData<Book>>
        get() = _onDeckBooksState

    private val _recentlyAddedBooks: MutableStateFlow<PagingData<Book>> =
        MutableStateFlow(value = PagingData.empty())
    val recentlyAddedBooks: StateFlow<PagingData<Book>>
        get() = _recentlyAddedBooks

    private val _newSeries: MutableStateFlow<PagingData<Series>> =
        MutableStateFlow(value = PagingData.empty())
    val newSeries: StateFlow<PagingData<Series>>
        get() = _newSeries

    private val _updatedSeries: MutableStateFlow<PagingData<Series>> =
        MutableStateFlow(value = PagingData.empty())
    val updatedSeries: StateFlow<PagingData<Series>>
        get() = _updatedSeries

    val libraryId = null

    init {
        getKeepReading()
        getOnDeckBooks()
        getRecentlyAddedBooks()
        getNewSeries()
        getUpdatedSeries()
    }

    private fun getKeepReading() {
        viewModelScope.launch {
            homeScreenUseCases.getKeepReading.invoke(pageSize = PAGE_SIZE, libraryId = libraryId)
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect {
                    _keepReadingState.value = it
                }
        }
    }

    private fun getOnDeckBooks() {
        viewModelScope.launch {
            homeScreenUseCases.getOnDeckBooks.invoke(pageSize = PAGE_SIZE, libraryId = libraryId)
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect {
                    _onDeckBooksState.value = it
                }
        }
    }

    private fun getRecentlyAddedBooks() {
        viewModelScope.launch {
            homeScreenUseCases.getRecentlyAddedBooks.invoke(
                pageSize = PAGE_SIZE,
                libraryId = libraryId
            )
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect {
                    _recentlyAddedBooks.value = it
                }
        }
    }

    private fun getNewSeries() {
        viewModelScope.launch {
            homeScreenUseCases.getNewSeries.invoke(pageSize = PAGE_SIZE, libraryId = libraryId)
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect {
                    _newSeries.value = it
                }
        }
    }
    private fun getUpdatedSeries(){
        viewModelScope.launch {
            homeScreenUseCases.getUpdatedSeries.invoke(pageSize = PAGE_SIZE, libraryId = libraryId)
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect {
                    _updatedSeries.value = it
                }
        }
    }

}



