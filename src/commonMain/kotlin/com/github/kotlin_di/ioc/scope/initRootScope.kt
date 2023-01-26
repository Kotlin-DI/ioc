package com.github.kotlin_di.ioc.scope

import com.github.kotlin_di.ioc.IoC
import com.github.kotlin_di.ioc.Scopes
import com.github.kotlin_di.ioc.dependencies.ExecuteInScope
import com.github.kotlin_di.ioc.dependencies.Register
import com.github.kotlin_di.ioc.dependencies.ScopeNew
import com.github.kotlin_di.ioc.dependencies.Unregister
import com.github.kotlin_di.resolve

fun RootScope.init() {
    store[IoC.REGISTER.toString()] = Register()
    store[IoC.UNREGISTER.toString()] = Unregister()
    store[Scopes.NEW.toString()] = ScopeNew()
    store[Scopes.EXECUTE_IN_SCOPE.toString()] = ExecuteInScope()
    store[Scopes.EXECUTE_IN_NEW_SCOPE.toString()] = {
        val scope = resolve(Scopes.NEW)
        resolve(Scopes.EXECUTE_IN_SCOPE, scope)
    }
    store[Scopes.ROOT.toString()] = { this }
}
