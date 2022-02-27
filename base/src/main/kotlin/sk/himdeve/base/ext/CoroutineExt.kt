package sk.himdeve.base.ext

import sk.himdeve.base.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

fun <T> simpleFlow(action: suspend () -> T): Flow<T> {
    return flow { emit(action()) }
}

fun <T> signalFlow(action: suspend () -> T): Flow<Signal<T>> {
    return simpleFlow(action)
        .asSignal()
}

fun <T> Flow<T>.replayingShare(scope: CoroutineScope = GlobalScope): Flow<T> {
    return shareIn(scope = scope, started = SharingStarted.WhileSubscribed(), replay = 1)
}

fun <T> Flow<T>.asSignal(): Flow<Signal<T>> {
    return map<T, Signal<T>> { Success(it) }
        .catch { emit(Fail(it)) }
        .onStart { emit(Loading) }
}