package org.maddiesoftware.komagareader.server_display.presentaion.screen.allseries

import android.util.Log
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
import org.maddiesoftware.komagareader.server_display.domain.model.Series
import org.maddiesoftware.komagareader.server_display.domain.usecase.AllSeriesUseCases
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
        Log.d("Komga1234","ViewModel Load")
        savedStateHandle.get<String>(key = LIBRARY_ID_KEY)?.let { it ->
            libraryId = it
            Log.d("komga12345","pre Libraryid = $libraryId")
            if(libraryId == "null"){
                libraryId = null
                Log.d("komga12345","reassigned Libraryid = $libraryId")
            }
        }
        Log.d("komaga12345","aftte libary = $libraryId")
        viewModelScope.launch {
            allSeriesUseCases.getAllSeries.invoke(pageSize = PAGE_SIZE, libraryId = libraryId)
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect {
                    _seriesState.value = it
                }
            Log.d("Komga1234","view ${_seriesState.value}")
            }

    }

}