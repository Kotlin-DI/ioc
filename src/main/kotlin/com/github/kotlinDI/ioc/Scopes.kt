package com.github.kotlinDI.ioc

import com.github.kotlinDI.common.interfaces.Usable
import com.github.kotlinDI.common.plugins.KeyDefinition
import com.github.kotlinDI.common.types.Key
import com.github.kotlinDI.common.types.Option
import com.github.kotlinDI.ioc.scope.IScope
import kotlinx.coroutines.ThreadContextElement

object Scopes : KeyDefinition {
    /** executes block of code in a specified scope. after execution set back original scope
     * @param arg execution scope
     *
     * @sample com.github.kotlinDI.ioc.executeInScopeSample
     */
    val EXECUTE_IN_SCOPE = Key<IScope, Usable>("Scopes.executeInScope")

    /** executes block of code in a new scope. after execution set back original scope
     * @sample com.github.kotlinDI.ioc.executeInNewScopeSample
     */
    val EXECUTE_IN_NEW_SCOPE = Key<Unit, Usable>("Scopes.executeInNewScope")

    /** creates new instance of IScope
     * @param arg [IScope] optional parent scope. if not set will use current scope
     * @sample com.github.kotlinDI.ioc.newScopeSample
     */
    val NEW = Key<Option<IScope>, IScope>("Scopes.New")

    /** returns root scope of the application
     */
    val ROOT = Key<Unit, IScope>("Scopes.Root")

    /**
     * Creates coroutine context element for scope
     * @param arg [IScope] optional scope. if not set will use current scope
     * @sample com.github.kotlinDI.ioc.withScopeSample
     */
    val WITH_SCOPE = Key<Option<IScope>, ThreadContextElement<IScope>>("Scope.WithScope")

    override val version = "0.0.6"

    override fun keys() = listOf(EXECUTE_IN_SCOPE, EXECUTE_IN_NEW_SCOPE, NEW, ROOT, WITH_SCOPE)
}