package com.github.kotlinDI.ioc.scope

import com.github.kotlinDI.common.interfaces.Dependency

interface LinkedScope : IScope {
    val parent: IScope
    var notFoundStrategy: (String) -> Dependency<*, *>
}