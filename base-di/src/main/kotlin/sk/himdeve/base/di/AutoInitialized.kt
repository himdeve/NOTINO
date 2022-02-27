package sk.himdeve.base.di

import javax.inject.Qualifier
import kotlin.reflect.KClass

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class AutoInitialized

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.FUNCTION)
annotation class AutoInitializedSet(val scope: KClass<*>)