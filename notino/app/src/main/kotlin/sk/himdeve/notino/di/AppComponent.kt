package sk.himdeve.notino.di

import android.content.Context
import sk.himdeve.base.Scoped
import sk.himdeve.base.di.AppScope
import sk.himdeve.base.di.AutoInitializedSet
import sk.himdeve.base.di.ComponentInitializer
import com.squareup.anvil.annotations.ContributesTo
import com.squareup.anvil.annotations.MergeComponent
import dagger.BindsInstance
import dagger.Component

/**
 * Created by Robin Himdeve on 2/23/2022.
 */
@AppScope
@MergeComponent(AppScope::class)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}

object AppComponentInitializer : ComponentInitializer<AppComponent> {
    override fun init(component: AppComponent) {
        (component as AppComponentWorkaround).scoped.forEach { it.init() }
    }

    override fun clear(component: AppComponent) {
        (component as AppComponentWorkaround).scoped.forEach { it.clear() }
    }
}

@ContributesTo(AppScope::class)
interface AppComponentWorkaround {
    @get:AutoInitializedSet(AppScope::class) val scoped: Set<Scoped>
}