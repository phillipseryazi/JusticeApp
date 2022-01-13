package com.mudhut.software.justiceapp.utils

import java.util.regex.Pattern

sealed class InputValidator {
    open var message: String? = null

    private fun checkLength(string: String, threshold: Int?): Boolean {
        return string.length < threshold ?: 3
    }

    open fun validate(string: String, threshold: Int?): String? {
        message = when {
            string.trim().isEmpty() -> {
                "Input can not be empty"
            }
            checkLength(string.trim(), threshold) -> {
                "Input must be at least ${threshold ?: 3} characters"
            }
            else -> {
                null
            }
        }
        return message
    }

    object EmailValidator : InputValidator() {
        private const val EMAIL_REGEX = "^(.+)@(.+)\$"
        override fun validate(string: String, threshold: Int?): String? {
            super.validate(string, threshold)
            message = if (!Pattern.matches(EMAIL_REGEX, string)) {
                "Please provide a valid email"
            } else {
                null
            }
            return message
        }
    }

    object PasswordValidator : InputValidator() {}
    object TextFieldValidator : InputValidator() {}
}
