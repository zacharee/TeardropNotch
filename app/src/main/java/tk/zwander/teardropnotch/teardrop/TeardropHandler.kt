package tk.zwander.teardropnotch.teardrop

import android.content.Context
import android.view.Surface
import android.view.View
import android.view.WindowManager
import tk.zwander.teardropnotch.R

class TeardropHandler(private val context: Context) {
    private val teardrops = HashMap<Int, Teardrop>()

    init {
        teardrops[Surface.ROTATION_0] = View.inflate(context, R.layout.notch, null) as Teardrop
        teardrops[Surface.ROTATION_90] = View.inflate(context, R.layout.notch_90, null) as Teardrop
        teardrops[Surface.ROTATION_180] = View.inflate(context, R.layout.notch_180, null) as Teardrop
        teardrops[Surface.ROTATION_270] = View.inflate(context, R.layout.notch_270, null) as Teardrop
    }

    fun onNewCenterRatio(ratio: Float) {
        teardrops.values.forEach {
            it.onNewCenterRatio(ratio)
        }
    }

    fun onNewDimensions(wm: WindowManager, rotation: Int) {
        teardrops.values.forEach {
            it.onNewDimensions()
        }

        updateWindow(wm, rotation)
    }

    fun onRotation(wm: WindowManager, rotation: Int) {
        teardrops.values.forEach {
            removeWindow(wm, it)
        }

        addWindow(wm, rotation)
    }

    fun addWindow(wm: WindowManager, rotation: Int) {
        val drop = teardrops[rotation]!!
        addWindow(wm, drop)
    }

    fun addWindow(wm: WindowManager, drop: Teardrop) {
        try {
            wm.addView(drop, drop.params)
        } catch (e: Exception) {}
    }

    fun updateWindow(wm: WindowManager, rotation: Int) {
        val drop = teardrops[rotation]!!
        updateWindow(wm, drop)
    }

    fun updateWindow(wm: WindowManager, drop: Teardrop) {
        try {
            wm.updateViewLayout(drop, drop.params)
        } catch (e: Exception) {}
    }

    fun removeWindow(wm: WindowManager, rotation: Int) {
        val drop = teardrops[rotation]!!
        removeWindow(wm, drop)
    }

    fun removeWindow(wm: WindowManager, drop: Teardrop) {
        try {
            wm.removeView(drop)
        } catch (e: Exception) {}
    }
}