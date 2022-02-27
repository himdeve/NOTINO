package sk.himdeve.logger

object LOG {

    var impl: Impl? = null

    fun d(message: String) = impl?.d(message)

    fun d(tag: String, message: String) = impl?.d(tag, message)

    fun i(message: String) = impl?.i(message)

    fun e(message: String) = impl?.e(message)

    fun e(e: Throwable?) = impl?.e(e)

    fun w(message: String) = impl?.w(message)

    fun w(t: Throwable?, logRemotely: Boolean = false, metadata: Metadata? = null) = impl?.w(t, logRemotely, metadata)

    interface Impl {
        fun d(message: String)
        fun d(tag: String, message: String)
        fun i(message: String)
        fun w(message: String)
        fun w(throwable: Throwable?, logRemotely: Boolean, metadata: Metadata?)
        fun e(message: String)
        fun e(throwable: Throwable?)
    }

    data class Metadata(val key: String, val value: String)
}