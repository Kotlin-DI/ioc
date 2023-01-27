package com.github.kotlin_di.ioc.dependencies

import com.github.kotlin_di.common.errors.ResolveDependencyError
import com.github.kotlin_di.common.interfaces.Dependency
import com.github.kotlin_di.common.types.None
import com.github.kotlin_di.common.types.Option
import com.github.kotlin_di.common.types.Some
import com.github.kotlin_di.ioc.Container
import com.github.kotlin_di.ioc.scope.IScope
import com.github.kotlin_di.ioc.scope.Scope
import kotlin.jvm.Throws

class ScopeNew : Dependency<Option<IScope>, IScope> {

    @Throws(ResolveDependencyError::class)
    override fun invoke(args: Option<IScope>): IScope {
        val parent: IScope = when (args) {
            is None -> Container.currentScope
            is Some -> args.value
        }
        return Scope(parent)
    }
}
