package sk.himdeve.notino.products

import sk.himdeve.notino.products.remote.*

/**
 * Created by Robin Himdeve on 2/23/2022.
 */
@Suppress("FunctionName")
fun ProductMapper(remote: List<ApiProduct>, imageBaseUrl: String): List<Product> {
    return remote.map {
        val brand = mapBrand(it.brand)
        val attributes = mapAttributes(it.attributes)
        val price = mapPrise(it.price)
        val reviewSummary = mapReviewSummary(it.reviewSummary)
        val stockAvailability = mapStockAvailability(it.stockAvailability)

        Product(
            id = ProductId(it.id),
            brand = brand,
            attributes = attributes,
            annotation = it.annotation,
            masterId = it.masterId,
            url = it.url,
            orderUnit = it.orderUnit,
            price = price,
            imageUrl = imageBaseUrl + it.imageUrl,
            name = it.name,
            productCode = it.productCode,
            reviewSummary = reviewSummary,
            stockAvailability = stockAvailability
        )
    }
}

private fun mapBrand(brand: ApiBrand): Product.Brand {
    return Product.Brand(
        id = brand.id,
        name = brand.name
    )
}

private fun mapAttributes(attributes: ApiAttributes): Product.Attributes {
    return Product.Attributes(
        new = attributes.new,
        action = attributes.action,
        differentPackaging = attributes.differentPackaging,
        master = attributes.master,
        airTransportDisallowed = attributes.airTransportDisallowed,
        packageSize = attributes.packageSize?.let(::mapAttributesPackageSize),
        freeDelivery = attributes.freeDelivery
    )
}

private fun mapAttributesPackageSize(packageSize: ApiPackageSize): Product.PackageSize {
    return Product.PackageSize(
        height = packageSize.height,
        width = packageSize.width,
        depth = packageSize.depth
    )
}

private fun mapPrise(price: ApiPrice): Product.Price {
    return Product.Price(
        value = price.value,
        currency = price.currency
    )
}

private fun mapReviewSummary(reviewSummary: ApiReviewSummary): Product.ReviewSummary {
    return Product.ReviewSummary(
        score = reviewSummary.score,
        averageRating = reviewSummary.averageRating
    )
}

private fun mapStockAvailability(stockAvailability: ApiStockAvailability): Product.StockAvailability {
    return Product.StockAvailability(code = stockAvailability.code)
}