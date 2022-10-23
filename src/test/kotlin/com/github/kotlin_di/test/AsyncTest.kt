package com.github.kotlin_di.test

import com.github.kotlin_di.common.types.Key
import com.github.kotlin_di.common.types.by
import com.github.kotlin_di.ioc.Container
import com.github.kotlin_di.ioc.IoC
import com.github.kotlin_di.resolve
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
        launch(Dispatchers.Default) {
            val newScope = Container.currentScope
            assertNotEquals(scope, newScope)

            resolve(IoC.REGISTER, intKey by { 2 })()
            assertEquals(2, resolve(intKey))
        }
        assertEquals(scope, Container.currentScope)
        assertEquals(1, resolve(intKey))
    }
}
