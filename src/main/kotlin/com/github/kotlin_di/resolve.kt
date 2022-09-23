package com.github.kotlin_di

import com.github.kotlin_di.ioc.Container
import com.github.kotlin_di.ioc.Key
import com.github.kotlin_di.ioc.ResolveDependencyError
import kotlin.jvm.Throws

@Throws(ResolveDependencyError::class)
inline fun <reified T> resolve(key: String, vararg arguments: Any): T {
    try {
        return Container.currentScope[key](arguments) as T
    } catch (ex: ResolveDependencyError) {
        throw ex
    } catch (ex: Throwable) {
        throw ResolveDependencyError("IoC dependency for $key thrown unexpected exception.", ex)
    }
}

@Throws(ResolveDependencyError::class)
inline fun <reified T> resolve(key: Key<T>, vararg arguments: Any): T {
    try {
        return Container.currentScope[key._name](arguments) as T
    } catch (ex: ResolveDependencyError) {
        throw ex
    } catch (ex: Throwable) {
        throw ResolveDependencyError("IoC dependency for $key thrown unexpected exception.", ex)
    }
}
