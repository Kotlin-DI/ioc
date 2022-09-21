package com.kotlin_di.ioc

operator fun Dependency.invoke(vararg arguments: Any): Any {
    return this.invoke(arguments)
}
