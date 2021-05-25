package tk.zwander.teardropnotch.teardrop

import android.content.Context
import android.graphics.PixelFormat
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import tk.zwander.teardropnotch.util.PrefManager
import tk.zwander.teardropnotch.R

/**
 * The base Teardrop View. Handles most base logic, while delegating
 * specific behavior to sub-classes.
 */
abstract class Teardrop : FrameLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    /**
     * A reference to the preference store.
     */
    protected val prefs = PrefManager.getInstance(context)

    /**
     * The base layout params for the View. Type, flags, and format are all constant
     * for all Views. Width, height, and gravity depend on the View.
     */
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

    /**
     * Sub-classes should implement this to provide the
     * width for the overlay.
     */
    protected abstract val paramWidth: Int

    /**
     * Sub-classes should implement this to provide the
     * height for the overlay.
     */
    protected abstract val paramHeight: Int

    /**
     * Sub-classes should implement this to provide the
     * gravity for the overlay.
     */
    protected abstract val paramGravity: Int

    /**
     * A reference to the left or top part of the notch, depending
     * on the orientation.
     */
    protected val leftCorner by lazy { findViewById<View>(R.id.left_corner) }

    /**
     * A reference to the center part of the notch.
     */
    protected val rightCorner by lazy { findViewById<View>(R.id.right_corner) }

    /**
     * A reference to the right or bottom part of the notch, depending
     * on the orientation.
     */
    protected val center by lazy { findViewById<View>(R.id.center) }

    /**
     * Sub-classes should implement this to update the size of the
     * center part of the notch (adjust the width or height, depending
     * on which orientation this View is for).
     */
    protected abstract fun setCenterSize(centerSize: Int)

    /**
     * Sub-classes should implement this to update the size of the
     * sides of the notch (adjust the width or height, depending
     * on which orientation this View is for).
     */
    protected abstract fun setCornerSize(cornerSize: Int)

    /**
     * Update the center size when the View attaches.
     */
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        onNewCenterRatio(prefs.customCenterRatio)
    }

    /**
     * Called when the user updates the size of the notch.
     */
    fun onNewDimensions() {
        params.width = paramWidth
        params.height = paramHeight

        onNewCenterRatio(prefs.customCenterRatio)
    }

    /**
     * Called when the center ratio or dimensions change,
     * to properly set the size the notch elements.
     */
    fun onNewCenterRatio(ratio: Float) {
        val centerSize = (prefs.customWidth * ratio / 100f).toInt()
        val cornerSize = ((prefs.customWidth - centerSize) / 2f).toInt()

        setCenterSize(centerSize)
        setCornerSize(cornerSize)
    }
}