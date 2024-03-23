package kz.kasip.data

sealed class Resource<T> {
    data class Failure<T>(val e: Throwable) : Resource<T>()

    data class Success<T>(val value: T) : Resource<T>()
}