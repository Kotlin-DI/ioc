package com.github.kotlin_di.ioc.scope

import com.github.kotlin_di.common.types.Dependency
import com.github.kotlin_di.common.types.Store

class Scope(override var parent: IScope) : MutableScope, LinkedScope {

    private val store = Store<String, Dependency<*, *>>()

    override var notFoundStrategy: (String) -> Dependency<*, *> = { key -> parent[key] }
    override fun get(key: String): Dependency<*, *> {
        return store.getOrElse(key) { notFoundStrategy(key) }
    }

    override fun set(key: String, dependency: Dependency<*, *>) {
        store[key] = dependency
    }

    override fun remove(key: String) {
        store.remove(key)
    }
}
