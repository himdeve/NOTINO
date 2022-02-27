package sk.himdeve.testutils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import sk.himdeve.base.DispatcherProvider

/**
 * Created by Robin Himdeve on 2/23/2022.
 */
class TestDispatcherProvider : DispatcherProvider {
    override val io: CoroutineDispatcher = Dispatchers.Unconfined
    override val main: CoroutineDispatcher = Dispatchers.Unconfined
    override val default: CoroutineDispatcher = Dispatchers.Unconfined

    override fun assertMainThread() {}
}