package com.github.kotlin_di.ioc

import com.github.kotlin_di.common.command.Command

sealed class IoC<T>(override val _name: String) : Key<T> {
    /** Registers dependency in the current Scope
     *  @param arg0 String : dependency key
     *  @param arg1 Dependency : dependency to register
     *  @sample registerSample
     */
    object REGISTER : IoC<Command>("IoC.Register")

    /** Removes dependency from current Scope
     * @param arg0 String : dependency key
     * @sample unregisterSample
     */
    object UNREGISTER : IoC<Command>("IoC.Unregister")
}
