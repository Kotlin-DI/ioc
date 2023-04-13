package com.github.kotlinDI.ioc.scope

import com.github.kotlinDI.common.errors.ResolveDependencyError
import com.github.kotlinDI.common.interfaces.Dependency
import java.util.concurrent.ConcurrentHashMap

class RootScope : IScope {

    internal val store = ConcurrentHashMap<String, Dependency<*, *>>()

    override fun get(key: String): Dependency<*, *> {
        return store.getOrElse(key) { throw ResolveDependencyError("$key Dependency not registered") }
    }
}