package tk.zwander.teardropnotch.teardrop

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity

abstract class TeardropVertical : Teardrop {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override val paramWidth: Int
        get() = prefs.customWidth
    override val paramHeight: Int
        get() = prefs.customHeight

    override fun setCenterSize(centerSize: Int) {
        center.layoutParams = center.layoutParams.apply {
            width = centerSize
        }
    }

    override fun setCornerSize(cornerSize: Int) {
        leftCorner.layoutParams = leftCorner.layoutParams.apply {
            width = cornerSize
        }

        rightCorner.layoutParams = rightCorner.layoutParams.apply {
            width = cornerSize
        }
    }
}

class Teardrop0 : TeardropVertical {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override val paramGravity: Int
        get() = Gravity.TOP
}

class Teardrop180 : TeardropVertical {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override val paramGravity: Int
        get() = Gravity.BOTTOM
}