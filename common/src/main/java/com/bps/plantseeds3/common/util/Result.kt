package com.bps.plantseeds3.common.util

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
    object Loading : Result<Nothing>()
}

suspend fun <T> safeCall(apiCall: suspend () -> T): Result<T> {
    return try {
        Result.Success(apiCall.invoke())
    } catch (throwable: Exception) {
        Result.Error(throwable)
    }
}

fun <T> Result<T>.handle(
    onSuccess: (T) -> Unit,
    onError: (Exception) -> Unit,
    onLoading: () -> Unit
) {
    when (this) {
        is Result.Success -> onSuccess(data)
        is Result.Error -> onError(exception)
        is Result.Loading -> onLoading()
    }
} 