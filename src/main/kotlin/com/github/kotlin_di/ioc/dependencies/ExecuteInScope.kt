package com.github.kotlin_di.ioc.dependencies

import com.github.kotlin_di.common.interfaces.Usable
import com.github.kotlin_di.ioc.Container
import com.github.kotlin_di.ioc.Dependency
import com.github.kotlin_di.ioc.ResolveDependencyError
import com.github.kotlin_di.ioc.cast
import com.github.kotlin_di.ioc.scope.IScope
import java.io.Closeable
import kotlin.Throws

class ExecuteInScope : Dependency {

    class ScopeGuard(private val scope: IScope) : Usable {

        private val parent = Container.currentScope

        override fun <R> use(block: (Usable) -> R): R {
            return (this as Closeable).use {
                Container.currentScope = scope
                block(this@ScopeGuard)
            }
        }

        override fun close() {
            Container.currentScope = parent
        }
    }

    @Throws(ResolveDependencyError::class)
    override fun invoke(args: Array<out Any>): Usable {
        try {
            val scope: IScope = cast(args[0])
            return ScopeGuard(scope)
        } catch (ex: ResolveDependencyError) {
            throw ex
        } catch (ex: Throwable) {
            throw ResolveDependencyError("Unable to execute in scope", ex)
        }
    }
}
