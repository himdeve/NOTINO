package sk.himdeve.base.util

import android.os.Looper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import sk.himdeve.base.DispatcherProvider
import sk.himdeve.base.EnvironmentConfig

internal class DispatcherProviderImpl : DispatcherProvider {
    override val io: CoroutineDispatcher = Dispatchers.IO
    override val main: CoroutineDispatcher = Dispatchers.Main
    override val default: CoroutineDispatcher = Dispatchers.Default

    override fun assertMainThread() {
        if (EnvironmentConfig.debug && Looper.myLooper() != Looper.getMainLooper()) {
            error("Cannot this off main thread")
        }
    }
}