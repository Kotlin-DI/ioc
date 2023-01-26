package com.github.kotlin_di.ioc

import com.github.kotlin_di.ioc.scope.IScope
import com.github.kotlin_di.ioc.scope.RootScope
import com.github.kotlin_di.ioc.scope.Scope
import com.github.kotlin_di.ioc.scope.init
import kotlin.concurrent.getOrSet

actual object Container {
    private val scope = ThreadLocal<IScope>()
    private val root = RootScope().apply { init() }

    actual var currentScope: IScope
        get() {
            return scope.getOrSet { Scope(root) }
        }
        set(value) {
            scope.set(value)
        }
}