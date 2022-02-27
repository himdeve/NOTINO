package sk.himdeve.base

interface Complete

interface Incomplete
sealed class Signal<out T> {
    open operator fun invoke(): T? = null
}

object Uninitialized : Signal<Nothing>(), Incomplete

object Loading : Signal<Nothing>(), Incomplete

data class Success<T>(val value: T) : Signal<T>(), Complete {
    override operator fun invoke(): T? = value
}

data class Fail(val throwable: Throwable) : Signal<Nothing>(), Complete