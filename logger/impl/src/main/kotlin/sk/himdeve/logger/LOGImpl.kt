package sk.himdeve.logger

import android.util.Log
import sk.himdeve.logger.LOG.Metadata

private const val TAG = "Default"

class LOGImpl(private val enabled: Boolean) : LOG.Impl {

    override fun d(message: String) {
        if (enabled) Log.d(TAG, message)
    }

    override fun d(tag: String, message: String) {
        if (enabled) Log.d(tag, message)
    }

    override fun i(message: String) {
        if (enabled) Log.i(TAG, message)
    }

    override fun w(message: String) {
        if (enabled) Log.w(TAG, message)
    }

    override fun w(throwable: Throwable?, logRemotely: Boolean, metadata: Metadata?) {
        if (enabled) Log.w(TAG, throwable?.message, throwable)
    }

    override fun e(message: String) {
        if (enabled) Log.e(TAG, message)
    }

    override fun e(throwable: Throwable?) {
        if (enabled) Log.e(TAG, throwable?.message, throwable)
    }
}