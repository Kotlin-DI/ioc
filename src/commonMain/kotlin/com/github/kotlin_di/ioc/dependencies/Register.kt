package com.github.kotlin_di.ioc.dependencies

import com.github.kotlin_di.common.command.Command
import com.github.kotlin_di.common.command.CommandExecutionError
import com.github.kotlin_di.common.types.DRecord
import com.github.kotlin_di.common.types.Dependency
import com.github.kotlin_di.common.types.ResolveDependencyError
import com.github.kotlin_di.ioc.Container
import com.github.kotlin_di.ioc.scope.MutableScope

class Register : Dependency<DRecord<*, *>, Command> {

    class RegisterCommand(
        private val scope: MutableScope,
        private val key: String,
        private val dependency: Dependency<*, *>
    ) : Command {

        override fun invoke() {
            try {
                scope[key] = dependency
            } catch (ex: Throwable) {
                throw CommandExecutionError("unable to register $key dependency", ex)
            }
        }
    }

    override fun invoke(args: DRecord<*, *>): Command {
        try {
            val scope = Container.currentScope as MutableScope
            val (key, dependency) = args
            return RegisterCommand(scope, key, dependency)
        } catch (ex: ResolveDependencyError) {
            throw ex
        } catch (ex: Throwable) {
            throw ResolveDependencyError("unable to register dependency", ex)
        }
    }
}
