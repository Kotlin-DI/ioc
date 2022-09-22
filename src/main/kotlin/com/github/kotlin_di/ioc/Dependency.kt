package com.github.kotlin_di.ioc

typealias Dependency = (Array<out Any>) -> Any

fun asDependency(fn: (Array<out Any>) -> Any): Dependency {
    return fn
}
