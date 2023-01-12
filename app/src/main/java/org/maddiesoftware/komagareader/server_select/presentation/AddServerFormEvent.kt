package org.maddiesoftware.komagareader.server_select.presentation

sealed class AddServerFormEvent {
    data class ServerNameChanged(val serverName: String): AddServerFormEvent()
    data class UserNameChanged(val userName: String): AddServerFormEvent()
    data class PasswordChanged(val password: String): AddServerFormEvent()
    data class UrlChanged(val url: String): AddServerFormEvent()

    object Submit: AddServerFormEvent()
}
