package com.github.kotlin_di.ioc.scope

import com.github.kotlin_di.common.interfaces.Usable
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
        val scope: IScope = resolve("Scopes.New")
        resolve<Usable>("Scopes.executeInScope", scope)
    }
    store["Scopes.Root"] = { this }
}
