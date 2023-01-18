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
import org.maddiesoftware.komagareader.komga_server.domain.model.ReadList
import org.maddiesoftware.komagareader.komga_server.domain.use_case.readlists.ReadListUseCases
import javax.inject.Inject

@HiltViewModel
class AllReadListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    readListUseCases: ReadListUseCases,

): ViewModel() {

    private var libraryId: String? = null
    private val _readListState: MutableStateFlow<PagingData<ReadList>> =
        MutableStateFlow(value = PagingData.empty())
    val readListState: StateFlow<PagingData<ReadList>>
        get() = _readListState


    init {
        savedStateHandle.get<String>(key = LIBRARY_ID_KEY)?.let { it ->
            libraryId = it
            if(libraryId == "null"){
                libraryId = null
            }
        }
        viewModelScope.launch {
            readListUseCases.getAllReadList.invoke(pageSize = PAGE_SIZE, libraryId = libraryId)
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect {
                    _readListState.value = it
                }
        }

    }
}