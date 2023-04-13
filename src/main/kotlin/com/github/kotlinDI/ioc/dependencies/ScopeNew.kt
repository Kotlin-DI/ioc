package com.github.kotlinDI.ioc.dependencies

import com.github.kotlinDI.common.errors.ResolveDependencyError
import com.github.kotlinDI.common.interfaces.Dependency
import com.github.kotlinDI.common.types.None
import com.github.kotlinDI.common.types.Option
import com.github.kotlinDI.common.types.Some
import com.github.kotlinDI.ioc.Container
import com.github.kotlinDI.ioc.scope.IScope
import com.github.kotlinDI.ioc.scope.Scope
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