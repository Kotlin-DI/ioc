package com.github.kotlinDI.ioc

import com.github.kotlinDI.common.interfaces.Command
import com.github.kotlinDI.common.plugins.KeyDefinition
import com.github.kotlinDI.common.types.DRecord
import com.github.kotlinDI.common.types.Key

object IoC : KeyDefinition {
    /** Registers dependency in the current Scope
     *  @sample com.github.kotlinDI.ioc.registerSample
     */
    val REGISTER = Key<DRecord<*, *>, Command>("IoC.Register")

    /** Removes dependency from current Scope
     * @param args String : dependency key
     * @sample com.github.kotlinDI.ioc.unregisterSample
     */
    val UNREGISTER = Key<String, Command>("IoC.Unregister")

    override val version = "0.0.6"
    override fun keys() = listOf(REGISTER, UNREGISTER)
}