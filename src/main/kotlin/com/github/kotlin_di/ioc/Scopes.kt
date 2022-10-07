package com.github.kotlin_di.ioc

import com.github.kotlin_di.common.interfaces.Usable
import com.github.kotlin_di.ioc.scope.IScope

object Scopes {
    /** executes block of code in a specified scope. after execution set back original scope
     * @param arg0 execution scope
     *
     * @sample executeInScopeSample
     */
    fun EXECUTE_IN_SCOPE(arg0: IScope) = Key<Usable>("Scopes.executeInScope", arrayOf(arg0))

    /** executes block of code in a new scope. after execution set back original scope
     * @sample executeInNewScopeSample
     */
    fun EXECUTE_IN_NEW_SCOPE() = Key<Usable>("Scopes.executeInNewScope")

    /** creates new instance of IScope
     * @param arg0 optional parent scope. if not set will use current scope
     * @sample newScopeSample
     */
    fun NEW(arg0: IScope? = null) = if (arg0 !== null) {
        Key<IScope>("Scopes.New", arrayOf(arg0))
    } else {
        Key("Scopes.New")
    }

    /** returns root scope of the application
     */
    fun ROOT() = Key<IScope>("Scopes.Root")
}
