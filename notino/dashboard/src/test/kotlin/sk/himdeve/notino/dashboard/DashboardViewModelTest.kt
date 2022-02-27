package sk.himdeve.notino.dashboard

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import sk.himdeve.notino.products.testutils.dummyProduct1
import sk.himdeve.notino.products.testutils.dummyProduct2
import sk.himdeve.notino.products.testutils.dummyProductId1
import sk.himdeve.testutils.TestDispatcherProvider
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test

/**
 * Created by Robin Himdeve on 2/27/2022.
 */
class DashboardViewModelTest {
    private val dispatcherProvider = TestDispatcherProvider()
    private val productRepository = FakeProductRepository()
    private val productSyncer = FakeProductSyncer()

    @Test
    fun `should show dashboard list of products`() = runBlocking {
        productRepository.set(products = listOf(dummyProduct1, dummyProduct2))
        productSyncer.set(state = { flowOf(false) })
        val viewModel = initViewModel()

        viewModel.state.test {
            assertThat(awaitItem())
                .isEqualTo(
                    DashboardViewModel.State(
                        loading = false,
                        items = listOf(dummyProduct1, dummyProduct2)
                    )
                )
        }
    }

    @Test
    fun `should return favourite as true after heart button click`() = runBlocking {
        productRepository.set(products = listOf(dummyProduct1, dummyProduct2))
        productSyncer.set(state = { flowOf(false) })
        val viewModel = initViewModel()

        viewModel.heartClick(dummyProductId1, true)

        viewModel.state.test {
            assertThat(awaitItem())
                .isEqualTo(
                    DashboardViewModel.State(
                        loading = false,
                        items = listOf(dummyProduct1.copy(favourite = true), dummyProduct2)
                    )
                )
        }
    }

    private fun initViewModel(): DashboardViewModel {
        return DashboardViewModel(
            DashboardViewModel.State(),
            dispatcherProvider,
            productSyncer,
            productRepository
        ).apply { init() }
    }
}