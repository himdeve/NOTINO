package sk.himdeve.notino.products

import sk.himdeve.base.StatefulScoped

/**
 * Created by Robin Himdeve on 2/24/2022.
 */
interface ProductSyncer : StatefulScoped<Boolean> {
    fun syncProducts()
}