package com.github.kotlinDI

import com.github.kotlinDI.common.errors.ResolveDependencyError
import com.github.kotlinDI.common.interfaces.Dependency
import com.github.kotlinDI.common.types.Key
import com.github.kotlinDI.common.types.None
import com.github.kotlinDI.common.types.Option
import com.github.kotlinDI.ioc.Container
import kotlin.jvm.Throws

@Throws(ResolveDependencyError::class)
inline fun <reified P : Any, reified R : Any> resolve(key: Key<P, R>, args: P): R {
    return resolve(key.toString(), args)
}

@Throws(ResolveDependencyError::class)
inline fun <reified T : Any, reified R : Any> resolve(key: Key<Option<T>, R>, args: Option<T> = None): R {
    return resolve(key.toString(), args)
}

@Throws(ResolveDependencyError::class)
inline fun <reified R : Any> resolve(key: Key<Unit, R>): R {
    return resolve(key.toString(), Unit)
}

@Throws(ResolveDependencyError::class)
inline fun <reified P : Any, reified R : Any> resolve(key: String, args: P): R {
    try {
        return (Container.currentScope[key] as Dependency<P, R>)(args)
    } catch (ex: ResolveDependencyError) {
        throw ex
    } catch (ex: Throwable) {
        throw ResolveDependencyError("IoC dependency for $key thrown unexpected exception.", ex)
    }
}