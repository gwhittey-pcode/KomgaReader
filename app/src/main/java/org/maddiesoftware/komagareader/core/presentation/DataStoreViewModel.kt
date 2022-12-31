package org.maddiesoftware.komagareader.core.presentation

import android.icu.lang.UProperty.AGE
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.maddiesoftware.komagareader.core.data.datastore.DataStoreRepository
import javax.inject.Inject

@HiltViewModel
class DataStoreViewModel @Inject constructor(
    private val repository: DataStoreRepository
) : ViewModel() {

    fun saveServerName(value: String) {
        viewModelScope.launch {
            repository.putString("userName", value)
        }
    }

    fun getServerName(): String? = runBlocking {
        repository.getString("userName")
    }

    fun saveUserName(value: String) {
        viewModelScope.launch {
            repository.putString("userName", value)
        }
    }

    fun getUserName(): String? = runBlocking {
        repository.getString("userName")
    }

    fun savePassword(value: String) {
        viewModelScope.launch {
            repository.putString("password", value)
        }
    }

    fun getPassword(): String? = runBlocking {
        repository.getString("password")
    }

    fun saveUrl(value: String) {
        viewModelScope.launch {
            repository.putString("url", value)
        }
    }

    fun getUrl(): String? = runBlocking {
        repository.getString("url")
    }
}