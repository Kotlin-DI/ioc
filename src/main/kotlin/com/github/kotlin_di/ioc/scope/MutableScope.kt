package com.github.kotlin_di.ioc.scope

import com.github.kotlin_di.common.interfaces.Dependency

interface MutableScope : IScope {
    operator fun set(key: String, dependency: Dependency<*, *>)
    fun remove(key: String)
}
