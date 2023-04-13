package com.github.kotlinDI.ioc

import com.github.kotlinDI.common.types.Key
import com.github.kotlinDI.common.types.by
import com.github.kotlinDI.common.types.toOption
import com.github.kotlinDI.ioc.scope.IScope
import com.github.kotlinDI.resolve
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

internal fun registerSample() {
    val key = Key<Unit, Int>("dependencyKey")

    resolve(IoC.REGISTER, key by { 1 })()
}

internal fun unregisterSample() {
    val key = Key<Unit, Int>("dependencyKey")

    resolve(IoC.UNREGISTER, key.toString())()
}

internal fun executeInNewScopeSample() {
    resolve(Scopes.EXECUTE_IN_NEW_SCOPE).use { // it: Closeable
        // do stuff

        // if for some reason operation has to be terminated
        it.close()
    }
}

internal fun executeInScopeSample() {
    val scope = resolve(Scopes.NEW)
    resolve(Scopes.EXECUTE_IN_SCOPE, scope).use {
        // do stuff

        // if for some reason operation has to terminated
        it.close()
    }
}

internal fun newScopeSample() {
    val newScope: IScope = resolve(Scopes.NEW)
    val childScope = resolve(Scopes.NEW, newScope.toOption())
}

internal suspend fun withScopeSample() {
    val scope = resolve(Scopes.NEW)
    runBlocking {
        async(resolve(Scopes.WITH_SCOPE, scope.toOption())) {
            // TODO
        }
    }.await()
}