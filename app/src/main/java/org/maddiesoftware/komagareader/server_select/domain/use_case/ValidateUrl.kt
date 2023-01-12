package org.maddiesoftware.komagareader.server_select.domain.use_case

class ValidateUrl {
    fun execute(url:String): ValidationResult {
        if(url.isBlank()){
            return ValidationResult(
                success = false,
                errorMessage = "Url Can Not Be Blank"
            )
        }
        if (!url.endsWith("/")){
            return ValidationResult(
                success = false,
                errorMessage = "Url must end with a /"
            )
        }
        return ValidationResult(
            success = true
        )

    }
}