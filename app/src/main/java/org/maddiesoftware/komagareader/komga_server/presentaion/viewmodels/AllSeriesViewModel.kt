package org.maddiesoftware.komagareader.komga_server.presentaion.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.maddiesoftware.komagareader.core.util.LIBRARY_ID_KEY
import org.maddiesoftware.komagareader.core.util.PAGE_SIZE
import org.maddiesoftware.komagareader.komga_server.domain.model.Series
import org.maddiesoftware.komagareader.komga_server.domain.use_case.AllSeriesUseCases
import javax.inject.Inject


@HiltViewModel
class AllSeriesViewModel @Inject constructor(
    private val allSeriesUseCases: AllSeriesUseCases,
    savedStateHandle: SavedStateHandle


): ViewModel() {
    private var libraryId: String? = null
    private val _seriesState: MutableStateFlow<PagingData<Series>> =
        MutableStateFlow(value = PagingData.empty())
    val seriesState: StateFlow<PagingData<Series>>
        get() = _seriesState

    init {
        savedStateHandle.get<String>(key = LIBRARY_ID_KEY)?.let { it ->
            libraryId = it
            if(libraryId == "null"){
                libraryId = null
            }
        }
        viewModelScope.launch {
            allSeriesUseCases.getAllSeries.invoke(pageSize = PAGE_SIZE, libraryId = libraryId)
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect {
                    _seriesState.value = it
                }
            }

    }

}