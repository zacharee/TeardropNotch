package tk.zwander.teardropnotch

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class PrefManager private constructor(context: Context) : ContextWrapper(context) {
    companion object {
        @SuppressLint("StaticFieldLeak")
        private var instance: PrefManager? = null

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

    val prefs = PreferenceManager.getDefaultSharedPreferences(this)

    var isEnabled: Boolean
        get() = getBoolean(KEY_ENABLED)
        set(value) {
            putBoolean(KEY_ENABLED, value)
        }

    var customWidth: Int
        get() = getInt(KEY_CUSTOM_WIDTH, statusBarHeight * 2)
        set(value) {
            putInt(KEY_CUSTOM_WIDTH, value)
        }

    var customCenterRatio: Float
        get() = getInt(KEY_CUSTOM_CENTER_WIDTH, 450) / 10f
        set(value) {
            putInt(KEY_CUSTOM_CENTER_WIDTH, (value * 10).toInt())
        }

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