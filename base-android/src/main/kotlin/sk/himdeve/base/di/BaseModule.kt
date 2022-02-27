package sk.himdeve.base.di

import sk.himdeve.base.DispatcherProvider
import sk.himdeve.base.util.DispatcherProviderImpl
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides

@Module
@ContributesTo(AppScope::class)
object DispatcherModule {

    @AppScope
    @JvmStatic
    @Provides
    fun dispatcherProvider(): DispatcherProvider = DispatcherProviderImpl()
}