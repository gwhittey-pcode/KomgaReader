package org.maddiesoftware.komagareader.server_select.domain.use_case

class ValidateUserName {
    fun execute(username:String): ValidationResult {
        if(username.isBlank()){
            return ValidationResult(
                success = false,
                errorMessage = "User Name Can Not Be Blank"
            )
        }
        return ValidationResult(
            success = true
        )

    }
}