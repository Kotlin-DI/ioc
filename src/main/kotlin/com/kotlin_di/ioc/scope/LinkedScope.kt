package com.kotlin_di.ioc.scope

import com.kotlin_di.ioc.Dependency

interface LinkedScope : IScope {
    val parent: IScope
    var notFoundStrategy: (String) -> Dependency
}
