package com.bps.plantseeds3.common.util

object ValidationUtils {
    fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
        return email.matches(emailRegex.toRegex())
    }

    fun isValidPassword(password: String): Boolean {
        return password.length >= 8 && // Minst 8 tecken
               password.any { it.isDigit() } && // Minst en siffra
               password.any { it.isUpperCase() } && // Minst en stor bokstav
               password.any { it.isLowerCase() } // Minst en liten bokstav
    }

    fun isValidName(name: String): Boolean {
        return name.trim().length >= 2 && // Minst 2 tecken
               name.all { it.isLetter() || it.isWhitespace() } // Bara bokstäver och mellanslag
    }

    fun isValidPhoneNumber(phone: String): Boolean {
        val phoneRegex = "^[+]?[0-9]{10,13}$" // +XX eller 07X följt av 8-10 siffror
        return phone.matches(phoneRegex.toRegex())
    }

    fun getPasswordRequirements(): String {
        return """
            Lösenordet måste innehålla:
            - Minst 8 tecken
            - Minst en siffra
            - Minst en stor bokstav
            - Minst en liten bokstav
        """.trimIndent()
    }
} 