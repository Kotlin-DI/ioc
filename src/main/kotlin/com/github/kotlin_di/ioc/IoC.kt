package com.github.kotlin_di.ioc

import com.github.kotlin_di.common.command.Command
import com.github.kotlin_di.common.types.DRecord
import com.github.kotlin_di.common.types.Key

object IoC {
    /** Registers dependency in the current Scope
     *  @param arg0 String : dependency key
     *  @param arg1 Dependency : dependency to register
     *  @sample registerSample
     */
    val REGISTER = Key<DRecord<*, *>, Command>("IoC.Register")

    /** Removes dependency from current Scope
     * @param arg0 String : dependency key
     * @sample unregisterSample
     */
    val UNREGISTER = Key<Key<*, *>, Command>("IoC.Unregister")
}
