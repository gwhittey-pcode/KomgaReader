package org.maddiesoftware.komagareader.server_select.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.maddiesoftware.komagareader.server_select.domain.repository.ServerRepository
import org.maddiesoftware.komagareader.server_select.domain.use_case.ServerAddUseCase
import org.maddiesoftware.komagareader.server_select.presentation.state.AddServerFormState
import javax.inject.Inject

@HiltViewModel
class ServerAddViewModel @Inject constructor(
    private  val serverRepository: ServerRepository,
    private val serverAddUseCase: ServerAddUseCase,
//    private val validateUrl: ValidateUrl = ValidateUrl(),
//    private val validateServerName: ValidateServerName = ValidateServerName(),
//    private val validateUserName: ValidateUserName = ValidateUserName(),
//    private val validatePassword: ValidatePassword = ValidatePassword(),
):ViewModel() {
    var state by mutableStateOf(AddServerFormState())
    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun onEvent(event: AddServerFormEvent){
        when(event){
            is AddServerFormEvent.ServerNameChanged ->{
                state = state.copy(serverName = event.serverName)
            }
            is AddServerFormEvent.UserNameChanged -> {
                state = state.copy(username = event.userName)
            }
            is AddServerFormEvent.PasswordChanged ->{
                state = state.copy(password = event.password)
            }
            is AddServerFormEvent.UrlChanged ->{
                state = state.copy(url = event.url)
            }
            is AddServerFormEvent.Submit ->{
                submitData()
            }
        }
    }

    private fun submitData() {
        val serverNameResult = serverAddUseCase.validateServerName.execute(state.serverName)
        val userNameResult = serverAddUseCase.validateUserName.execute(state.username)
        val passwordResult = serverAddUseCase.validatePassword.execute(state.password)
        val urlResult = serverAddUseCase.validateUrl.execute(state.url)
        val hasError = listOf(
            serverNameResult,
            urlResult,
            passwordResult,
            urlResult
        ).any { !it.success }

        if (hasError){
            state = state.copy(
                serverNameError = serverNameResult.errorMessage,
                usernameError = userNameResult.errorMessage,
                passwordError = passwordResult.errorMessage,
                urlError = urlResult.errorMessage
            )
            return
        }
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }
    }

//    init {
//        viewModelScope.launch {}
//    }
//      fun onAdd(
//        serverId: Int? = null,
//        serverName: String,
//        username:String,
//        password: String,
//        url:String
//    ){
//
//          viewModelScope.launch(Dispatchers.IO) {
//              serverRepository.insertServer(
//                  Server(
//                      serverId = serverId,
//                      serverName = serverName,
//                      userName = username,
//                      password = password,
//                      url = url
//
//                  )
//              )
//          }
//    }
    sealed class ValidationEvent{
        object Success: ValidationEvent()
    }
}