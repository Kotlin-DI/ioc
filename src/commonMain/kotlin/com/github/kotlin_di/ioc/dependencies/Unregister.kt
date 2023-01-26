package com.github.kotlin_di.ioc.dependencies

import com.github.kotlin_di.common.command.Command
import com.github.kotlin_di.common.command.CommandExecutionError
import com.github.kotlin_di.common.types.Dependency
import com.github.kotlin_di.common.types.ResolveDependencyError
import com.github.kotlin_di.ioc.Container
import com.github.kotlin_di.ioc.scope.MutableScope
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
