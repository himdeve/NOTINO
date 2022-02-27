package sk.himdeve.notino.products.di

import sk.himdeve.base.DispatcherProvider
import sk.himdeve.base.EnvironmentConfig
import sk.himdeve.base.di.AppScope
import sk.himdeve.base.di.AutoInitialized
import sk.himdeve.base.di.BaseRetrofit
import sk.himdeve.net.ApiHelper
import sk.himdeve.notino.products.*
import sk.himdeve.notino.products.db.ProductQueries
import sk.himdeve.notino.products.remote.ProductApi
import sk.himdeve.notino.products.remote.ProductApiClientImpl
import sk.himdeve.sqldelight.DaoTransactor
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

/**
 * Created by Robin Himdeve on 2/24/2022.
 */
@Module
@ContributesTo(AppScope::class)
object ProductModule {

    @AutoInitialized
    @Provides
    @JvmStatic
    @AppScope
    fun productSyncer(
        dispatcherProvider: DispatcherProvider,
        productRepository: ProductRepository
    ): ProductSyncer {
        return ProductSyncerImpl(
            false,
            dispatcherProvider,
            productRepository
        )
    }

    @AutoInitialized
    @Provides
    @JvmStatic
    @AppScope
    fun productRepository(
        @BaseRetrofit retrofit: Retrofit,
        apiHelper: ApiHelper,
        daoTransactor: DaoTransactor,
        productDao: ProductDao,
        dispatcherProvider: DispatcherProvider
    ): ProductRepository {
        val imageBaseUrl = EnvironmentConfig.imageBaseUrl
        val api = retrofit.create(ProductApi::class.java)
        val apiClient = ProductApiClientImpl(api, apiHelper)
        return ProductRepositoryImpl(imageBaseUrl, daoTransactor, apiClient, productDao, dispatcherProvider)
    }

    @AppScope
    @Provides
    @JvmStatic
    fun productDao(productQueries: ProductQueries, dispatcherProvider: DispatcherProvider): ProductDao {
        return ProductDaoImpl(productQueries, dispatcherProvider)
    }
}