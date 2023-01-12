package org.maddiesoftware.komagareader.server_select.domain.use_case

class ValidateServerName {
    fun execute(serverName:String): ValidationResult {
        if(serverName.isBlank()){
            return ValidationResult(
                success = false,
                errorMessage = "Server Name Can Not Be Blank"
            )
        }
        return ValidationResult(
            success = true
        )

    }
}