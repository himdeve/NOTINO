package sk.himdeve.notino.products

import sk.himdeve.notino.products.remote.ApiProduct
import sk.himdeve.notino.products.remote.ProductApiClient

/**
 * Created by Robin Himdeve on 2/25/2022.
 */
class FakeProductApiClient : ProductApiClient {
    lateinit var apiCall: () -> List<ApiProduct>

    fun init(
        apiCall: () -> List<ApiProduct> = { error("Not implemented") }
    ) {
        this.apiCall = apiCall
    }

    override suspend fun products(): List<ApiProduct> {
        return apiCall()
    }
}