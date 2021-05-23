package tk.zwander.teardropnotch

import android.content.Context
import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Surface
import android.view.WindowManager
import androidx.preference.PreferenceFragmentCompat
import tk.zwander.seekbarpreference.SeekBarPreference
import tk.zwander.teardropnotch.util.PrefManager
import tk.zwander.teardropnotch.util.statusBarHeight

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    class MainFragment : PreferenceFragmentCompat() {
        private val wm by lazy { requireContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager }
        private val realSize: Point
            get() = Point().apply {
                wm.defaultDisplay.getRealSize(this)
                if (wm.defaultDisplay.rotation.run { this != Surface.ROTATION_0 && this != Surface.ROTATION_180 }) {
                    val x = this.x
                    val y = this.y

                    this.x = y
                    this.y = x
                }
            }

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.prefs_main, rootKey)

            findPreference<SeekBarPreference>(PrefManager.KEY_CUSTOM_WIDTH)?.apply {
                minValue = 0
                maxValue = realSize.x
                defaultValue = requireContext().statusBarHeight * 2
            }

            findPreference<SeekBarPreference>(PrefManager.KEY_CUSTOM_CENTER_WIDTH)?.apply {
                minValue = 0
                maxValue = 1000
                defaultValue = 450
            }

            findPreference<SeekBarPreference>(PrefManager.KEY_CUSTOM_HEIGHT)?.apply {
                minValue = 0
                maxValue = requireContext().statusBarHeight * 3
                defaultValue = requireContext().statusBarHeight
            }
        }
    }
}