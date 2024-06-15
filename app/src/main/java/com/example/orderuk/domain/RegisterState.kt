package com.example.orderuk.domain

import com.example.orderuk.domain.use_cases.ValidationResult

data class RegisterState(
    val email: String="",
    val password: String = "",
    val emailResult:ValidationResult= ValidationResult(successful = false, errorMessage = "Email is required"),
    val passwordResult: ValidationResult = ValidationResult(successful = false, errorMessage = "Password is required" ),
    val firstTime: Boolean = true
)
