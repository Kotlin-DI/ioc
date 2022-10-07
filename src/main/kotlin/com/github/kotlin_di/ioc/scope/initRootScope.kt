package com.github.kotlin_di.ioc.scope

import com.github.kotlin_di.ioc.Scopes
import com.github.kotlin_di.ioc.dependencies.ExecuteInScope
import com.github.kotlin_di.ioc.dependencies.Register
import com.github.kotlin_di.ioc.dependencies.ScopeNew
import com.github.kotlin_di.ioc.dependencies.Unregister
import com.github.kotlin_di.resolve

fun RootScope.init() {
    store["IoC.Register"] = Register()
    store["IoC.Unregister"] = Unregister()
    store["Scopes.New"] = ScopeNew()
    store["Scopes.executeInScope"] = ExecuteInScope()
    store["Scopes.executeInNewScope"] = {
        val scope = resolve(Scopes.NEW())
        resolve(Scopes.EXECUTE_IN_SCOPE(scope))
    }
    store["Scopes.Root"] = { this }
}
