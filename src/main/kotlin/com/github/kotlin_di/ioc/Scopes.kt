package com.github.kotlin_di.ioc

import com.github.kotlin_di.common.interfaces.Usable
import com.github.kotlin_di.ioc.scope.IScope

sealed class Scopes<T>(override val _name: String) : Key<T> {
    /** executes block of code in a specified scope. after execution set back original scope
     * @param arg0 IScope : execution scope
     * @sample executeInScopeSample
     */
    object EXECUTE_IN_SCOPE : Scopes<Usable>("Scopes.executeInScope")

    /** executes block of code in a new scope. after execution set back original scope
     * @sample executeInNewScopeSample
     */
    object EXECUTE_IN_NEW_SCOPE : Scopes<Usable>("Scopes.executeInNewScope")

    /** creates new instance of IScope
     * @param arg0 IScope : optional parent scope. if not set will use current scope
     * @sample newScopeSample
     */
    object NEW : Scopes<IScope>("Scopes.New")
}
