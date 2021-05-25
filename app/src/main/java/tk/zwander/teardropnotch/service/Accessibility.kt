package tk.zwander.teardropnotch.service

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.content.SharedPreferences
import android.os.Handler
import android.os.Looper
import android.os.ServiceManager
import android.util.Log
import android.view.*
import android.view.accessibility.AccessibilityEvent
import tk.zwander.teardropnotch.util.PrefManager
import tk.zwander.teardropnotch.teardrop.TeardropHandler
import tk.zwander.teardropnotch.util.displayCompat
import tk.zwander.teardropnotch.util.windowManager

/**
 * The Accessibility Service for this app. Used to manage adding and removing the overlay.
 *
 * An Accessibility Service is used because it allows the use of a special overlay that shows
 * on top of almost all other elements, including the status bar.
 */
class Accessibility : AccessibilityService(), SharedPreferences.OnSharedPreferenceChangeListener {
    /**
     * A reference to the preference store.
     */
    private val prefs by lazy { PrefManager.getInstance(this) }

    /**
     * A reference to the Accessibility WindowManager.
     */
    private val wm by lazy { windowManager }

    /**
     * A reference to the internal WindowManager. Used to watch screen orientation.
     */
    private val iWm by lazy { IWindowManager.Stub.asInterface(ServiceManager.getService(Context.WINDOW_SERVICE)) }

    /**
     * The teardrop View handler.
     */
    private val view by lazy { TeardropHandler(this) }

    /**
     * Used to respond to changes in screen orientation.
     */
    private val rotationListener = object : IRotationWatcher.Stub() {
        override fun onRotationChanged(rotation: Int) {
            Handler(Looper.getMainLooper()).post {
                view.onRotation(wm, rotation)
            }
        }
    }

    override fun onInterrupt() {}
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {}

    /**
     * Perform initial setup.
     */
    override fun onServiceConnected() {
        super.onServiceConnected()

        prefs.prefs.registerOnSharedPreferenceChangeListener(this)
        iWm.watchRotation(rotationListener, Display.DEFAULT_DISPLAY)
        view.onRotation(wm, displayCompat.rotation)
        view.onNewCenterRatio(prefs.customCenterRatio)
        updateWindowAddState()
        Log.e("Teardrop", "accessibility started")
    }

    /**
     * Perform cleanup.
     */
    override fun onDestroy() {
        super.onDestroy()
        prefs.prefs.unregisterOnSharedPreferenceChangeListener(this)
        iWm.removeRotationWatcher(rotationListener)
        updateWindowAddState(true)
    }

    /**
     * Respond to changes in preferences.
     */
    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        when (key) {
            PrefManager.KEY_ENABLED -> {
                updateWindowAddState()
            }
            PrefManager.KEY_CUSTOM_WIDTH -> {
                view.onNewDimensions(wm, displayCompat.rotation)
            }
            PrefManager.KEY_CUSTOM_HEIGHT -> {
                view.onNewDimensions(wm, displayCompat.rotation)
            }
            PrefManager.KEY_CUSTOM_CENTER_WIDTH -> {
                view.onNewCenterRatio(prefs.customCenterRatio)
            }
        }
    }

    /**
     * Called when the service starts or ends, or the enabled state changes.
     */
    private fun updateWindowAddState(destroying: Boolean = false) {
        if (prefs.isEnabled && !destroying) {
            view.addWindow(wm, displayCompat.rotation)
        } else {
            view.removeWindow(wm, displayCompat.rotation)
        }
    }
}