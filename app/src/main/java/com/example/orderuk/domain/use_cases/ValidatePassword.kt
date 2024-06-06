package com.example.orderuk.domain.use_cases

class ValidatePassword {
    fun validate(password: String): ValidationResult {
        if (password.isEmpty()) {
            return ValidationResult(successful = false, errorMessage = "Password can not be blank")
        }
        val containsChar = password.any {
            it.isDigit()
        } && password.any {
            it.isUpperCase()
        } && password.any {
            it.isLowerCase()
        }
        if (password.length < 8) {
            return ValidationResult(successful = false, errorMessage = "Minimum of 8 characters")
        }
        if (!containsChar) {
            return ValidationResult(
                successful = false,
                errorMessage = "Password must contain one number, one uppercase and one lowercase letter"
            )
        }
        return ValidationResult(successful = true)
    }
}