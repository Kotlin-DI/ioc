package com.kotlin_di.ioc.dependencies

import com.kotlin_di.common.command.Command
import com.kotlin_di.common.command.CommandExecutionError
import com.kotlin_di.ioc.Container
import com.kotlin_di.ioc.Dependency
import com.kotlin_di.ioc.ResolveDependencyError
import com.kotlin_di.ioc.cast
import com.kotlin_di.ioc.scope.MutableScope
import kotlin.Throws

class Unregister : Dependency {

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
    override fun invoke(args: Array<out Any>): Any {
        try {
            val scope: MutableScope = cast(Container.currentScope)
            val key: String = cast(args[0])
            return UnregisterCommand(scope, key)
        } catch (ex: ResolveDependencyError) {
            throw ex
        } catch (ex: Throwable) {
            throw ResolveDependencyError("unable to remove dependency", ex)
        }
    }
}
