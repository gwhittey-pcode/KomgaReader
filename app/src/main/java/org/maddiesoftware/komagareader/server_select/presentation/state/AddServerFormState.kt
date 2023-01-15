package org.maddiesoftware.komagareader.server_select.presentation.state

data class AddServerFormState(
    val serverName: String = "",
    val serverNameError:String? = null,
    val username:String = "",
    val usernameError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val url:String = "",
    val urlError: String? = null,
    var addServerDone: Boolean = false
)
