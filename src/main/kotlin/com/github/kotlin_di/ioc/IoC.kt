package com.github.kotlin_di.ioc

import com.github.kotlin_di.common.command.Command
object IoC {
    /** Registers dependency in the current Scope
     *  @param arg0 String : dependency key
     *  @param arg1 Dependency : dependency to register
     *  @sample registerSample
     */
    fun REGISTER(
        arg0: String,
        arg1: Dependency
    ) = Key<Command>(
        "IoC.Register",
        arrayOf(arg0, arg1)
    )

    /** Removes dependency from current Scope
     * @param arg0 String : dependency key
     * @sample unregisterSample
     */
    fun UNREGISTER(arg0: String) = Key<Command>("IoC.Unregister", arrayOf(arg0))
}
