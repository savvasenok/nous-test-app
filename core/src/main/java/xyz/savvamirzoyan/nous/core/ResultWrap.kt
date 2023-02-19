package xyz.savvamirzoyan.nous.core

sealed interface ResultWrap<T> {

    class Success<T>(val data: T) : ResultWrap<T>
    class Failure<T>(val error: ErrorEntity) : ResultWrap<T>

    fun get(): T? = when (this) {
        is Success -> data
        is Failure -> null
    }

    fun isSuccessful(): Boolean = this is Success
}