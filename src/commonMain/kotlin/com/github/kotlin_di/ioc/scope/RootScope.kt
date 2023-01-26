package com.github.kotlin_di.ioc.scope

import com.github.kotlin_di.common.types.Dependency
import com.github.kotlin_di.common.types.ResolveDependencyError
import com.github.kotlin_di.common.types.Store

class RootScope : IScope {

    internal val store = Store<String, Dependency<*, *>>()

    override fun get(key: String): Dependency<*, *> {
        return store.getOrElse(key) { throw ResolveDependencyError("$key Dependency not registered") }
    }
}
