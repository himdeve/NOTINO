package sk.himdeve.notino.products.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import retrofit2.Response
import retrofit2.http.GET

/**
 * Created by Robin Himdeve on 2/23/2022.
 */
interface ProductApi {
    // TODO: Consider to use Paging - depends on backend api implementation
    @GET("notino-assignment/db")
    suspend fun products(): Response<ProductsResponse>
}

// TODO: Some attributes might be nullable. Need to be specified in api documentation.
@JsonClass(generateAdapter = true)
data class ProductsResponse(
    @Json(name = "vpProductByIds") val products: List<ApiProduct>?
)

@JsonClass(generateAdapter = true)
data class ApiProduct(
    @Json(name = "productId") val id: Long,
    @Json(name = "brand") val brand: ApiBrand,
    @Json(name = "attributes") val attributes: ApiAttributes,
    @Json(name = "annotation") val annotation: String,
    @Json(name = "masterId") val masterId: Long,
    @Json(name = "url") val url: String,
    @Json(name = "orderUnit") val orderUnit: String,
    @Json(name = "price") val price: ApiPrice,
    @Json(name = "imageUrl") val imageUrl: String,
    @Json(name = "name") val name: String,
    @Json(name = "productCode") val productCode: String,
    @Json(name = "reviewSummary") val reviewSummary: ApiReviewSummary,
    @Json(name = "stockAvailability") val stockAvailability: ApiStockAvailability
)

@JsonClass(generateAdapter = true)
data class ApiAttributes(
    @Json(name = "New") val new: Boolean?,
    @Json(name = "Action") val action: Boolean?,
    @Json(name = "DifferentPackaging") val differentPackaging: Boolean?,
    @Json(name = "Master") val master: Boolean?,
    @Json(name = "AirTransportDisallowed") val airTransportDisallowed: Boolean?,
    @Json(name = "PackageSize") val packageSize: ApiPackageSize?,
    @Json(name = "FreeDelivery") val freeDelivery: Boolean?
)

@JsonClass(generateAdapter = true)
data class ApiPackageSize(
    @Json(name = "height") val height: Int,
    @Json(name = "width") val width: Int,
    @Json(name = "depth") val depth: Int
)

@JsonClass(generateAdapter = true)
data class ApiBrand(
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String
)

@JsonClass(generateAdapter = true)
data class ApiPrice(
    @Json(name = "value") val value: Double,
    @Json(name = "currency") val currency: String
)

@JsonClass(generateAdapter = true)
data class ApiReviewSummary(
    @Json(name = "score") val score: Int,
    @Json(name = "averageRating") val averageRating: Double
)

@JsonClass(generateAdapter = true)
data class ApiStockAvailability(
    @Json(name = "code") val code: String,
    @Json(name = "count") val count: Any?
)