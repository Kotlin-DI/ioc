package com.github.kotlin_di

import com.github.kotlin_di.common.errors.ResolveDependencyError
import com.github.kotlin_di.common.interfaces.Dependency
import com.github.kotlin_di.common.types.Key
import com.github.kotlin_di.common.types.None
import com.github.kotlin_di.common.types.Option
import com.github.kotlin_di.common.types.Some
import com.github.kotlin_di.ioc.Container
import kotlin.jvm.Throws

inline fun <reified T : Any> T?.toOption(): Option<T> {
    return when (this) {
        null -> None
        else -> Some(this)
    }
}

@Throws(ResolveDependencyError::class)
inline fun <reified P : Any, reified R : Any> resolve(key: Key<P, R>, args: P): R {
    return resolve(key.toString(), args)
}

@Throws(ResolveDependencyError::class)
inline fun <reified T : Any, reified P : Option<T>, reified R : Any> resolve(key: Key<P, R>, args: T? = null): R {
    return resolve(key.toString(), args.toOption())
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
