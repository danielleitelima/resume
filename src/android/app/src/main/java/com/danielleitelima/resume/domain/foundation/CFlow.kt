package com.danielleitelima.resume.domain.foundation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

interface Closeable {
    fun close()
}

private fun <T> Flow<T>.internalWatch(block: (T) -> Unit): Closeable {
    val job = Job()

    onEach {
        block(it)
    }.launchIn(CoroutineScope(Dispatchers.Main + job))

    return object : Closeable {
        override fun close() {
            job.cancel()
        }
    }
}

fun <T> Flow<T>.wrap(): CFlow<T> =
    CFlow(this)

class CFlow<T>(private val origin: Flow<T>) : Flow<T> by origin {
    fun watch(block: (T) -> Unit): Closeable {
        return internalWatch(block)
    }
}

open class CStateFlow<T>(private val origin: StateFlow<T>) : StateFlow<T> by origin {
    fun watch(block: (T) -> Unit): Closeable {
        return internalWatch(block)
    }
}

class CMutableStateFlow<T : Any>(private val origin: MutableStateFlow<T>) :
    CStateFlow<T>(origin),
    MutableStateFlow<T> by origin {
    constructor(value: T) : this(MutableStateFlow(value))
}

fun launchInAppScope(call: suspend () -> Unit): Job {
    val job = Job()
    CoroutineScope(Dispatchers.Main + job).launch {
        call()
    }
    return job
}

fun runPeriodically(interval: Long, call: suspend () -> Unit): Job {
    val job = Job()
    CoroutineScope(Dispatchers.Main + job).launch {
        while (isActive) {
            call()
            withContext(Dispatchers.Default) {
                delay(interval)
            }
        }
    }

    return job
}
