package tk.zwander.teardropnotch.teardrop

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity

abstract class TeardropHorizontal : Teardrop {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override val paramWidth: Int
        get() = prefs.customHeight
    override val paramHeight: Int
        get() = prefs.customWidth

    override fun setCenterSize(centerSize: Int) {
        center.layoutParams = center.layoutParams.apply {
            height = centerSize
        }
    }

    override fun setCornerSize(cornerSize: Int) {
        leftCorner.layoutParams = leftCorner.layoutParams.apply {
            height = cornerSize
        }

        rightCorner.layoutParams = rightCorner.layoutParams.apply {
            height = cornerSize
        }
    }
}

class Teardrop90 : TeardropHorizontal {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override val paramGravity: Int
        get() = Gravity.LEFT
}

class Teardrop270 : TeardropHorizontal {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override val paramGravity: Int
        get() = Gravity.RIGHT
}