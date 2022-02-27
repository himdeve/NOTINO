package sk.himdeve.net.di

import sk.himdeve.base.EnvironmentConfig
import sk.himdeve.base.di.AppScope
import sk.himdeve.base.di.BaseOkHttp
import sk.himdeve.base.di.BaseRetrofit
import com.squareup.anvil.annotations.ContributesTo
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import sk.himdeve.logger.LOG
import sk.himdeve.net.*

/**
 * Created by Robin Himdeve on 2/23/2022.
 */
@Module
@ContributesTo(AppScope::class)
object NetModule {
    @JvmStatic
    @Provides
    fun apiHelper(moshi: Moshi): ApiHelper = ApiHelper(moshi)

    @AppScope
    @Provides
    @JvmStatic
    fun moshiConverterFactory(moshi: Moshi): MoshiConverterFactory {
        return MoshiConverterFactory.create(moshi)
    }

    @AppScope
    @BaseOkHttp
    @JvmStatic
    @Provides
    fun baseOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            if (EnvironmentConfig.debug) {
                addNetworkInterceptor(
                    HttpLoggingInterceptor { message -> LOG.d("Api", message) }
                        .apply { level = HttpLoggingInterceptor.Level.BASIC })
            }
        }.build()
    }

    @AppScope
    @JvmStatic
    @Provides
    @BaseRetrofit
    fun baseRetrofit(
        @BaseOkHttp apiOkHttpClient: OkHttpClient,
        moshiConverterFactory: MoshiConverterFactory,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(EnvironmentConfig.apiBaseUrl)
            .client(apiOkHttpClient)
            .addConverterFactory(moshiConverterFactory)
            .build()
    }
}