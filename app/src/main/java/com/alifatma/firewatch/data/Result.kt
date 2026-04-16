package com.alifatma.firewatch.data

sealed class Result<out T> {

    enum class ErrorType {
        NETWORK,
        HTTP,
        UNKNOWN
    }

    data class Success<out T>(
        val data: T
    ) : Result<T>()

    data class Error(
        val message: String,
        val exception: Throwable? = null,
        val errorType: ErrorType = ErrorType.UNKNOWN
    ) : Result<Nothing>()

    object Loading : Result<Nothing>()
}