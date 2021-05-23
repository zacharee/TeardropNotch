package tk.zwander.teardropnotch.service

import android.content.SharedPreferences
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import tk.zwander.teardropnotch.util.PrefManager

class TeardropTile : TileService(), SharedPreferences.OnSharedPreferenceChangeListener {
    private val prefs by lazy { PrefManager.getInstance(this) }

    override fun onCreate() {
        prefs.prefs.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        prefs.prefs.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key == PrefManager.KEY_ENABLED) {
            updateState()
        }
    }

    override fun onClick() {
        prefs.isEnabled = !prefs.isEnabled
    }

    override fun onStartListening() {
        updateState()
    }

    private fun updateState() {
        qsTile?.state = if (prefs.isEnabled) Tile.STATE_ACTIVE else Tile.STATE_INACTIVE
        qsTile?.updateTile()
    }
}