package tk.zwander.teardropnotch.util

import android.content.Context
import android.graphics.Point
import android.os.Build
import android.view.Display
import android.view.Surface
import android.view.WindowManager

val Context.windowManager: WindowManager
    get() = getSystemService(Context.WINDOW_SERVICE) as WindowManager

@Suppress("DEPRECATION")
val Context.displayCompat: Display
    get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        display
    } else {
        windowManager.defaultDisplay
    }

val Display.realSizeAbsolute: Point
    get() = Point().apply {
        getRealSize(this)
        if (rotation.run { this != Surface.ROTATION_0 && this != Surface.ROTATION_180 }) {
            val x = this.x
            val y = this.y

            this.x = y
            this.y = x
        }
    }