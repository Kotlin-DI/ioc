package com.github.kotlin_di.ioc.dependencies

import com.github.kotlin_di.common.command.Command
import com.github.kotlin_di.common.command.CommandExecutionError
import com.github.kotlin_di.ioc.Container
import com.github.kotlin_di.ioc.Dependency
import com.github.kotlin_di.ioc.ResolveDependencyError
import com.github.kotlin_di.ioc.cast
import com.github.kotlin_di.ioc.scope.MutableScope
import kotlin.Throws

class Register : Dependency {

    class RegisterCommand(
        private val scope: MutableScope,
        private val key: String,
        private val dependency: Dependency
    ) : Command {

        @Throws(CommandExecutionError::class)
        override fun invoke() {
            try {
                scope[key] = dependency
            } catch (ex: Throwable) {
                throw CommandExecutionError("unable to register $key dependency", ex)
            }
        }
    }

    @Throws(ResolveDependencyError::class)
    override fun invoke(arguments: Array<out Any>): Any {
        try {
            val scope: MutableScope = cast(Container.currentScope)
            val key: String = cast(arguments[0])
            val dependency: Dependency = cast(arguments[1])
            return RegisterCommand(scope, key, dependency)
        } catch (ex: ResolveDependencyError) {
            throw ex
        } catch (ex: Throwable) {
            throw ResolveDependencyError("unable to register dependency", ex)
        }
    }
}
