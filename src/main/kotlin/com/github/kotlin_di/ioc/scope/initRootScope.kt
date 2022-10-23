package com.github.kotlin_di.ioc.scope

import com.github.kotlin_di.ioc.IoC
import com.github.kotlin_di.ioc.Scopes
import com.github.kotlin_di.ioc.dependencies.ExecuteInScope
import com.github.kotlin_di.ioc.dependencies.Register
import com.github.kotlin_di.ioc.dependencies.ScopeNew
import com.github.kotlin_di.ioc.dependencies.Unregister
import com.github.kotlin_di.resolve

fun RootScope.init() {
    store[IoC.REGISTER.name] = Register()
    store[IoC.UNREGISTER.name] = Unregister()
    store[Scopes.NEW.name] = ScopeNew()
    store[Scopes.EXECUTE_IN_SCOPE.name] = ExecuteInScope()
    store[Scopes.EXECUTE_IN_NEW_SCOPE.name] = {
        val scope = resolve(Scopes.NEW)
        resolve(Scopes.EXECUTE_IN_SCOPE, scope)
    }
    store[Scopes.ROOT.name] = { this }
}
