package com.bps.plantseeds3.common.config

object AppConfig {
    const val APP_NAME = "PlantSeeds3"
    const val APP_VERSION = "1.0.0"
    const val DATABASE_NAME = "plantseeds3.db"
    const val DATABASE_VERSION = 1

    object Api {
        const val BASE_URL = "https://api.plantseeds3.com/"
        const val TIMEOUT = 30L
    }

    object Validation {
        const val MIN_NAME_LENGTH = 2
        const val MAX_NAME_LENGTH = 50
        const val MAX_DESCRIPTION_LENGTH = 500
        const val MIN_PASSWORD_LENGTH = 6
    }

    object UI {
        const val ANIMATION_DURATION = 300L
        const val DEFAULT_PADDING = 16
        const val DEFAULT_SPACING = 8
    }
} 