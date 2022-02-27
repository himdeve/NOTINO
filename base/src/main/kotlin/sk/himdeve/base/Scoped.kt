package sk.himdeve.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface Scoped {
    fun init()
    fun clear()
}

abstract class AbstractScoped(dispatcher: CoroutineDispatcher) : Scoped {
    protected val scope = SupervisedScope(dispatcher)

    override fun clear() {
        scope.cancel()
    }
}

interface StatefulScoped<S> : Scoped {
    val state: Flow<S>
}

abstract class AbstractStatefulScoped<S>(
    initialState: S,
    dispatcher: CoroutineDispatcher
) : AbstractScoped(dispatcher), StatefulScoped<S> {

    private val _state = MutableStateFlow(initialState)
    override val state: Flow<S> get() = _state

    protected open fun setState(reducer: S.() -> S): Boolean {
        return _state.set(reducer)
    }

    protected fun state(): S {
        return _state.value
    }
}

fun SupervisedScope(coroutineDispatcher: CoroutineDispatcher): CoroutineScope =
    CoroutineScope(SupervisorJob() + coroutineDispatcher)

fun <T> MutableStateFlow<T>.set(reduce: T.() -> T): Boolean {
    return synchronized(this) {
        unsafeSet(reduce)
    }
}

fun <T> MutableStateFlow<T>.unsafeSet(reduce: T.() -> T): Boolean {
    val currentValue = value
    val newValue = currentValue.reduce()
    return if (currentValue !== newValue) {
        value = newValue
        true
    } else {
        false
    }
}