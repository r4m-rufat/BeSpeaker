package com.codingwithrufat.bespeaker.common.utils

sealed class NetworkResponse<out T>(val data: T? = null, val message: String? = null) {

    class SUCCEED<out T>(data: T): NetworkResponse<T>(data)
    class ERROR<out T>(msg: String): NetworkResponse<T>(null, msg)
    class LOADING<out T>: NetworkResponse<T>()

}