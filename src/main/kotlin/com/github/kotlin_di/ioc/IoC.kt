package com.github.kotlin_di.ioc

import com.github.kotlin_di.common.interfaces.Command
import com.github.kotlin_di.common.plugins.KeyDefinition
import com.github.kotlin_di.common.types.DRecord
import com.github.kotlin_di.common.types.Key

object IoC : KeyDefinition {
    /** Registers dependency in the current Scope
     *  @sample registerSample
     */
    val REGISTER = Key<DRecord<*, *>, Command>("IoC.Register")

    /** Removes dependency from current Scope
     * @param arg0 String : dependency key
     * @sample unregisterSample
     */
    val UNREGISTER = Key<String, Command>("IoC.Unregister")

    override val version = "0.0.6"
    override fun keys() = listOf(REGISTER, UNREGISTER)
}
