package com.example.orderuk.domain.use_cases

import android.util.Patterns
import androidx.compose.ui.res.stringResource
import java.util.regex.Pattern

class ValidateEmail {
    fun validate(email: String): ValidationResult {
        if (email.isBlank()) {
            return ValidationResult(successful = false, errorMessage = "The email can not be empty")
        }
        if (!Pattern.matches(Patterns.EMAIL_ADDRESS.pattern(), email)) {
            return ValidationResult(successful = false, errorMessage = "Invalid email")
        }
        return ValidationResult(successful = true)
    }
}