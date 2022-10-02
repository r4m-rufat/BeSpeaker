package com.codingwithrufat.bespeaker.common

sealed class NetworkResponse<out T>(val data: T? = null, val message: String? = null) {

    class Success<out T>(data: T): NetworkResponse<T>(data)
    class Error<out T>(msg: String): NetworkResponse<T>(null, msg)
    class Loading<out T>(): NetworkResponse<T>()

}