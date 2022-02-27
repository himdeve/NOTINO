package sk.himdeve.notino.products.remote

import sk.himdeve.net.ApiHelper
import sk.himdeve.net.mapBody

/**
 * Created by Robin Himdeve on 2/23/2022.
 */
class ProductApiClientImpl(private val productApi: ProductApi, private val apiHelper: ApiHelper) : ProductApiClient {
    override suspend fun products(): List<ApiProduct> {
        return productApi.products().mapBody(apiHelper).products ?: emptyList()
    }
}