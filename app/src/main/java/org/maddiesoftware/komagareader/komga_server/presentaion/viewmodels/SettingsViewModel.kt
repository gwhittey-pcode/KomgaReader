package org.maddiesoftware.komagareader.komga_server.presentaion.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.maddiesoftware.komagareader.core.data.local.ReaderPreferenceSingleton
import org.maddiesoftware.komagareader.komga_server.data.DataStoreManager
import org.maddiesoftware.komagareader.komga_server.persitance.PreferenceKeys
import org.maddiesoftware.komagareader.komga_server.presentaion.state.SettingsState
import javax.inject.Inject


@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dataStore: DataStoreManager
) : ViewModel() {
    var state by mutableStateOf(SettingsState())
    init {
        readUseDblPageSplit()
    }
    private fun writeUseDblPageSplit(value:Boolean){
        viewModelScope.launch {
            dataStore.storeValue(PreferenceKeys.DBL_PAGE_SPLIT,value)
            ReaderPreferenceSingleton.useDblPageSplit = value
        }
    }
    private fun readUseDblPageSplit() {
        viewModelScope.launch {
            dataStore.readValue(PreferenceKeys.DBL_PAGE_SPLIT) {
                ReaderPreferenceSingleton.useDblPageSplit = this
                state = state.copy(
                    useDblPageSplit = this,
                )
            }
        }
    }
}
