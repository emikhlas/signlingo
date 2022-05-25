package com.signlingo.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.signlingo.R

class RoundedButton : AppCompatButton {
    private lateinit var rounded: Drawable
    private var txtColor: Int = 0

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        background = rounded
        setTextColor(txtColor)
    }

    private fun init() {
        txtColor = ContextCompat.getColor(context, android.R.color.white)
        rounded = ContextCompat.getDrawable(context, R.drawable.button_rounded) as Drawable
    }
}