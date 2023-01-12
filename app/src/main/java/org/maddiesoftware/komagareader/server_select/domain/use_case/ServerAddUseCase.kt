package org.maddiesoftware.komagareader.server_select.domain.use_case

data class ServerAddUseCase(
    val validateServerName: ValidateServerName,
    val validateUserName: ValidateUserName,
    val validatePassword: ValidatePassword,
    val validateUrl: ValidateUrl
)
