package com.github.kotlin_di.test
import com.github.kotlin_di.common.types.Key
import com.github.kotlin_di.common.types.Some
import com.github.kotlin_di.common.types.by
import com.github.kotlin_di.ioc.Container
import com.github.kotlin_di.ioc.IoC
import com.github.kotlin_di.ioc.Scopes
import com.github.kotlin_di.resolve
import kotlinx.coroutines.*
import kotlinx.coroutines.test.runTest
import kotlin.test.*

class AsyncTest {

    private val intKey = Key<Unit, Int>("intKey")

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun new_Thread_creates_its_own_scope() = runTest {
        val scope = Container.currentScope
        val newScope = resolve(Scopes.NEW, Some(scope))
        resolve(IoC.REGISTER, intKey by { 1 })()
        assertEquals(1, resolve(intKey))
        val future = async(Dispatchers.Default + CoroutineName("test")) {
            resolve(Scopes.EXECUTE_IN_SCOPE, newScope).use {
                resolve(IoC.REGISTER, intKey by { 2 })()
                assertEquals(2, resolve(intKey))
            }
        }
        assertEquals(1, resolve(intKey))
        future.await()
        assertEquals(1, resolve(intKey))
    }
}
