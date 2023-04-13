package com.github.kotlin_di.ioc.dependencies

import com.github.kotlin_di.common.interfaces.Dependency
import com.github.kotlin_di.common.types.None
import com.github.kotlin_di.common.types.Option
import com.github.kotlin_di.common.types.Some
import com.github.kotlin_di.ioc.Container
import com.github.kotlin_di.ioc.scope.IScope
import kotlinx.coroutines.ThreadContextElement
import kotlinx.coroutines.asContextElement

class WithScope : Dependency<Option<IScope>, ThreadContextElement<IScope>> {
    override fun invoke(args: Option<IScope>): ThreadContextElement<IScope> {
        val scope = when (args) {
            is None -> Container.currentScope
            is Some -> args.value
        }
        return Container.scope.asContextElement(scope)
    }
}
