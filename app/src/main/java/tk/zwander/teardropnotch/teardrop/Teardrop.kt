package tk.zwander.teardropnotch.teardrop

import android.content.Context
import android.graphics.PixelFormat
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import tk.zwander.teardropnotch.PrefManager
import tk.zwander.teardropnotch.R

abstract class Teardrop : FrameLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    protected val prefs = PrefManager.getInstance(context)
    val params by lazy {
        WindowManager.LayoutParams().apply {
            type = WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY
            flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS or
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            format = PixelFormat.RGBA_8888

            width = paramWidth
            height = paramHeight
            gravity = Gravity.CENTER or paramGravity
        }
    }

    protected abstract val paramWidth: Int
    protected abstract val paramHeight: Int
    protected abstract val paramGravity: Int

    protected val leftCorner by lazy { findViewById<View>(R.id.left_corner) }
    protected val rightCorner by lazy { findViewById<View>(R.id.right_corner) }
    protected val center by lazy { findViewById<View>(R.id.center) }

    protected abstract fun setCenterSize(centerSize: Int)
    protected abstract fun setCornerSize(cornerSize: Int)

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        onNewCenterRatio(prefs.customCenterRatio)
    }

    fun onNewDimensions() {
        params.width = paramWidth
        params.height = paramHeight

        onNewCenterRatio(prefs.customCenterRatio)
    }

    fun onNewCenterRatio(ratio: Float) {
        val centerSize = (prefs.customWidth * ratio / 100f).toInt()
        val cornerSize = ((prefs.customWidth - centerSize) / 2f).toInt()

        setCenterSize(centerSize)
        setCornerSize(cornerSize)
    }
}