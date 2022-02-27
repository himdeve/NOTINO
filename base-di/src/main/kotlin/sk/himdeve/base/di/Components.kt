package sk.himdeve.base.di

import kotlin.reflect.KClass

object Components {

    @Volatile private var store = ComponentsStore()

    fun transaction(body: ComponentsStore.() -> Unit) {
        synchronized(this) {
            val store = store.copy()
            store.body()
            this.store = store
        }
    }

    inline fun <reified T : Any> getOrNull(): T? = getOrNull(T::class)
    inline fun <reified T : Any> get(): T = get(T::class)

    fun <T : Any> getOrNull(klass: KClass<T>): T? = store.getOrNull(klass)
    fun <T : Any> get(klass: KClass<T>): T = store.getOrNull(klass) ?: error("No such component '$klass' found")
}

class ComponentsStore {
    private val list: MutableList<ComponentAndInitializer>

    constructor(store: ComponentsStore) {
        list = store.list.toMutableList()
    }

    constructor() {
        list = mutableListOf()
    }

    fun <T : Any> add(component: T, initializer: ComponentInitializer<T>? = null) {
        @Suppress("UNCHECKED_CAST")
        list += ComponentAndInitializer(component, initializer as? ComponentInitializer<Any>)
        initializer?.init(component)
    }

    fun remove(selector: (Any) -> Boolean) {
        val pair = list.firstOrNull { selector(it.component) }
        if (pair != null) {
            pair.initializer?.clear(pair.component)
            list.remove(pair)
        }
    }

    inline fun <reified T : Any> replace(component: T, initializer: ComponentInitializer<T>? = null) {
        remove { it is T }
        add(component, initializer)
    }

    fun removeAll() {
        list.forEach {
            it.initializer?.clear(it.component)
        }
        list.clear()
    }

    fun <T : Any> getOrNull(klass: KClass<T>): T? {
        @Suppress("UNCHECKED_CAST")
        return (list.firstOrNull { klass.isInstance(it.component) }?.component as? T)
    }

    fun copy(): ComponentsStore {
        return ComponentsStore(this)
    }

    private data class ComponentAndInitializer(val component: Any, val initializer: ComponentInitializer<Any>?)
}


interface ComponentInitializer<T> {
    fun init(component: T)
    fun clear(component: T)
}