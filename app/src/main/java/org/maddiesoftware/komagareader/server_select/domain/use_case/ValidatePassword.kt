package org.maddiesoftware.komagareader.server_select.domain.use_case

class ValidatePassword {
    fun execute(password:String): ValidationResult {
        if(password.isBlank()){
            return ValidationResult(
                success = false,
                errorMessage = "Password Can Not Be Blank"
            )
        }
        return ValidationResult(
            success = true
        )

    }
}