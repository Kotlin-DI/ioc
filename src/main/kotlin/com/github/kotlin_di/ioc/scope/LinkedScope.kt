package com.github.kotlin_di.ioc.scope

import com.github.kotlin_di.common.interfaces.Dependency

interface LinkedScope : IScope {
    val parent: IScope
    var notFoundStrategy: (String) -> Dependency<*, *>
}
