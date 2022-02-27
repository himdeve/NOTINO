package sk.himdeve.notino.products

import kotlinx.coroutines.flow.Flow

/**
 * Created by Robin Himdeve on 2/23/2022.
 */
interface ProductDao {
    fun products(): Flow<List<Product>>
    fun saveProducts(products: List<Product>)
    // TODO: Just for the demo purposes!
    fun updateFavourite(productId: ProductId, favourite: Boolean)
}