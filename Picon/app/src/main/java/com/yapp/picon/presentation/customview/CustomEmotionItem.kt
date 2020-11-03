package com.yapp.picon.presentation.customview

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import com.yapp.picon.R
import kotlinx.android.synthetic.main.custom_emotion_item.view.*

@RequiresApi(Build.VERSION_CODES.M)
open class CustomEmotionItem: ConstraintLayout {
    private var circleColor: Int = resources.getColor(R.color.very_light_brown, null)
    private var emotionText: String = resources.getString(R.string.nav_custom_text_soft_blue)

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs)
    }
    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(context, attrs)
    }

    @SuppressLint("ResourceAsColor")
    private fun init(context: Context, attrs: AttributeSet?) {
        val view: View = View.inflate(context, R.layout.custom_emotion_item, this)

        val typeArray: TypedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CustomEmotionItem,
            0, 0
        )

        try {
            circleColor = typeArray.getColor(R.styleable.CustomEmotionItem_circleColor, R.color.black)
            emotionText = typeArray.getString(R.styleable.CustomEmotionItem_emotionText).toString()
        } finally {
            typeArray.recycle()
        }

        setCircleColor(view)
        view.emotion_item_tv.text = emotionText
    }

    private fun setCircleColor(view: View) {
        when(circleColor) {
            getColorRes(R.color.soft_blue) -> setCircle(view, R.drawable.ic_custom_circle_soft_blue)
            getColorRes(R.color.cornflower) -> setCircle(view, R.drawable.ic_custom_circle_cornflower)
            getColorRes(R.color.bluegrey) -> setCircle(view, R.drawable.ic_custom_circle_bluegrey)
            getColorRes(R.color.very_light_brown) -> setCircle(view, R.drawable.ic_custom_circle_very_light_brown)
            getColorRes(R.color.warm_grey) -> setCircle(view, R.drawable.ic_custom_circle_warm_grey)
        }
    }

    private fun getColorRes(id: Int): Int {
        return resources.getColor(id, null)
    }

    private fun setCircle(view: View, id: Int) {
        view.emotion_item_color_iv.setImageResource(id)
    }
}