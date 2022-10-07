package com.github.kotlin_di.ioc

data class Key<R>(
    val name: String,
    val args: Array<out Any> = arrayOf()
)
