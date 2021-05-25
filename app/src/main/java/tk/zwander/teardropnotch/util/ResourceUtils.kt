package tk.zwander.teardropnotch.util

import android.content.Context

/**
 * A convenience method for retrieving the status bar height.
 */
val Context.statusBarHeight: Int
    get() = resources.getDimensionPixelSize(com.android.internal.R.dimen.status_bar_height)