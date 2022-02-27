package sk.himdeve.notino.products

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import sk.himdeve.notino.products.testutils.*
import sk.himdeve.sqldelight.DaoTransactor
import sk.himdeve.testutils.InMemorySqlDriverRule
import sk.himdeve.testutils.TestDispatcherProvider
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test

/**
 * Created by Robin Himdeve on 2/25/2022.
 */
class ProductRepositoryTest {
    @get:Rule
    val inMemorySqliteDriverRule = InMemorySqlDriverRule()

    private val testDispatcherProvider = TestDispatcherProvider()
    private val dao = productDao()
    private val daoTransactor = TestDaoTransactor()
    private val fakeProductApiClient = FakeProductApiClient()
    private val repository = repository(dao)

    @Test
    fun `should return empty list if no products returned from API and DB is empty`() = runBlocking {
        fakeProductApiClient.init(apiCall = { emptyList() })

        repository.syncProducts()

        repository.products.test {
            assertThat(awaitItem()).isEmpty()
        }
    }

    @Test
    fun `should return all products from DB after calling sync product API`() = runBlocking {
        fakeProductApiClient.init(apiCall = {
            listOf(
                dummyApiProduct1,
                dummyApiProduct2
            )
        })

        repository.syncProducts()

        repository.products.test {
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
        fakeProductApiClient.init(apiCall = {
            listOf(dummyApiProduct1)
        })

        repository.syncProducts()
        repository.updateFavourite(dummyProductId1, true)

        repository.products.test {
            assertThat(awaitItem()).isEqualTo(
                listOf(dummyProduct1.copy(favourite = true))
            )
        }
    }

    private fun productDao(): ProductDao {
        val queries = inMemoryDatabase(inMemorySqliteDriverRule.driver).productQueries
        return ProductDaoImpl(queries, testDispatcherProvider)
    }

    private fun repository(productDao: ProductDao): ProductRepository {
        return ProductRepositoryImpl(
            imageBaseUrl = "",
            daoTransactor = daoTransactor,
            productApiClient = fakeProductApiClient,
            productDao = productDao,
            dispatcherProvider = testDispatcherProvider
        )
    }
}

class TestDaoTransactor : DaoTransactor {
    override fun <T> transaction(body: () -> T): T = body.invoke()
}