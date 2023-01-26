package com.github.kotlin_di.ioc

import com.github.kotlin_di.ioc.scope.IScope
import com.github.kotlin_di.ioc.scope.RootScope
import com.github.kotlin_di.ioc.scope.Scope
import com.github.kotlin_di.ioc.scope.init

actual object Container {
    private val root = RootScope().apply { init() }
    private var scope: IScope = Scope(root)

    actual var currentScope: IScope
        get() = scope
        set(value) {
            scope = value
        }
}