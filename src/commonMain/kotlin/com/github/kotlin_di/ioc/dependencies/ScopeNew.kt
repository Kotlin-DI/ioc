package com.github.kotlin_di.ioc.dependencies

import com.github.kotlin_di.common.types.Dependency
import com.github.kotlin_di.common.types.None
import com.github.kotlin_di.common.types.Option
import com.github.kotlin_di.common.types.Some
import com.github.kotlin_di.ioc.Container
import com.github.kotlin_di.ioc.scope.IScope
import com.github.kotlin_di.ioc.scope.Scope

class ScopeNew : Dependency<Option<IScope>, IScope> {

    override fun invoke(args: Option<IScope>): IScope {
        val parent: IScope = when (args) {
            is None -> Container.currentScope
            is Some -> args.value
        }
        return Scope(parent)
    }
}
