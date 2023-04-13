package com.github.kotlin_di.ioc.scope

import com.github.kotlin_di.common.interfaces.Dependency
import com.github.kotlin_di.common.interfaces.Usable
import com.github.kotlin_di.ioc.IoC
import com.github.kotlin_di.ioc.Scopes
import com.github.kotlin_di.ioc.dependencies.*
import com.github.kotlin_di.resolve

fun RootScope.init() {
    store[IoC.REGISTER.toString()] = Register()
    store[IoC.UNREGISTER.toString()] = Unregister()
    store[Scopes.NEW.toString()] = ScopeNew()
    store[Scopes.EXECUTE_IN_SCOPE.toString()] = ExecuteInScope()
    store[Scopes.EXECUTE_IN_NEW_SCOPE.toString()] = Dependency<Unit, Usable> {
        val scope = resolve(Scopes.NEW)
        resolve(Scopes.EXECUTE_IN_SCOPE, scope)
    }
    store[Scopes.ROOT.toString()] = Dependency<Unit, IScope> { this }
    store[Scopes.WITH_SCOPE.toString()] = WithScope()
}
