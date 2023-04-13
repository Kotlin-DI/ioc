package com.github.kotlinDI.ioc.scope

import com.github.kotlinDI.common.interfaces.Dependency
import java.util.concurrent.ConcurrentHashMap

class Scope(override var parent: IScope) : MutableScope, LinkedScope {

    private val store = ConcurrentHashMap<String, Dependency<*, *>>()

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