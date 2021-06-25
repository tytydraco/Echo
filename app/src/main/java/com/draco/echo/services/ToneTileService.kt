package com.draco.echo.services

import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import com.draco.echo.utils.TonePlayer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class ToneTileService : TileService() {
    /* Media player containing our special tone */
    private lateinit var tonePlayer: TonePlayer

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
        tonePlayer = TonePlayer(applicationContext)
    }

    override fun onClick() {
        super.onClick()

        /* Prevent running twice */
        if (qsTile.state == Tile.STATE_ACTIVE)
            return

        /* Do this part async */
        GlobalScope.launch(Dispatchers.IO) {
            setTileActive(true)
            tonePlayer.play()
            setTileActive(false)
        }
    }
}