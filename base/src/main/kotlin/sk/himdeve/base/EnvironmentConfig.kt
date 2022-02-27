package sk.himdeve.base

/**
 * Created by Robin Himdeve on 2/23/2022.
 */
object EnvironmentConfig {
    enum class Project {
        NOTINO
    }

    var impl: Impl? = null

    private val assertedImpl: Impl
        get() = impl ?: error("Impl not set")
    val debug: Boolean
        get() = assertedImpl.debug
    val project: Project
        get() = assertedImpl.app
    val versionCode: Int
        get() = assertedImpl.versionCode
    val versionName: String
        get() = assertedImpl.versionName
    val flavor: String
        get() = assertedImpl.flavor
    val apiBaseUrl: String
        get() = assertedImpl.apiBaseUrl
    val imageBaseUrl: String
        get() = assertedImpl.imageBaseUrl

    interface Impl {
        val debug: Boolean
        val app: Project
        val versionCode: Int
        val versionName: String
        val flavor: String
        val apiBaseUrl: String
        val imageBaseUrl: String
    }
}