package com.bps.plantseeds3.domain.model

sealed class Resource<out T> {
    data class Success<T>(val data: T) : Resource<T>()
    data class Error<T>(val message: String? = null) : Resource<T>()
} 