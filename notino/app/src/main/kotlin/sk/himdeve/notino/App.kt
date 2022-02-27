package sk.himdeve.notino

import android.app.Application
import sk.himdeve.base.EnvironmentConfig
import sk.himdeve.base.di.Components
import sk.himdeve.logger.LOG
import sk.himdeve.logger.LOGImpl
import sk.himdeve.notino.di.AppComponentInitializer
import sk.himdeve.notino.di.DaggerAppComponent

/**
 * Created by Robin Himdeve on 2/23/2022.
 */
class App: Application() {
    override fun onCreate() {
        LOG.i("App # onCreate")
        super.onCreate()

        val appComponent = DaggerAppComponent.factory().create(this)
        Components.transaction {
            add(appComponent, AppComponentInitializer)
        }
    }

    companion object {

        init {
            EnvironmentConfig.impl = EnvironmentConfigImpl()
            LOG.impl = LOGImpl(enabled = BuildConfig.DEBUG)
        }
    }
}