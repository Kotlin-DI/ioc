package com.github.kotlin_di.ioc.scope

import com.github.kotlin_di.common.errors.ResolveDependencyError
import com.github.kotlin_di.common.interfaces.Dependency

interface IScope {
    @Throws(ResolveDependencyError::class)
    operator fun get(key: String): Dependency<*, *>
}
