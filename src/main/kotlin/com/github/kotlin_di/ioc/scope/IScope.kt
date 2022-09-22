package com.github.kotlin_di.ioc.scope

import com.github.kotlin_di.ioc.Dependency

interface IScope {
    operator fun get(key: String): Dependency
}
