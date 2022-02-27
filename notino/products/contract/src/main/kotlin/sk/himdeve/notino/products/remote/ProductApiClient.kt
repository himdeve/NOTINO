package sk.himdeve.notino.products.remote

/**
 * Created by Robin Himdeve on 2/23/2022.
 */
interface ProductApiClient {
    suspend fun products(): List<ApiProduct>
}