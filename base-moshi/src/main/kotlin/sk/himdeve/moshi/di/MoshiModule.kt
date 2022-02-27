package sk.himdeve.moshi.di

import sk.himdeve.base.di.AppScope
import com.squareup.anvil.annotations.ContributesTo
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides

/**
 * Created by Robin Himdeve on 2/23/2022.
 */
@Module
@ContributesTo(AppScope::class)
object MoshiModule {
    @AppScope
    @JvmStatic
    @Provides
    fun moshi(
    ): Moshi {
        return Moshi.Builder()
            .build()
    }
}