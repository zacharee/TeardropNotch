package tk.zwander.teardropnotch.util

import android.content.Context
import android.graphics.Point
import android.hardware.display.DisplayManager
import android.os.Build
import android.view.Display
import android.view.Surface
import android.view.WindowManager

/**
 * A convenience method for getting a reference to the WindowManager.
 */
val Context.windowManager: WindowManager
    get() = getSystemService(Context.WINDOW_SERVICE) as WindowManager

/**
 * A convenience method for getting a reference to the DisplayManager.
 */
val Context.displayManager: DisplayManager
    get() = getSystemService(Context.DISPLAY_SERVICE) as DisplayManager

/**
 * A convenience method for getting the default display.
 */
@Suppress("DEPRECATION")
val Context.displayCompat: Display
    get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        displayManager.getDisplay(Display.DEFAULT_DISPLAY)
    } else {
        windowManager.defaultDisplay
    }

/**
 * Get the real size of the display, ignoring rotation.
 * This will always return the width and height of the display
 * as if the rotation were 0˚.
 */
val Display.realSizeAbsolute: Point
    get() = Point().apply {
        getRealSize(this)
        if (isLandscape) {
            val x = this.x
            val y = this.y

            this.x = y
            this.y = x
        }
    }

/**
 * A convenience method for checking if the screen is in landscape.
 */
val Display.isLandscape: Boolean
    get() = rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270