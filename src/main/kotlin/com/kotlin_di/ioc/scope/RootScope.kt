package com.kotlin_di.ioc.scope

import com.kotlin_di.ioc.Dependency
import com.kotlin_di.ioc.ResolveDependencyError
import java.util.concurrent.ConcurrentHashMap

class RootScope : IScope {

    internal val store = ConcurrentHashMap<String, Dependency>()

    override fun get(key: String): Dependency {
        return store.getOrElse(key) { throw ResolveDependencyError("$key Dependency not registered") }
    }
}
