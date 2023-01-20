package org.maddiesoftware.komagareader.komga_server.presentaion.viewmodels

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
import org.maddiesoftware.komagareader.komga_server.domain.model.Series
import org.maddiesoftware.komagareader.komga_server.domain.repository.ApiRepository
import org.maddiesoftware.komagareader.komga_server.domain.use_case.collections.CollectionUseCases
import org.maddiesoftware.komagareader.komga_server.presentaion.state.CollectionByIdState
import javax.inject.Inject


@HiltViewModel
class CollectionByIdViewModel @Inject constructor(
    private val apiRepository: ApiRepository,
    savedStateHandle: SavedStateHandle,
    private val collectionUseCases: CollectionUseCases
): ViewModel() {
    private var libraryId: String? = null
    private var collectionId:String = ""
    private val _collectionState: MutableStateFlow<PagingData<Series>> =
        MutableStateFlow(value = PagingData.empty())
    val collectionState: StateFlow<PagingData<Series>>
        get() = _collectionState
    var collectionByIdState by mutableStateOf(CollectionByIdState())

    init {
        savedStateHandle.get<String>(key = "collectionId")?.let { it ->
            collectionId = it

        }

        getBooksFromReadList()
    }

    private fun getBooksFromReadList(){
        viewModelScope.launch {
            collectionUseCases.getSeriesFromCollection.invoke(pageSize = PAGE_SIZE, collectionId = collectionId, libraryId = libraryId)
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect {
                    _collectionState.value = it
                }
        }

    }

    private fun getReadListById(){
        viewModelScope.launch {
            collectionByIdState = collectionByIdState.copy(isLoading = true)
            val collectionByIdResult = async { apiRepository.getCollectionById(collectionId = collectionId.toString()) }
            when(val result = collectionByIdResult.await()) {
                is Resource.Success -> {
                    collectionByIdState = collectionByIdState.copy(
                        collectionInfo = result.data,
                        isLoading = false,
                        error = null
                    )
                }
                is Resource.Error -> {
                    collectionByIdState = collectionByIdState.copy(
                        isLoading = false,
                        error = result.message,
                        collectionInfo = null
                    )
                }
                else -> Unit
            }
        }
    }

}