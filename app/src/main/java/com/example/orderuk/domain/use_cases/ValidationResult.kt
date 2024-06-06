package com.example.orderuk.domain.use_cases

data class ValidationResult(
    var successful: Boolean,
    var errorMessage: String=""
)