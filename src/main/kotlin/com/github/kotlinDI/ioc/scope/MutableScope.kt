package com.github.kotlinDI.ioc.scope

import com.github.kotlinDI.common.interfaces.Dependency

interface MutableScope : IScope {
    operator fun set(key: String, dependency: Dependency<*, *>)
    fun remove(key: String)
}