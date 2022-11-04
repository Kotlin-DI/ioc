package com.github.kotlin_di.test

import com.github.kotlin_di.common.types.Key
import com.github.kotlin_di.ioc.Container
import com.github.kotlin_di.ioc.ResolveDependencyError
import com.github.kotlin_di.ioc.Scopes
import com.github.kotlin_di.ioc.scope.MutableScope
import com.github.kotlin_di.resolve
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ScopesTest {

    private val intKey = Key<Unit, Int>("dependencyKey")

    @Test
    fun `Root scope set by default`() {
        val root = resolve(Scopes.ROOT)
        val current = Container.currentScope
        assertNotEquals(root, current)
    }

    @Test
    fun `ScopeGuard should redo previous scope`() {
        val scope = Container.currentScope

        resolve(Scopes.EXECUTE_IN_NEW_SCOPE).use {
            assertNotEquals(scope, Container.currentScope)
        }

        assertEquals(scope, Container.currentScope)
    }

    @Test
    fun `Method set of Scope class should change current scope`() {

        resolve(Scopes.EXECUTE_IN_NEW_SCOPE).use {

            assertThrows<ResolveDependencyError> {
                resolve(intKey)
            }

            val scope = resolve(Scopes.NEW) as MutableScope

            scope["dependencyKey"] = { 1 }

            Container.currentScope = scope

            assertEquals(1, resolve(intKey))
        }
    }

    @Test
    fun `It is possible to create new scope by default`() {
        assertNotNull(resolve(Scopes.NEW))
    }

    @Test
    fun `Scope exception rollbacks current scope`() {
        val scope = Container.currentScope
        assertThrows<Error> {
            resolve(Scopes.EXECUTE_IN_NEW_SCOPE).use {
                assertNotEquals(scope, Container.currentScope)
                throw Error("test")
            }
        }
        assertEquals(scope, Container.currentScope)
    }
}
