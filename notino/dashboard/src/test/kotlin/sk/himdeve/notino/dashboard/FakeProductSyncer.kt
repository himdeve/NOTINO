package sk.himdeve.notino.dashboard

import sk.himdeve.notino.products.ProductSyncer
import kotlinx.coroutines.flow.Flow

/**
 * Created by Robin Himdeve on 2/27/2022.
 */
class FakeProductSyncer : ProductSyncer {
    @Volatile
    var productsSynced: Boolean = false
        private set

    private lateinit var _state: () -> Flow<Boolean>

    fun set(
        state: () -> Flow<Boolean> = { error("Not implemented") },
    ) {
        this._state = state
    }

    override val state: Flow<Boolean>
        get() = _state()

    override fun syncProducts() {
        productsSynced = true
    }

    override fun init() {}

    override fun clear() {}
}