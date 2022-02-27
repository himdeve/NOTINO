package sk.himdeve.notino.navigation.di

import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import sk.himdeve.base.DispatcherProvider
import sk.himdeve.base.di.AppScope
import sk.himdeve.base.di.AutoInitialized
import sk.himdeve.notino.navigation.Navigator
import sk.himdeve.notino.navigation.NavigatorImpl

/**
 * Created by Robin Himdeve on 2/23/2022.
 */
@Module
@ContributesTo(AppScope::class)
object NavigatorModule {

    @AutoInitialized
    @AppScope
    @JvmStatic
    @Provides
    fun navigator(dispatcherProvider: DispatcherProvider): Navigator {
        return NavigatorImpl(dispatcherProvider)
    }
}
