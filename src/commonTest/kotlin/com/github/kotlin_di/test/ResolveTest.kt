package com.github.kotlin_di.test

import com.github.kotlin_di.common.command.Command
import com.github.kotlin_di.common.interfaces.Usable
import com.github.kotlin_di.common.types.DRecord
import com.github.kotlin_di.common.types.Key
import com.github.kotlin_di.common.types.ResolveDependencyError
import com.github.kotlin_di.common.types.by
import com.github.kotlin_di.ioc.IoC
import com.github.kotlin_di.ioc.Scopes
import com.github.kotlin_di.resolve
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ResolveTest {

    private val intKey = Key<Unit, Int>("dependencyKey")
    @Test
    fun function_resolve_should_resolve_registered_dependency_by_name() {
        resolve(Scopes.EXECUTE_IN_NEW_SCOPE).use {
            resolve(
                IoC.REGISTER, intKey by { 1 }
            )()
            assertEquals(1, resolve(intKey))
        }
    }

    @Test
    fun unsafe_resolve() {
        resolve<Unit, Usable>("Scopes.executeInNewScope", Unit).use {
            resolve<DRecord<*, *>, Command>(
                "IoC.Register", "Some dependency" by { a: Int -> a + 1 }
            )()
            assertEquals(2, resolve("Some dependency", 1))
        }
    }

    @Test
    fun unregister_should_remove_registered_dependency_from_current_scope() {
        resolve(Scopes.EXECUTE_IN_NEW_SCOPE).use {
            resolve(IoC.REGISTER, intKey by { 1 })()
            assertEquals(1, resolve(intKey))

            resolve(IoC.UNREGISTER, intKey.toString())()

            assertFailsWith<ResolveDependencyError> { resolve(intKey) }
        }
    }

    @Test
    fun function_resolve_should_use_current_dependencies_scope_to_resolve_dependency() {
        resolve(Scopes.EXECUTE_IN_NEW_SCOPE).use {
            resolve(IoC.REGISTER, intKey by { 1 })()
            assertEquals(1, resolve(intKey))

            resolve(Scopes.EXECUTE_IN_NEW_SCOPE).use {
                resolve(IoC.REGISTER, intKey by { 2 })()
                assertEquals(2, resolve(intKey))
            }

            assertEquals(1, resolve(intKey))
        }
    }

    @Test
    fun ioc_dependency_may_be_redefined() {
        resolve(Scopes.EXECUTE_IN_NEW_SCOPE).use {
            resolve(IoC.REGISTER, intKey by { 1 })()
            assertEquals(1, resolve(intKey))

            resolve(IoC.REGISTER, intKey by { 2 })()
            assertEquals(2, resolve(intKey))
        }
    }

    @Test
    fun resolve_should_throw_ResolveDependencyError_if_resolving_dependency_not_registered() {
        resolve(Scopes.EXECUTE_IN_NEW_SCOPE).use {
            assertFailsWith<ResolveDependencyError> { resolve(intKey) }
        }
    }

    @Test
    fun resolve_should_throw_ResolveDependencyError_if_resolve_dependency_strategy_throw_ResolveDependencyError() {
        resolve(Scopes.EXECUTE_IN_NEW_SCOPE).use {
            resolve(IoC.REGISTER, intKey by { throw ResolveDependencyError("Error") })()
            assertFailsWith<ResolveDependencyError> { resolve(intKey) }
        }
    }
}
