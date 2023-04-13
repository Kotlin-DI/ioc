package com.github.kotlinDI.ioc.scope

import com.github.kotlinDI.common.errors.ResolveDependencyError
import com.github.kotlinDI.common.interfaces.Dependency

interface IScope {
    @Throws(ResolveDependencyError::class)
    operator fun get(key: String): Dependency<*, *>
}