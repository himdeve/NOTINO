package sk.himdeve.notino

import sk.himdeve.base.EnvironmentConfig

/**
 * Created by Robin Himdeve on 2/23/2022.
 */
class EnvironmentConfigImpl : EnvironmentConfig.Impl {
    override val debug = BuildConfig.DEBUG
    override val app = EnvironmentConfig.Project.NOTINO
    override val versionCode = BuildConfig.VERSION_CODE
    override val versionName = BuildConfig.VERSION_NAME
    override val flavor = BuildConfig.FLAVOR
    override val apiBaseUrl = BuildConfig.ApiBaseUrl
    override val imageBaseUrl = BuildConfig.ImageBaseUrl
}