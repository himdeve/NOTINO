package sk.himdeve.base.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class BaseOkHttp

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class BaseRetrofit