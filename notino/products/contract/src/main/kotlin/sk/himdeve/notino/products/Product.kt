package sk.himdeve.notino.products

import sk.himdeve.base.Id
import com.squareup.moshi.JsonClass

/**
 * Created by Robin Himdeve on 2/23/2022.
 */
data class Product(
    val id: ProductId,
    val brand: Brand,
    val attributes: Attributes,
    val annotation: String,
    val masterId: Long,
    val url: String,
    val orderUnit: String,
    val price: Price,
    val imageUrl: String,
    val name: String,
    val productCode: String,
    val reviewSummary: ReviewSummary,
    val stockAvailability: StockAvailability,
    // TODO: Not ideal! It should come from the API
    val favourite: Boolean = false
) {
    @JsonClass(generateAdapter = true)
    data class Brand(val id: String, val name: String)

    @JsonClass(generateAdapter = true)
    data class Attributes(
        val new: Boolean?,
        val action: Boolean?,
        val differentPackaging: Boolean?,
        val master: Boolean?,
        val airTransportDisallowed: Boolean?,
        val packageSize: PackageSize?,
        val freeDelivery: Boolean?
    )

    @JsonClass(generateAdapter = true)
    data class PackageSize(val height: Int, val width: Int, val depth: Int)

    @JsonClass(generateAdapter = true)
    data class Price(val value: Double, val currency: String)

    @JsonClass(generateAdapter = true)
    data class ReviewSummary(val score: Int, val averageRating: Double)

    @JsonClass(generateAdapter = true)
    data class StockAvailability(val code: String)
}

data class ProductId(override val value: Long) : Id