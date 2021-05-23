package tk.zwander.teardropnotch.util

import android.content.Context

val Context.statusBarHeight: Int
    get() = resources.getDimensionPixelSize(com.android.internal.R.dimen.status_bar_height)