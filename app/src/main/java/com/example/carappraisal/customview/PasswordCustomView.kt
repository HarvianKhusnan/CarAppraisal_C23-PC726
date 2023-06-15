package com.example.carappraisal.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import com.example.carappraisal.R

class PasswordCustomView : AppCompatEditText {

    private val errorMsg = MutableLiveData<String>()
    private val errorHide = MutableLiveData<Boolean>()
    private lateinit var icon: Drawable

    constructor(context: Context) : super(context){
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context,attrs){
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr){
        init()
    }
    override fun onDraw(canvas: Canvas?){
        super.onDraw(canvas)
    }

    private fun init(){
        icon = ContextCompat.getDrawable(context, R.drawable.icon_key_) as Drawable
        onShow(icon)
        addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(text.toString().length < 8){
                    error = resources.getString(R.string.password_length)
                }else{
                    error = null
                }
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun onShow(icon: Drawable){
        drawableButton(startOfText = icon)
    }

    private fun drawableButton(
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