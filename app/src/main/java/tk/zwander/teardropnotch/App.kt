package tk.zwander.teardropnotch

import android.app.Application
import org.lsposed.hiddenapibypass.HiddenApiBypass

/**
 * Used to invoke the hidden API blacklist bypass as soon as the app starts.
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        HiddenApiBypass.addHiddenApiExemptions("L")
    }
}