package dev.rustybite.rustygram.util

sealed class RustyResult<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : RustyResult<T>(data = data)
    class Failure<String>(message: String) : RustyResult<String>(message)
    data object Loading : RustyResult<Nothing>()
}