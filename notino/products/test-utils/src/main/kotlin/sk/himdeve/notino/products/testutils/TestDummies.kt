package sk.himdeve.notino.products.testutils

import sk.himdeve.notino.products.Product
import sk.himdeve.notino.products.ProductId
import sk.himdeve.notino.products.remote.*

/**
 * Created by Robin Himdeve on 2/25/2022.
 */
val dummyProductId1 = ProductId(0)
val dummyProductId2 = ProductId(1)

val dummyProduct1 = Product(
    id = dummyProductId1,
    brand = Product.Brand(
        id = "brandId0",
        name = "brandName"
    ),
    attributes = Product.Attributes(
        new = null,
        action = null,
        differentPackaging = null,
        master = null,
        airTransportDisallowed = null,
        packageSize = null,
        freeDelivery = null
    ),
    annotation = "annotation",
    masterId = 0,
    url = "url",
    orderUnit = "orderUnit",
    price = Product.Price(
        value = 100.0,
        currency = "CZK"
    ),
    imageUrl = "imageUrl",
    name = "name",
    productCode = "productCode",
    reviewSummary = Product.ReviewSummary(
        score = 5,
        averageRating = 5.0
    ),
    stockAvailability = Product.StockAvailability(
        code = "code"
    ),
    favourite = false
)

val dummyProduct2 = Product(
    id = dummyProductId2,
    brand = Product.Brand(
        id = "brandId1",
        name = "brandName"
    ),
    attributes = Product.Attributes(
        new = null,
        action = null,
        differentPackaging = null,
        master = null,
        airTransportDisallowed = null,
        packageSize = null,
        freeDelivery = null
    ),
    annotation = "annotation",
    masterId = 1,
    url = "url",
    orderUnit = "orderUnit",
    price = Product.Price(
        value = 100.0,
        currency = "CZK"
    ),
    imageUrl = "imageUrl",
    name = "name",
    productCode = "productCode",
    reviewSummary = Product.ReviewSummary(
        score = 5,
        averageRating = 5.0
    ),
    stockAvailability = Product.StockAvailability(
        code = "code"
    ),
    favourite = false
)

val dummyApiProductId1 = 0L
val dummyApiProductId2 = 1L

val dummyApiProduct1 = ApiProduct(
    id = dummyApiProductId1,
    brand = ApiBrand(
        id = "brandId0",
        name = "brandName"
    ),
    attributes = ApiAttributes(
        new = null,
        action = null,
        differentPackaging = null,
        master = null,
        airTransportDisallowed = null,
        packageSize = null,
        freeDelivery = null
    ),
    annotation = "annotation",
    masterId = 0,
    url = "url",
    orderUnit = "orderUnit",
    price = ApiPrice(
        value = 100.0,
        currency = "CZK"
    ),
    imageUrl = "imageUrl",
    name = "name",
    productCode = "productCode",
    reviewSummary = ApiReviewSummary(
        score = 5,
        averageRating = 5.0
    ),
    stockAvailability = ApiStockAvailability(
        code = "code",
        count = null
    )
)

val dummyApiProduct2 = ApiProduct(
    id = dummyApiProductId2,
    brand = ApiBrand(
        id = "brandId1",
        name = "brandName"
    ),
    attributes = ApiAttributes(
        new = null,
        action = null,
        differentPackaging = null,
        master = null,
        airTransportDisallowed = null,
        packageSize = null,
        freeDelivery = null
    ),
    annotation = "annotation",
    masterId = 1,
    url = "url",
    orderUnit = "orderUnit",
    price = ApiPrice(
        value = 100.0,
        currency = "CZK"
    ),
    imageUrl = "imageUrl",
    name = "name",
    productCode = "productCode",
    reviewSummary = ApiReviewSummary(
        score = 5,
        averageRating = 5.0
    ),
    stockAvailability = ApiStockAvailability(
        code = "code",
        count = null
    )
)