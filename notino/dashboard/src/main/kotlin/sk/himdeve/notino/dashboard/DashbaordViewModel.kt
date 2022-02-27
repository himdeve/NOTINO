package sk.himdeve.notino.dashboard

import sk.himdeve.base.DispatcherProvider
import sk.himdeve.base.tupleOf
import sk.himdeve.compose.BaseComposeViewModel
import sk.himdeve.notino.products.Product
import sk.himdeve.notino.products.ProductId
import sk.himdeve.notino.products.ProductRepository
import sk.himdeve.notino.products.ProductSyncer
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

/**
 * Created by Robin Himdeve on 2/23/2022.
 */
class DashboardViewModel(
    initialState: State,
    dispatcherProvider: DispatcherProvider,
    private val productSyncer: ProductSyncer,
    private val productRepository: ProductRepository
) : BaseComposeViewModel<DashboardViewModel.State>(initialState, dispatcherProvider) {

    override fun init() {
        scope.launch {
            combine(
                productSyncer.state,
                productRepository.products,
                ::tupleOf
            )
                .distinctUntilChanged()
                .collect { (syncing, products) ->
                    setState { copy(loading = syncing, items = products) }
                }
        }
    }

    fun heartClick(productId: ProductId, favourite: Boolean) {
        // TODO: Consider locking heart button during execution
        scope.launch {
            productRepository.updateFavourite(productId, favourite)
        }
    }

    data class State(
        val loading: Boolean = true,
        val items: List<Product>? = null
    )
}