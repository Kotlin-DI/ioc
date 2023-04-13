package com.github.kotlinDI.ioc.dependencies

import com.github.kotlinDI.common.interfaces.Dependency
import com.github.kotlinDI.common.types.None
import com.github.kotlinDI.common.types.Option
import com.github.kotlinDI.common.types.Some
import com.github.kotlinDI.ioc.Container
import com.github.kotlinDI.ioc.scope.IScope
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