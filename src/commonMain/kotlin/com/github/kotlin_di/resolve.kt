package com.github.kotlin_di

import com.github.kotlin_di.common.types.*
import com.github.kotlin_di.ioc.Container

@Throws(ResolveDependencyError::class)
inline fun <reified P : Any, reified R : Any> resolve(key: Key<P, R>, args: P): R {
    return resolve(key.toString(), args)
}

@Throws(ResolveDependencyError::class)
inline fun <reified A : Any, reified P : Option<A>, reified R : Any> resolve(key: Key<P, R>, args: P = None as P): R {
    return resolve(key.toString(), args)
}

inline fun <reified R : Any> resolve(key: Key<Unit, R>): R {
    return resolve(key.toString(), Unit)
}

inline fun <reified P : Any, reified R : Any> resolve(key: String, args: P): R {
    try {
        return (Container.currentScope[key] as Dependency<P, R>)(args)
    } catch (ex: ResolveDependencyError) {
        throw ex
    } catch (ex: Throwable) {
        throw ResolveDependencyError("IoC dependency for $key thrown unexpected exception.", ex)
    }
}
