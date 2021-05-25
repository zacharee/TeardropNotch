package tk.zwander.teardropnotch.service

import android.content.SharedPreferences
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import tk.zwander.teardropnotch.util.PrefManager

/**
 * A QS tile to toggle the enabled state.
 */
class TeardropTile : TileService(), SharedPreferences.OnSharedPreferenceChangeListener {
    /**
     * A reference to the preference store.
     */
    private val prefs by lazy { PrefManager.getInstance(this) }

    /**
     * Perform setup.
     */
    override fun onCreate() {
        prefs.prefs.registerOnSharedPreferenceChangeListener(this)
    }

    /**
     * Perform cleanup.
     */
    override fun onDestroy() {
        super.onDestroy()
        prefs.prefs.unregisterOnSharedPreferenceChangeListener(this)
    }

    /**
     * Respond to changes in the enabled state.
     */
    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key == PrefManager.KEY_ENABLED) {
            updateState()
        }
    }

    /**
     * Toggle the enabled state on click.
     */
    override fun onClick() {
        prefs.isEnabled = !prefs.isEnabled
    }

    /**
     * Update the state when the tile becomes visible.
     */
    override fun onStartListening() {
        updateState()
    }

    /**
     * Update the tile state based on the enabled state.
     */
    private fun updateState() {
        qsTile?.state = if (prefs.isEnabled) Tile.STATE_ACTIVE else Tile.STATE_INACTIVE
        qsTile?.updateTile()
    }
}