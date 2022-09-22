package com.github.kotlin_di.ioc

import kotlin.jvm.Throws

@Throws(ResolveDependencyError::class)
inline fun <reified T> cast(
    argument: Any,
    or: (Any) -> T = {
        throw ResolveDependencyError("Invalid argument type. Expected ${T::class}; Received ${argument::class}")
    }
): T {
    return if (argument is T) {
        argument
    } else {
        or(argument)
    }
}
