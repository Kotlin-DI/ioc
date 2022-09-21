package com.kotlin_di.ioc.scope

import com.kotlin_di.ioc.Dependency

interface MutableScope : IScope {
    operator fun set(key: String, dependency: Dependency)
    fun remove(key: String)
}
