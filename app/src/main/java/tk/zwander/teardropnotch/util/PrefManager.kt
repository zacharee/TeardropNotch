package tk.zwander.teardropnotch.util

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import tk.zwander.teardropnotch.util.statusBarHeight

/**
 * Handle persisting data.
 */
class PrefManager private constructor(context: Context) : ContextWrapper(context) {
    companion object {
        /**
         * A singleton instance.
         */
        @SuppressLint("StaticFieldLeak")
        private var instance: PrefManager? = null

        /**
         * Retrieve the singleton instance. If there is none yet,
         * create it and return it, before setting it to the instance field.
         */
        fun getInstance(context: Context): PrefManager {
            val appContext = if (context is Application) context else context.applicationContext

            return instance ?: PrefManager(appContext).apply {
                instance = this
            }
        }

        const val KEY_ENABLED = "notch_enabled"
        const val KEY_CUSTOM_CENTER_WIDTH = "notch_custom_center_width"
        const val KEY_CUSTOM_WIDTH = "notch_custom_width"
        const val KEY_CUSTOM_HEIGHT = "notch_custom_height"
    }

    /**
     * A reference to the actual backing SharedPreferences.
     */
    val prefs = PreferenceManager.getDefaultSharedPreferences(this)

    /**
     * Whether the teardrop is enabled.
     */
    var isEnabled: Boolean
        get() = getBoolean(KEY_ENABLED)
        set(value) {
            putBoolean(KEY_ENABLED, value)
        }

    /**
     * The width for the teardrop.
     */
    var customWidth: Int
        get() = getInt(KEY_CUSTOM_WIDTH, statusBarHeight * 2)
        set(value) {
            putInt(KEY_CUSTOM_WIDTH, value)
        }

    /**
     * The ratio for the amount of space the center part takes up.
     */
    var customCenterRatio: Float
        get() = getInt(KEY_CUSTOM_CENTER_WIDTH, 450) / 10f
        set(value) {
            putInt(KEY_CUSTOM_CENTER_WIDTH, (value * 10).toInt())
        }

    /**
     * The height for the overlay.
     */
    var customHeight: Int
        get() = getInt(KEY_CUSTOM_HEIGHT, statusBarHeight)
        set(value) {
            putInt(KEY_CUSTOM_HEIGHT, value)
        }

    fun getBoolean(key: String, def: Boolean = false) = prefs.getBoolean(key, def)
    fun getInt(key: String, def: Int = 0) = prefs.getInt(key, def)
    fun getFloat(key: String, def: Float = 0f) = prefs.getFloat(key, def)

    fun putBoolean(key: String, value: Boolean) = prefs.edit {
        putBoolean(key, value)
    }
    fun putInt(key: String, value: Int) = prefs.edit {
        putInt(key, value)
    }
    fun putFloat(key: String, value: Float) = prefs.edit {
        putFloat(key, value)
    }
}