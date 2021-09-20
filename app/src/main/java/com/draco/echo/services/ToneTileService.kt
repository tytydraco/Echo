package com.draco.echo.services

import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import com.draco.echo.utils.TonePlayerSingleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class ToneTileService : TileService() {
    /**
     * Set the state of the tile quickly
     */
    private fun setTileActive(active: Boolean) {
        with (qsTile) {
            state = if (active) Tile.STATE_ACTIVE else Tile.STATE_INACTIVE
            updateTile()
        }
    }

    override fun onClick() {
        super.onClick()

        /* Prevent running twice */
        if (qsTile.state == Tile.STATE_ACTIVE)
            return

        /* Do this part async */
        GlobalScope.launch(Dispatchers.IO) {
            setTileActive(true)
            TonePlayerSingleton.play(applicationContext)
            setTileActive(false)
        }
    }
}