package sk.himdeve.notino.dashboard

import sk.himdeve.notino.products.Product
import sk.himdeve.notino.products.ProductId
import sk.himdeve.notino.products.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Created by Robin Himdeve on 2/27/2022.
 */
class FakeProductRepository : ProductRepository {
    @Volatile
    var productSyncCalled: Boolean = false

    private val _products = MutableStateFlow<List<Product>>(emptyList())

    fun set(products: List<Product>) {
        this._products.value = products
    }

    override val products: Flow<List<Product>> by lazy {
        _products
    }

    override fun init() {}
    override fun clear() {}

    override suspend fun syncProducts() {
        productSyncCalled = true
    }

    override suspend fun updateFavourite(productId: ProductId, favourite: Boolean) {
        _products.value = _products.value.map {
            if (it.id == productId) {
                it.copy(favourite = favourite)
            } else {
                it
            }
        }
    }
}