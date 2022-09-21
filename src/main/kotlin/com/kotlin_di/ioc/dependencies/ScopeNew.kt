package com.kotlin_di.ioc.dependencies

import com.kotlin_di.ioc.Container
import com.kotlin_di.ioc.Dependency
import com.kotlin_di.ioc.ResolveDependencyError
import com.kotlin_di.ioc.cast
import com.kotlin_di.ioc.scope.IScope
import com.kotlin_di.ioc.scope.Scope
import kotlin.jvm.Throws

class ScopeNew : Dependency {

    @Throws(ResolveDependencyError::class)
    override fun invoke(arguments: Array<out Any>): Any {
        val parent: IScope = if (arguments.isNotEmpty()) {
            cast(arguments[0])
        } else {
            Container.currentScope
        }
        return Scope(parent)
    }
}
