package org.maddiesoftware.komagareader.server_select.domain.use_case

data class ValidationResult(
    val success: Boolean,
    val errorMessage: String? = null
)
