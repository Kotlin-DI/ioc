package com.github.kotlinDI.ioc

import com.github.kotlinDI.ioc.scope.IScope
import com.github.kotlinDI.ioc.scope.RootScope
import com.github.kotlinDI.ioc.scope.Scope
import com.github.kotlinDI.ioc.scope.init
import kotlin.concurrent.getOrSet

object Container {
    internal val scope = ThreadLocal<IScope>()
    private val root = RootScope().apply { init() }

    var currentScope: IScope
        get() {
            return scope.getOrSet { Scope(root) }
        }
        set(value) {
            scope.set(value)
        }
}