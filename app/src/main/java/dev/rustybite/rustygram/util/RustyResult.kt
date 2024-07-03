package dev.rustybite.rustygram.util

sealed class RustyResult<T>(data: T? = null, message: String? = null) {
    data class Success<T>(val data: T) : RustyResult<T>(data = data)
    data class Failure<T>(val message: String) : RustyResult<T>(message = message)
    class Loading<T> : RustyResult<T>()
}