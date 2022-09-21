package com.kotlin_di

import com.kotlin_di.ioc.Container
import com.kotlin_di.ioc.ResolveDependencyError
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
