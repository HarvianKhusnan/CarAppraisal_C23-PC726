package com.example.carappraisal.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.example.carappraisal.R

class NameCustomView : AppCompatEditText {

    private lateinit var icon : Drawable

    constructor(context: Context) : super(context){
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs){
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr){
        init()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }

    private fun init(){
        icon = ContextCompat.getDrawable(context, R.drawable.iconperson) as Drawable
        onShow(icon)
    }

    private fun onShow(icon: Drawable){
        drawablesButton(startOfText = icon)
    }

    private fun drawablesButton(
        startOfText: Drawable? = null,
        topOfText: Drawable? = null,
        endOfText: Drawable? = null,
        bottomOfText: Drawable? = null
    ) {
        setCompoundDrawablesWithIntrinsicBounds(
            startOfText,
            topOfText,
            endOfText,
            bottomOfText
        )
    }
}