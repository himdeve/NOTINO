package sk.himdeve.notino.products

import sk.himdeve.base.DispatcherProvider
import sk.himdeve.notino.products.db.ProductQueries
import sk.himdeve.sqldelight.diff
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.Flow

/**
 * Created by Robin Himdeve on 2/23/2022.
 */
class ProductDaoImpl(
    private val productQueries: ProductQueries,
    private val dispatcherProvider: DispatcherProvider
) : ProductDao {
    override fun products(): Flow<List<Product>> {
        return productQueries.products(MAPPER)
            .asFlow()
            .mapToList(dispatcherProvider.io)
    }

    override fun saveProducts(products: List<Product>) {
        diff(
            db = productQueries,
            localsList = { productQueries.products(MAPPER).executeAsList() },
            localIdSelector = { it.id },
            remotesList = products,
            remoteIdSelector = { it.id },
            applyResult = { result ->
                result.inserts.forEach { insertProduct(it) }
                result.updates.forEach { (local, remote) ->
                    {
                        // TODO: This is just for a demo purposes. Otherwise it is wrong API architecture!
                        val adjustedProduct = remote.copy(favourite = local.favourite)

                        updateProduct(adjustedProduct)
                    }
                }
                result.deletes.forEach { productQueries.deleteProduct(it.id) }
            }
        )
    }

    override fun updateFavourite(productId: ProductId, favourite: Boolean) {
        productQueries.transaction {
            val localList = productQueries.products(MAPPER).executeAsList()
            val localProduct = localList.firstOrNull { it.id == productId }
            if (localProduct != null) {
                updateProduct(localProduct.copy(favourite = favourite))
            }
        }
    }

    private fun insertProduct(remote: Product) {
        remote.apply {
            productQueries.insertProduct(
                id,
                brand,
                attributes,
                annotation,
                masterId,
                url,
                orderUnit,
                price,
                imageUrl,
                name,
                productCode,
                reviewSummary,
                stockAvailability,
                favourite
            )
        }
    }

    private fun updateProduct(remote: Product) {
        remote.apply {
            productQueries.updateProduct(
                brand,
                attributes,
                annotation,
                masterId,
                url,
                orderUnit,
                price,
                imageUrl,
                name,
                productCode,
                reviewSummary,
                stockAvailability,
                favourite,
                id
            )
        }
    }
}

private val MAPPER: (
    id: ProductId, brand: Product.Brand, attributes: Product.Attributes, annotation: String, masterId: Long,
    url: String, orderUnit: String, price: Product.Price, imageUrl: String, name: String, productCode: String,
    reviewSummary: Product.ReviewSummary, stockAvailability: Product.StockAvailability, favourite: Boolean
) -> Product =
    { id, brand, attributes, annotation, masterId, url, orderUnit, price, imageUrl, name, productCode, reviewSummary,
      stockAvailability, favourite ->
        Product(
            id, brand, attributes, annotation, masterId, url, orderUnit, price, imageUrl, name, productCode,
            reviewSummary, stockAvailability, favourite
        )
    }