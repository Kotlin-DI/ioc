package com.github.kotlin_di.test

import com.github.kotlin_di.common.errors.ResolveDependencyError
import com.github.kotlin_di.common.interfaces.Command
import com.github.kotlin_di.common.interfaces.Usable
import com.github.kotlin_di.common.types.DRecord
import com.github.kotlin_di.common.types.Key
import com.github.kotlin_di.common.types.by
import com.github.kotlin_di.ioc.IoC
import com.github.kotlin_di.ioc.Scopes
import com.github.kotlin_di.resolve
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ResolveTest {

    private val intKey = Key<Unit, Int>("dependencyKey")
    @Test
    fun `function resolve should resolve registered dependency by name`() {
        resolve(Scopes.EXECUTE_IN_NEW_SCOPE).use {
            resolve(
                IoC.REGISTER, intKey by { 1 }
            )()
            assertEquals(1, resolve(intKey))
        }
    }

    @Test
    fun `unsafe resolve`() {
        resolve<Unit, Usable>("Scopes.executeInNewScope", Unit).use {
            resolve<DRecord<*, *>, Command>(
                "IoC.Register", "Some dependency" by { a: Int -> a + 1 }
            )()
            assertEquals(2, resolve("Some dependency", 1))
        }
    }

    @Test
    fun `unregister should remove registered dependency from current scope`() {
        resolve(Scopes.EXECUTE_IN_NEW_SCOPE).use {
            resolve(IoC.REGISTER, intKey by { 1 })()
            assertEquals(1, resolve(intKey))

            resolve(IoC.UNREGISTER, intKey.toString())()

            assertThrows<ResolveDependencyError> { resolve(intKey) }
        }
    }

    @Test
    fun `function resolve should use current dependencies scope to resolve dependency`() {
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
    fun `IoC dependency may be redefined`() {
        resolve(Scopes.EXECUTE_IN_NEW_SCOPE).use {
            resolve(IoC.REGISTER, intKey by { 1 })()
            assertEquals(1, resolve(intKey))

            resolve(IoC.REGISTER, intKey by { 2 })()
            assertEquals(2, resolve(intKey))
        }
    }

    @Test
    fun `resolve should throw ResolveDependencyError if resolving dependency didn't registered`() {
        resolve(Scopes.EXECUTE_IN_NEW_SCOPE).use {
            assertThrows<ResolveDependencyError> { resolve(intKey) }
        }
    }

    @Test
    fun `resolve should throw ResolveDependencyError if resolve dependency strategy throw ResolveDependencyError`() {
        resolve(Scopes.EXECUTE_IN_NEW_SCOPE).use {
            resolve(IoC.REGISTER, intKey by { throw ResolveDependencyError("Error") })()
            assertThrows<ResolveDependencyError> { resolve(intKey) }
        }
    }
}
