package com.github.kotlin_di.test

import com.github.kotlin_di.common.command.Command
import com.github.kotlin_di.common.interfaces.Usable
import com.github.kotlin_di.ioc.ResolveDependencyError
import com.github.kotlin_di.ioc.asDependency
import com.github.kotlin_di.resolve
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ResolveTest {
    @Test
    fun `function resolve should resolve registered dependency by name`() {
        resolve<Usable>("Scopes.executeInNewScope").use {
            resolve<Command>(
                "IoC.Register",
                "dependencyKey",
                asDependency { 1 }
            )()
            assertEquals(1, resolve("dependencyKey"))
        }
    }

    @Test
    fun `function resolve should use current dependencies scope to resolve dependency`() {
        resolve<Usable>("Scopes.executeInNewScope").use {
            resolve<Command>("IoC.Register", "dependencyKey", asDependency { 1 })()
            assertEquals(1, resolve("dependencyKey"))

            resolve<Usable>("Scopes.executeInNewScope").use {
                resolve<Command>("IoC.Register", "dependencyKey", asDependency { 2 })()
                assertEquals(2, resolve("dependencyKey"))
            }

            assertEquals(1, resolve("dependencyKey"))
        }
    }

    @Test
    fun `IoC dependency may be redefined`() {
        resolve<Usable>("Scopes.executeInNewScope").use {
            resolve<Command>("IoC.Register", "dependencyKey", asDependency { 1 })()
            assertEquals(1, resolve("dependencyKey"))

            resolve<Command>("IoC.Register", "dependencyKey", asDependency { 2 })()
            assertEquals(2, resolve("dependencyKey"))
        }
    }

    @Test
    fun `resolve should throw ResolveDependencyError if resolving dependency didn't registered`() {
        resolve<Usable>("Scopes.executeInNewScope").use {
            assertThrows<ResolveDependencyError> { resolve<Unit>("non-existing dependency") }
        }
    }

    @Test
    fun `resolve should throw ResolveDependencyError if resolve dependency strategy throw ResolveDependencyError`() {
        resolve<Usable>("Scopes.executeInNewScope").use {
            resolve<Command>(
                "IoC.Register",
                "dependencyKey",
                asDependency { throw ResolveDependencyError("Error") }
            )()

            assertThrows<ResolveDependencyError> { resolve<Unit>("dependencyKey") }
        }
    }
}
