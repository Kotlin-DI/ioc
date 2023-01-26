package com.github.kotlin_di.ioc

import com.github.kotlin_di.common.types.Key
import com.github.kotlin_di.common.types.Some
import com.github.kotlin_di.common.types.by
import com.github.kotlin_di.ioc.scope.IScope
import com.github.kotlin_di.resolve

fun registerSample() {

    val key = Key<Unit, Int>("dependencyKey")

    resolve(IoC.REGISTER, key by { 1 })()
}

fun unregisterSample() {
    val key = Key<Unit, Int>("dependencyKey")

    resolve(IoC.UNREGISTER, key.toString())()
}

fun executeInNewScopeSample() {
    resolve(Scopes.EXECUTE_IN_NEW_SCOPE).use { // it: Closeable
        // do stuff

        // if for some reason operation has to be terminated
        it.close()
    }
}

fun executeInScopeSample() {
    val scope = resolve(Scopes.NEW)
    resolve(Scopes.EXECUTE_IN_SCOPE, scope).use {
        // do stuff

        // if for some reason operation has to terminated
//        it.close()
    }
}

fun newScopeSample() {
    val newScope: IScope = resolve(Scopes.NEW)
    val childScope = resolve(Scopes.NEW, Some(newScope))
}
