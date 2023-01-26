package com.github.kotlin_di.test

import com.github.kotlin_di.common.types.Key
import com.github.kotlin_di.common.types.ResolveDependencyError
import com.github.kotlin_di.ioc.Container
import com.github.kotlin_di.ioc.Scopes
import com.github.kotlin_di.ioc.scope.MutableScope
import com.github.kotlin_di.resolve
import kotlin.test.*

class ScopesTest {

    private val intKey = Key<Unit, Int>("dependencyKey")

    @Test
    fun root_scope_set_by_default() {
        val root = resolve(Scopes.ROOT)
        val current = Container.currentScope
        assertNotEquals(root, current)
    }

    @Test
    fun scopeGuard_should_redo_previous_scope() {
        val scope = Container.currentScope

        resolve(Scopes.EXECUTE_IN_NEW_SCOPE).use {
            assertNotEquals(scope, Container.currentScope)
        }

        assertEquals(scope, Container.currentScope)
    }

    @Test
    fun method_set_of_Scope_class_should_change_current_scope() {

        resolve(Scopes.EXECUTE_IN_NEW_SCOPE).use {

            assertFailsWith<ResolveDependencyError> {
                resolve(intKey)
            }

            val scope = resolve(Scopes.NEW) as MutableScope

            scope["dependencyKey"] = { 1 }

            Container.currentScope = scope

            assertEquals(1, resolve(intKey))
        }
    }

    @Test
    fun it_is_possible_to_create_new_scope_by_default() {
        assertNotNull(resolve(Scopes.NEW))
    }

    @Test
    fun scope_exception_rollbacks_current_scope() {
        val scope = Container.currentScope
        assertFailsWith<Error> {
            resolve(Scopes.EXECUTE_IN_NEW_SCOPE).use {
                assertNotEquals(scope, Container.currentScope)
                throw Error("com/github/kotlin_di/test")
            }
        }
        assertEquals(scope, Container.currentScope)
    }
}
