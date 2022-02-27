package sk.himdeve.notino.products

import sk.himdeve.base.AbstractScoped
import sk.himdeve.base.DispatcherProvider
import sk.himdeve.base.ext.replayingShare
import sk.himdeve.notino.products.remote.ProductApiClient
import sk.himdeve.sqldelight.DaoTransactor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

/**
 * Created by Robin Himdeve on 2/23/2022.
 */
class ProductRepositoryImpl(
    private val imageBaseUrl: String,
    private val daoTransactor: DaoTransactor,
    private val productApiClient: ProductApiClient,
    private val productDao: ProductDao,
    private val dispatcherProvider: DispatcherProvider
) : ProductRepository, AbstractScoped(dispatcherProvider.io) {
    override fun init() {}

    override val products: Flow<List<Product>> by lazy {
        productDao.products()
            .replayingShare(scope)
    }

    override suspend fun syncProducts() = withContext(dispatcherProvider.io) {
        val apiProducts = productApiClient.products()
        val products = ProductMapper(apiProducts, imageBaseUrl)

        daoTransactor.transaction {
            productDao.saveProducts(products)
            // TODO: Consider to save syncTimestamp and use TTL - depends on the internal business configuration
        }
    }

    override suspend fun updateFavourite(productId: ProductId, favourite: Boolean) {
        // TODO: This should call a backend API not a local database! It's here just for the demo purposes.
        daoTransactor.transaction {
            productDao.updateFavourite(productId, favourite)
        }
    }
}