package tk.zwander.teardropnotch.teardrop

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity

/**
 * The base class for teardrops for vertical screen rotations.
 */
abstract class TeardropVertical : Teardrop {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    /**
     * The width of this overlay is the width the user set.
     */
    override val paramWidth: Int
        get() = prefs.customWidth

    /**
     * The height of this overlay is the height the user set.
     */
    override val paramHeight: Int
        get() = prefs.customHeight

    /**
     * Update the width of the center area.
     */
    override fun setCenterSize(centerSize: Int) {
        center.layoutParams = center.layoutParams.apply {
            width = centerSize
        }
    }

    /**
     * Update the widths of the sides.
     */
    override fun setCornerSize(cornerSize: Int) {
        leftCorner.layoutParams = leftCorner.layoutParams.apply {
            width = cornerSize
        }

        rightCorner.layoutParams = rightCorner.layoutParams.apply {
            width = cornerSize
        }
    }
}

/**
 * The teardrop for 0˚ rotation.
 */
class Teardrop0 : TeardropVertical {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    /**
     * This teardrop displays on the top of the display.
     */
    override val paramGravity: Int
        get() = Gravity.TOP
}

/**
 * The teardrop for 180˚ rotation.
 */
class Teardrop180 : TeardropVertical {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    /**
     * This teardrop is on the bottom of the display.
     */
    override val paramGravity: Int
        get() = Gravity.BOTTOM
}