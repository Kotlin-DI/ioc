package com.github.kotlin_di.ioc

import com.github.kotlin_di.ioc.scope.IScope
import com.github.kotlin_di.resolve

fun registerSample() {
    resolve(IoC.REGISTER, "dependencyKey", asDependency { 1 })()
}

fun unregisterSample() {
    resolve(IoC.UNREGISTER, "dependencyKey")()
}

fun executeInNewScopeSample() {
    resolve(Scopes.EXECUTE_IN_NEW_SCOPE).use {
        // do stuff

        // if for some reason operation has to terminated
        it.close()
    }
}

fun executeInScopeSample() {
    val scope = resolve(Scopes.NEW)
    resolve(Scopes.EXECUTE_IN_SCOPE, scope).use {
        // do stuff

        // if for some reason operation has to terminated
        it.close()
    }
}

fun newScopeSample() {
    val newScope: IScope = resolve(Scopes.NEW)
    val childScope = resolve(Scopes.NEW, newScope)
}
