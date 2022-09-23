package com.github.kotlin_di.ioc.scope

import com.github.kotlin_di.ioc.IoC
import com.github.kotlin_di.ioc.Scopes
import com.github.kotlin_di.ioc.dependencies.ExecuteInScope
import com.github.kotlin_di.ioc.dependencies.Register
import com.github.kotlin_di.ioc.dependencies.ScopeNew
import com.github.kotlin_di.ioc.dependencies.Unregister
import com.github.kotlin_di.resolve

fun RootScope.init() {
    store[IoC.REGISTER._name] = Register()
    store[IoC.UNREGISTER._name] = Unregister()
    store[Scopes.NEW._name] = ScopeNew()
    store[Scopes.EXECUTE_IN_SCOPE._name] = ExecuteInScope()
    store[Scopes.EXECUTE_IN_NEW_SCOPE._name] = {
        val scope = resolve(Scopes.NEW)
        resolve(Scopes.EXECUTE_IN_SCOPE, scope)
    }
    store["Scopes.Root"] = { this }
}
