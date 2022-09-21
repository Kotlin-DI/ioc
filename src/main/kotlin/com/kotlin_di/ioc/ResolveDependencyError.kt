package com.kotlin_di.ioc

class ResolveDependencyError(override val message: String, override val cause: Throwable? = null) : Error(message, cause)
