package tk.zwander.teardropnotch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import tk.zwander.seekbarpreference.SeekBarPreference
import tk.zwander.teardropnotch.util.PrefManager
import tk.zwander.teardropnotch.util.displayCompat
import tk.zwander.teardropnotch.util.realSizeAbsolute
import tk.zwander.teardropnotch.util.statusBarHeight

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    class MainFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.prefs_main, rootKey)

            findPreference<SeekBarPreference>(PrefManager.KEY_CUSTOM_WIDTH)?.apply {
                minValue = 0
                maxValue = requireContext().displayCompat.realSizeAbsolute.x
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