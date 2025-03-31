package com.bps.plantseeds3.common.constants

object AppConstants {
    // API endpoints
    const val BASE_URL = "https://api.plantseeds3.com/"
    const val API_VERSION = "v1"
    
    // Database
    const val DATABASE_NAME = "plantseeds3_db"
    const val DATABASE_VERSION = 1
    
    // Preferences
    const val PREFERENCES_NAME = "plantseeds3_preferences"
    
    // Pagination
    const val PAGE_SIZE = 20
    const val INITIAL_PAGE = 1
    
    // Timeouts
    const val NETWORK_TIMEOUT = 30L // seconds
    const val CACHE_TIMEOUT = 24L * 60L * 60L // 24 hours in seconds
    
    // File handling
    const val MAX_IMAGE_SIZE = 5L * 1024L * 1024L // 5MB in bytes
    const val SUPPORTED_IMAGE_TYPES = "image/jpeg,image/png,image/webp"
    
    // UI
    const val MIN_SEARCH_LENGTH = 2
    const val DEBOUNCE_TIME = 300L // milliseconds
} 