package org.maddiesoftware.komagareader.server_select.domain.use_case

import android.util.Patterns
import android.webkit.URLUtil

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
        if(isUrlValid(url)){
            return ValidationResult(
                success = false,
                errorMessage = "Url not valid/"
            )
        }
        return ValidationResult(
            success = true
        )
    }
    fun isUrlValid(url: String?): Boolean {
        url ?: return false
        return Patterns.WEB_URL.matcher(url).matches() && URLUtil.isValidUrl(url)
    }
}