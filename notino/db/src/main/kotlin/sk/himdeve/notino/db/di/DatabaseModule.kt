package sk.himdeve.notino.db.di

import android.content.Context
import com.squareup.anvil.annotations.ContributesTo
import com.squareup.moshi.Moshi
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import sk.himdeve.base.di.AppScope
import sk.himdeve.notino.products.AppDatabase
import sk.himdeve.notino.products.ProductId
import sk.himdeve.notino.products.db.Product
import sk.himdeve.notino.products.db.ProductQueries
import sk.himdeve.sqldelight.*

/**
 * Created by Robin Himdeve on 2/23/2022.
 */
@Module
@ContributesTo(AppScope::class)
object DatabaseModule {

    @Provides
    @JvmStatic
    @AppScope
    fun database(driver: SqlDriver, moshi: Moshi): AppDatabase {
        val productIdAdapter = IdColumnAdapter(::ProductId)

        val productAdapter = Product.Adapter(
            idAdapter = productIdAdapter,
            brandAdapter = JsonColumnAdapter(moshi),
            attributesAdapter = JsonColumnAdapter(moshi),
            priceAdapter = JsonColumnAdapter(moshi),
            reviewSummaryAdapter = JsonColumnAdapter(moshi),
            stockAvailabilityAdapter = JsonColumnAdapter(moshi)
        )

        return AppDatabase(
            driver,
            productAdapter
        )
    }

    @Provides
    @JvmStatic
    fun daoTransactor(database: AppDatabase): DaoTransactor {
        return object : DaoTransactor {
            override fun <T> transaction(body: () -> T): T {
                return database.transactionWithResult { body() }
            }
        }
    }

    @Provides
    @JvmStatic
    fun productQueries(database: AppDatabase): ProductQueries = database.productQueries
}

@Module
@ContributesTo(AppScope::class)
object AndroidSqliteDriverModule {

    @Provides
    @JvmStatic
    fun driver(
        context: Context
    ): SqlDriver {
        val schema = AppDatabase.Schema
        return AndroidSqliteDriver(
            schema = schema,
            context = context,
            name = "notino",
            callback = AndroidDriverCallback(
                schema = schema,
                // TODO: This application is made from the scratch, we dont need those processors right now
                createProcessors = emptyList(),
                upgradeProcessors = emptyList())
        )
    }
}