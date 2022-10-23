package com.github.kotlin_di.ioc.dependencies

import com.github.kotlin_di.common.command.Command
import com.github.kotlin_di.common.command.CommandExecutionError
import com.github.kotlin_di.common.types.Dependency
import com.github.kotlin_di.common.types.Key
import com.github.kotlin_di.ioc.Container
import com.github.kotlin_di.ioc.ResolveDependencyError
import com.github.kotlin_di.ioc.scope.MutableScope
import kotlin.Throws

class Unregister : Dependency<Key<*, *>, Command> {

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
    override fun invoke(args: Key<*, *>): Command {
        try {
            val scope = Container.currentScope as MutableScope
            val key: String = args.name
            return UnregisterCommand(scope, key)
        } catch (ex: ResolveDependencyError) {
            throw ex
        } catch (ex: Throwable) {
            throw ResolveDependencyError("unable to remove dependency", ex)
        }
    }
}
