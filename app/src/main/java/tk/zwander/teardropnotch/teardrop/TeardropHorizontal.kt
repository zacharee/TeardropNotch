package tk.zwander.teardropnotch.teardrop

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity

/**
 * The base class for teardrops for horizontal screen rotations.
 */
abstract class TeardropHorizontal : Teardrop {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    /**
     * The width of the overlay should be the custom
     * height the user set.
     */
    override val paramWidth: Int
        get() = prefs.customHeight

    /**
     * The height of the overlay should be the custom
     * width of the user set.
     */
    override val paramHeight: Int
        get() = prefs.customWidth

    /**
     * Set the height of the center area.
     */
    override fun setCenterSize(centerSize: Int) {
        center.layoutParams = center.layoutParams.apply {
            height = centerSize
        }
    }

    /**
     * Set the heights of the sides.
     */
    override fun setCornerSize(cornerSize: Int) {
        leftCorner.layoutParams = leftCorner.layoutParams.apply {
            height = cornerSize
        }

        rightCorner.layoutParams = rightCorner.layoutParams.apply {
            height = cornerSize
        }
    }
}

/**
 * The teardrop for 90˚ rotation.
 */
class Teardrop90 : TeardropHorizontal {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    /**
     * This teardrop goes on the left of the screen.
     */
    override val paramGravity: Int
        get() = Gravity.LEFT
}

/**
 * The teardrop for 270˚ rotation.
 */
class Teardrop270 : TeardropHorizontal {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    /**
     * This teardrop goes on the right of the screen.
     */
    override val paramGravity: Int
        get() = Gravity.RIGHT
}