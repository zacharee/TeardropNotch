package tk.zwander.teardropnotch

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.content.SharedPreferences
import android.os.Handler
import android.os.Looper
import android.os.ServiceManager
import android.util.Log
import android.view.*
import android.view.accessibility.AccessibilityEvent
import tk.zwander.teardropnotch.teardrop.TeardropHandler

class Accessibility : AccessibilityService(), SharedPreferences.OnSharedPreferenceChangeListener {
    private val prefs by lazy { PrefManager.getInstance(this) }
    private val wm by lazy { getSystemService(Context.WINDOW_SERVICE) as WindowManager }
    private val iWm by lazy { IWindowManager.Stub.asInterface(ServiceManager.getService(Context.WINDOW_SERVICE)) }
    private val view by lazy { TeardropHandler(this) }

    private val rotationListener = object : IRotationWatcher.Stub() {
        override fun onRotationChanged(rotation: Int) {
            Handler(Looper.getMainLooper()).post {
                view.onRotation(wm, rotation)
            }
        }
    }

    override fun onInterrupt() {}
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {}

    override fun onServiceConnected() {
        super.onServiceConnected()

        prefs.prefs.registerOnSharedPreferenceChangeListener(this)
        iWm.watchRotation(rotationListener, Display.DEFAULT_DISPLAY)
        view.onRotation(wm, wm.defaultDisplay.rotation)
        view.onNewCenterRatio(prefs.customCenterRatio)
        updateWindowAddState()
        Log.e("Teardrop", "accessibility started")
    }

    override fun onDestroy() {
        super.onDestroy()
        prefs.prefs.unregisterOnSharedPreferenceChangeListener(this)
        iWm.removeRotationWatcher(rotationListener)
        updateWindowAddState(true)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        when (key) {
            PrefManager.KEY_ENABLED -> {
                updateWindowAddState()
            }
            PrefManager.KEY_CUSTOM_WIDTH -> {
                view.onNewDimensions(wm, wm.defaultDisplay.rotation)
            }
            PrefManager.KEY_CUSTOM_HEIGHT -> {
                view.onNewDimensions(wm, wm.defaultDisplay.rotation)
            }
            PrefManager.KEY_CUSTOM_CENTER_WIDTH -> {
                view.onNewCenterRatio(prefs.customCenterRatio)
            }
        }
    }

    private fun updateWindowAddState(destroying: Boolean = false) {
        if (prefs.isEnabled && !destroying) {
            view.addWindow(wm, wm.defaultDisplay.rotation)
        } else {
            view.removeWindow(wm, wm.defaultDisplay.rotation)
        }
    }
}