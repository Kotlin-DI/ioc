package com.github.kotlinDI.ioc.dependencies

import com.github.kotlinDI.common.errors.CommandExecutionError
import com.github.kotlinDI.common.errors.ResolveDependencyError
import com.github.kotlinDI.common.interfaces.Command
import com.github.kotlinDI.common.interfaces.Dependency
import com.github.kotlinDI.ioc.Container
import com.github.kotlinDI.ioc.scope.MutableScope
import kotlin.Throws

class Unregister : Dependency<String, Command> {

    class UnregisterCommand(
        private val scope: MutableScope,
        private val key: String
    ) : Command {
        @Throws(CommandExecutionError::class)
        override fun invoke() {
            try {
                scope.remove(key)
            } catch (ex: Throwable) {
                throw CommandExecutionError("unable to remove $key dependency", ex)
            }
        }
    }

    @Throws(ResolveDependencyError::class)
    override fun invoke(key: String): Command {
        try {
            val scope = Container.currentScope as MutableScope
            return UnregisterCommand(scope, key)
        } catch (ex: ResolveDependencyError) {
            throw ex
        } catch (ex: Throwable) {
            throw ResolveDependencyError("unable to remove dependency", ex)
        }
    }
}