package com.github.kotlin_di.test

import com.github.kotlin_di.common.types.Key
import com.github.kotlin_di.common.types.by
import com.github.kotlin_di.ioc.Container
import com.github.kotlin_di.ioc.IoC
import com.github.kotlin_di.ioc.Scopes
import com.github.kotlin_di.resolve
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test

class AsyncTest {

    private val intKey = Key<Unit, Int>("intKey")

    @Test
    fun `New Thread creates its own scope`() = runTest {
        val scope = Container.currentScope
        resolve(IoC.REGISTER, intKey by { 1 })()
        assertEquals(1, resolve(intKey))
        val future = async(Dispatchers.Default) {
            assertNotEquals(scope, Container.currentScope)

            resolve(IoC.REGISTER, intKey by { 2 })()
            assertEquals(2, resolve(intKey))
        }
        assertEquals(scope, Container.currentScope)
        assertEquals(1, resolve(intKey))
        future.await()
        assertEquals(scope, Container.currentScope)
        assertEquals(1, resolve(intKey))
    }

    @Test
    fun `coroutine uses parent scope`() = runTest {
        val scope = Container.currentScope
        resolve(IoC.REGISTER, intKey by { 1 })()
        assertEquals(1, resolve(intKey))
        val future = async {
            assertEquals(scope, Container.currentScope)
            resolve(Scopes.EXECUTE_IN_NEW_SCOPE).use {
                assertNotEquals(scope, Container.currentScope)
                resolve(IoC.REGISTER, intKey by { 2 })()
                assertEquals(2, resolve(intKey))
            }
        }
        assertEquals(scope, Container.currentScope)
        assertEquals(1, resolve(intKey))
        future.await()
        assertEquals(scope, Container.currentScope)
        assertEquals(1, resolve(intKey))
    }
}
