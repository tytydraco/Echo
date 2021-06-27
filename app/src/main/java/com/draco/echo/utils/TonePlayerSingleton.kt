package com.draco.echo.utils

import android.content.Context
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Use this singleton to avoid overlapping audio where TonePlayer instance
 * cannot be preserved.
 */
object TonePlayerSingleton {
    private val lock = AtomicBoolean(false)

    suspend fun play(context: Context) {
        if (lock.get())
            return
        lock.set(true)
        TonePlayer(context).play()
        lock.set(false)
    }
}