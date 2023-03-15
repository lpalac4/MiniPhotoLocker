package com.mora.minikeepsafe.data

sealed class Response<out T> {
    data class Success<out U>(val data: U): Response<U>()
    data class Error(val e: Exception) : Response<Nothing>()
    object Processing: Response<Nothing>()
}