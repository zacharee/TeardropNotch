package tk.zwander.teardropnotch

import android.content.Context
import android.graphics.PixelFormat
import android.util.AttributeSet
import android.view.*
import android.widget.FrameLayout

class TeardropView : FrameLayout {
    private val prefs = PrefManager.getInstance(context)
    private val params = WindowManager.LayoutParams().apply {
        width = prefs.customWidth
        height = prefs.customHeight
        gravity = Gravity.TOP or Gravity.CENTER
        type = WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY
        flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS or
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        format = PixelFormat.RGBA_8888
    }

    private val leftCorner by lazy { findViewById<View>(R.id.left_corner) }
    private val rightCorner by lazy { findViewById<View>(R.id.right_corner) }
    private val center by lazy { findViewById<View>(R.id.center) }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun onApplyWindowInsets(insets: WindowInsets?): WindowInsets {
        return super.onApplyWindowInsets(insets)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        onNewCenterRatio(prefs.customCenterRatio)
    }

    fun onNewDimensions(wm: WindowManager, width: Int, height: Int) {
        params.width = width
        params.height = height
        updateWindow(wm)

        onNewCenterRatio(prefs.customCenterRatio)
    }

    fun onNewCenterRatio(ratio: Float) {
        center.layoutParams = center.layoutParams.apply {
            width = (params.width * ratio / 100f).toInt()
        }

        val cornerWidth = ((params.width - center.width) / 2f).toInt()

        leftCorner.layoutParams = leftCorner.layoutParams.apply {
            width = cornerWidth
        }
        rightCorner.layoutParams = rightCorner.layoutParams.apply {
            width = cornerWidth
        }
    }

    fun onRotation(wm: WindowManager, rotation: Int) {

    }

    fun addWindow(wm: WindowManager) {
        try {
            wm.addView(this, params)
        } catch (e: Exception) {}
    }

    fun updateWindow(wm: WindowManager) {
        try {
            wm.updateViewLayout(this, params)
        } catch (e: Exception) {}
    }

    fun removeWindow(wm: WindowManager) {
        try {
            wm.removeView(this)
        } catch (e: Exception) {}
    }
}