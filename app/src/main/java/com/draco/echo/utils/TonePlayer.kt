package com.draco.echo.utils

import android.content.Context
import android.media.MediaPlayer
import com.draco.echo.R
import kotlinx.coroutines.delay

class TonePlayer(context: Context) {
    /* Media player containing our special tone */
    private val mediaPlayer = MediaPlayer.create(context, R.raw.tone)

    suspend fun play() {
        /* Play tone in left ear */
        mediaPlayer.apply {
            seekTo(0)
            setVolume(1f, 0f)
            start()
        }

        /* Wait for tone to finish + 100ms */
        delay(mediaPlayer.duration.toLong())
        delay(100)

        /* Play tone in right ear */
        mediaPlayer.apply {
            seekTo(0)
            setVolume(0f, 1f)
            start()
        }

        /* Wait for tone to finish */
        delay(mediaPlayer.duration.toLong())
    }
}