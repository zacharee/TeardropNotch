package tk.zwander.teardropnotch.teardrop

import android.content.Context
import android.view.Surface
import android.view.View
import android.view.WindowManager
import tk.zwander.teardropnotch.R
import tk.zwander.teardropnotch.util.PrefManager

/**
 * Manage all four teardrop Views (for all four screen orientations).
 * Retrieve the proper View for the current rotation and make sure it's added.
 * Update all Views when changes are made.
 */
class TeardropHandler(private val context: Context) {
    /**
     * A reference to the preference store.
     */
    private var prefs = PrefManager.getInstance(context)

    /**
     * A map of the different teardrops.
     */
    private val teardrops = HashMap<Int, Teardrop>()

    init {
        //Initialize the teardrops.
        teardrops[Surface.ROTATION_0] = View.inflate(context, R.layout.notch, null) as Teardrop
        teardrops[Surface.ROTATION_90] = View.inflate(context, R.layout.notch_90, null) as Teardrop
        teardrops[Surface.ROTATION_180] = View.inflate(context, R.layout.notch_180, null) as Teardrop
        teardrops[Surface.ROTATION_270] = View.inflate(context, R.layout.notch_270, null) as Teardrop
    }

    /**
     * Called when the user updates the center ratio.
     * Updates all teardrops.
     */
    fun onNewCenterRatio(ratio: Float) {
        teardrops.values.forEach {
            it.onNewCenterRatio(ratio)
        }
    }

    /**
     * Called when the user updates the dimensions of
     * the teardrops. Updates all teardrops and notifies
     * the WindowManager of updates for the proper View.
     */
    fun onNewDimensions(wm: WindowManager, rotation: Int) {
        teardrops.values.forEach {
            it.onNewDimensions()
        }

        updateWindow(wm, rotation)
    }

    /**
     * Called when the display rotates. Updates all teardrops
     * and notifies the WindowManager of updates for the proper
     * View.
     */
    fun onRotation(wm: WindowManager, rotation: Int) {
        teardrops.values.forEach {
            removeWindow(wm, it)
        }

        addWindow(wm, rotation)
    }

    /**
     * Add the proper teardrop for the given rotation.
     */
    fun addWindow(wm: WindowManager, rotation: Int) {
        val drop = teardrops[rotation]!!
        addWindow(wm, drop)
    }

    /**
     * Add the given teardrop. Will do nothing if the
     * teardrop isn't enabled.
     */
    fun addWindow(wm: WindowManager, drop: Teardrop) {
        if (prefs.isEnabled) {
            try {
                wm.addView(drop, drop.params)
            } catch (e: Exception) {}
        }
    }

    /**
     * Update the proper teardrop for the given rotation.
     */
    fun updateWindow(wm: WindowManager, rotation: Int) {
        val drop = teardrops[rotation]!!
        updateWindow(wm, drop)
    }

    /**
     * Update the given teardrop.
     */
    fun updateWindow(wm: WindowManager, drop: Teardrop) {
        try {
            wm.updateViewLayout(drop, drop.params)
        } catch (e: Exception) {}
    }

    /**
     * Remove the proper teardrop for the given rotation.
     */
    fun removeWindow(wm: WindowManager, rotation: Int) {
        val drop = teardrops[rotation]!!
        removeWindow(wm, drop)
    }

    /**
     * Remove the given teardrop.
     */
    fun removeWindow(wm: WindowManager, drop: Teardrop) {
        try {
            wm.removeView(drop)
        } catch (e: Exception) {}
    }
}