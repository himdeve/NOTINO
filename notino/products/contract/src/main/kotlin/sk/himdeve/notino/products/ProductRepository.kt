package sk.himdeve.notino.products

import sk.himdeve.base.Scoped
import kotlinx.coroutines.flow.Flow

/**
 * Created by Robin Himdeve on 2/23/2022.
 */
interface ProductRepository : Scoped {
    val products: Flow<List<Product>>
    suspend fun syncProducts()
    suspend fun updateFavourite(productId: ProductId, favourite: Boolean)
}