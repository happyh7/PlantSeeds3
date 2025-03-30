package com.bps.plantseeds3.common.config

object AppConfig {
    const val DATABASE_NAME = "plantseeds3.db"
    const val DATABASE_VERSION = 1

    object Preferences {
        const val PREF_NAME = "plantseeds3_prefs"
        const val KEY_THEME_MODE = "theme_mode"
        const val KEY_LANGUAGE = "language"
    }

    object Network {
        const val BASE_URL = "https://api.plantseeds3.com"
        const val TIMEOUT_SECONDS = 30L
    }
} 