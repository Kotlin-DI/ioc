package com.github.kotlin_di.ioc.dependencies

import com.github.kotlin_di.common.interfaces.Usable
import com.github.kotlin_di.common.types.Dependency
import com.github.kotlin_di.common.types.ResolveDependencyError
import com.github.kotlin_di.ioc.Container
import com.github.kotlin_di.ioc.scope.IScope

class ExecuteInScope : Dependency<IScope, Usable> {

    class ScopeGuard(private val scope: IScope) : Usable {

        private val parent = Container.currentScope

        override fun close() {
            Container.currentScope = parent
        }

        override fun <R> use(block: (Usable) -> R): R {
            return try {
                Container.currentScope = scope
                block(this)
            } finally {
                close()
            }
        }
    }

    override fun invoke(args: IScope): Usable {
        try {
            return ScopeGuard(args)
        } catch (ex: ResolveDependencyError) {
            throw ex
        } catch (ex: Throwable) {
            throw ResolveDependencyError("Unable to execute in scope", ex)
        }
    }
}
