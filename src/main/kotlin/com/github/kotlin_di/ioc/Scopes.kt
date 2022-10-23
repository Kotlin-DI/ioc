package com.github.kotlin_di.ioc

import com.github.kotlin_di.common.interfaces.Usable
import com.github.kotlin_di.common.types.Key
import com.github.kotlin_di.common.types.Option
import com.github.kotlin_di.ioc.scope.IScope

object Scopes {
    /** executes block of code in a specified scope. after execution set back original scope
     * @param arg execution scope
     *
     * @sample executeInScopeSample
     */
    val EXECUTE_IN_SCOPE = Key<IScope, Usable>("Scopes.executeInScope")

    /** executes block of code in a new scope. after execution set back original scope
     * @sample executeInNewScopeSample
     */
    val EXECUTE_IN_NEW_SCOPE = Key<Unit, Usable>("Scopes.executeInNewScope")

    /** creates new instance of IScope
     * @param arg optional parent scope. if not set will use current scope
     * @sample newScopeSample
     */
    val NEW = Key<Option<IScope>, IScope>("Scopes.New")

    /** returns root scope of the application
     */
    val ROOT = Key<Unit, IScope>("Scopes.Root")
}
