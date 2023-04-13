package com.github.kotlinDI.ioc.dependencies

import com.github.kotlinDI.common.errors.CommandExecutionError
import com.github.kotlinDI.common.errors.ResolveDependencyError
import com.github.kotlinDI.common.interfaces.Command
import com.github.kotlinDI.common.interfaces.Dependency
import com.github.kotlinDI.common.types.DRecord
import com.github.kotlinDI.ioc.Container
import com.github.kotlinDI.ioc.scope.MutableScope
import kotlin.Throws

class Register : Dependency<DRecord<*, *>, Command> {

    class RegisterCommand(
        private val scope: MutableScope,
        private val key: String,
        private val dependency: Dependency<*, *>
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