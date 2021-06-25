package com.draco.echo

import android.media.MediaPlayer
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class ToneTileService : TileService() {
    /* Media player containing our special tone */
    private lateinit var mediaPlayer: MediaPlayer

    /**
     * Set the state of the tile quickly
     */
    private fun setTileActive(active: Boolean) {
        with (qsTile) {
            state = if (active) Tile.STATE_ACTIVE else Tile.STATE_INACTIVE
            updateTile()
        }
    }

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer.create(applicationContext, R.raw.tone)
    }

    override fun onClick() {
        super.onClick()

        /* Prevent running twice */
        if (qsTile.state == Tile.STATE_ACTIVE)
            return

        /* Do this part async */
        GlobalScope.launch(Dispatchers.IO) {
            setTileActive(true)

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

            /* Clean up */
            setTileActive(false)
        }
    }
}