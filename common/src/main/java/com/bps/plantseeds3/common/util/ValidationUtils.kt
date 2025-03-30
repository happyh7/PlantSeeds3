package com.bps.plantseeds3.common.util

object ValidationUtils {
    fun isValidName(name: String?): Boolean {
        return !name.isNullOrBlank() && name.length >= 2 && name.length <= 50
    }

    fun isValidDescription(description: String?): Boolean {
        return !description.isNullOrBlank() && description.length <= 500
    }

    fun isValidNumber(value: String?): Boolean {
        return try {
            value?.toFloatOrNull() != null
        } catch (e: Exception) {
            false
        }
    }

    fun isValidPositiveNumber(value: String?): Boolean {
        return try {
            value?.toFloatOrNull()?.let { it > 0 } ?: false
        } catch (e: Exception) {
            false
        }
    }

    fun isValidEmail(email: String?): Boolean {
        return !email.isNullOrBlank() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidPassword(password: String?): Boolean {
        return !password.isNullOrBlank() && password.length >= 6
    }

    fun isValidList(list: List<String>?): Boolean {
        return !list.isNullOrEmpty() && list.all { it.isNotBlank() }
    }

    fun sanitizeInput(input: String?): String {
        return input?.trim() ?: ""
    }
} 