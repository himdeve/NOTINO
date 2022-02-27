package sk.himdeve.notino.products

import sk.himdeve.notino.products.db.Product
import sk.himdeve.sqldelight.IdColumnAdapter
import sk.himdeve.sqldelight.JsonColumnAdapter
import com.squareup.moshi.Moshi
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver

/**
 * Created by Robin Himdeve on 2/25/2022.
 */
fun inMemoryDatabase(jdbcSqliteDriver: JdbcSqliteDriver): AppDatabase {
    val moshi = moshi()
    val productAdapter = Product.Adapter(
        idAdapter = IdColumnAdapter(::ProductId),
        brandAdapter = JsonColumnAdapter(moshi),
        attributesAdapter = JsonColumnAdapter(moshi),
        priceAdapter = JsonColumnAdapter(moshi),
        reviewSummaryAdapter = JsonColumnAdapter(moshi),
        stockAvailabilityAdapter = JsonColumnAdapter(moshi)
    )

    val appDatabase = AppDatabase(
        driver = jdbcSqliteDriver,
        productAdapter = productAdapter
    )
    AppDatabase.Schema.create(jdbcSqliteDriver)
    return appDatabase
}

private fun moshi(): Moshi {
    return Moshi.Builder().build()
}