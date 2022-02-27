package sk.himdeve.notino.products

import sk.himdeve.base.*
import sk.himdeve.base.ext.signalFlow
import kotlinx.coroutines.launch

/**
 * Created by Robin Himdeve on 2/24/2022.
 */
class ProductSyncerImpl(
    syncing: Boolean,
    dispatcherProvider: DispatcherProvider,
    private val productRepository: ProductRepository
) : ProductSyncer, AbstractStatefulScoped<Boolean>(syncing, dispatcherProvider.io) {
    override fun init() {
        // initial sync
        syncProducts()
    }

    override fun syncProducts() {
        scope.launch {
            signalFlow { productRepository.syncProducts() }
                .collect {
                    setState { it == Loading }
                }
        }
    }
}