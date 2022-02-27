package sk.himdeve.notino.products

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import sk.himdeve.notino.products.testutils.dummyProduct1
import sk.himdeve.notino.products.testutils.dummyProduct2
import sk.himdeve.notino.products.testutils.dummyProductId1
import sk.himdeve.testutils.InMemorySqlDriverRule
import sk.himdeve.testutils.TestDispatcherProvider
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test

/**
 * Created by Robin Himdeve on 2/25/2022.
 */
class ProductDaoTest {

    @get:Rule
    val inMemorySqliteDriverRule = InMemorySqlDriverRule()

    private val testDispatcherProvider = TestDispatcherProvider()
    private val dao = productDao()

    @Test
    fun `should return empty list if querying products from empty DB`() = runBlocking {
        val products = dao.products()
        products.test {
            assertThat(awaitItem()).isEmpty()
        }
    }

    @Test
    fun `should return all products after saving`() = runBlocking {
        dao.saveProducts(
            products = listOf(
                dummyProduct1,
                dummyProduct2
            )
        )
        val products = dao.products()

        products.test {
            assertThat(awaitItem()).isEqualTo(
                listOf(
                    dummyProduct1,
                    dummyProduct2
                )
            )
        }
    }

    @Test
    fun `should return favourite as true after updating`() = runBlocking {
        dao.saveProducts(products = listOf(dummyProduct1))
        dao.updateFavourite(dummyProductId1, true)
        val products = dao.products()

        products.test {
            assertThat(awaitItem()).isEqualTo(
                listOf(dummyProduct1.copy(favourite = true))
            )
        }
    }

    private fun productDao(): ProductDao {
        val queries = inMemoryDatabase(inMemorySqliteDriverRule.driver).productQueries
        return ProductDaoImpl(queries, testDispatcherProvider)
    }
}