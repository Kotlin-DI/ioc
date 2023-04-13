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
    fun `New Thread creates its own scope from root`() = runTest {
        val thread = Thread.currentThread()
        val scope = Container.currentScope
        resolve(IoC.REGISTER, intKey by { 1 })()
        assertEquals(1, resolve(intKey))

        val future = async(Dispatchers.Default) {
            assertNotEquals(scope, Container.currentScope)
            assertNotEquals(thread, Thread.currentThread())
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
    fun `coroutines on the same thread use parent scope by default`() = runTest {
        val scope = Container.currentScope
        val thread = Thread.currentThread()
        resolve(IoC.REGISTER, intKey by { 1 })()
        assertEquals(1, resolve(intKey))

        val future = async {
            assertEquals(scope, Container.currentScope)
            assertEquals(thread, Thread.currentThread())
            resolve(IoC.REGISTER, intKey by { 2 })()
            assertEquals(2, resolve(intKey))
        }

        assertEquals(scope, Container.currentScope)
        assertEquals(1, resolve(intKey))
        future.await()
        assertEquals(scope, Container.currentScope)
        assertEquals(2, resolve(intKey))
    }

    @Test
    fun `using WITH_SCOPE can override scope on the same thread`() = runTest {
        val thread = Thread.currentThread()
        val scope = Container.currentScope
        val newScope = resolve(Scopes.NEW)
        resolve(IoC.REGISTER, intKey by { 1 })()
        assertEquals(1, resolve(intKey))

        val future = async(resolve(Scopes.WITH_SCOPE, newScope)) {
            assertEquals(newScope, Container.currentScope)
            assertEquals(thread, Thread.currentThread())
            assertEquals(1, resolve(intKey))
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
    fun `using WITH_SCOPE can pass scope to new thread`() = runTest {
        val thread = Thread.currentThread()
        val scope = Container.currentScope
        resolve(IoC.REGISTER, intKey by { 1 })()
        assertEquals(1, resolve(intKey))

        val future = async(Dispatchers.Default + resolve(Scopes.WITH_SCOPE)) {
            assertEquals(scope, Container.currentScope)
            assertNotEquals(thread, Thread.currentThread())
            assertEquals(1, resolve(intKey))
            resolve(IoC.REGISTER, intKey by { 2 })()
            assertEquals(2, resolve(intKey))
        }

        assertEquals(scope, Container.currentScope)
        assertEquals(1, resolve(intKey))
        future.await()
        assertEquals(scope, Container.currentScope)
        assertEquals(2, resolve(intKey))
    }
}
