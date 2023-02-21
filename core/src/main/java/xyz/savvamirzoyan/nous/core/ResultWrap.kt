package xyz.savvamirzoyan.nous.core

sealed interface ResultWrap<T> {

    class Success<T>(val data: T) : ResultWrap<T>
    class Failure<T>(val error: ErrorEntity) : ResultWrap<T>

    fun get(): T? = when (this) {
        is Success -> data
        is Failure -> null
    }

    val isSuccessful: Boolean
        get() = this is Success

    fun onError(callback: (error: ErrorEntity) -> Unit): ResultWrap<T> {
        if (this is Failure) {
            callback.invoke(error)
        }

        return this
    }

    fun <R> map(mapper: (T) -> R): ResultWrap<R> {
        return when (this) {
            is Failure -> Failure(error)
            is Success -> Success(mapper(data))
        }
    }
}