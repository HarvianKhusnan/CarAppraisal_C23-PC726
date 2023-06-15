package com.example.carappraisal.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.InputType
import android.util.AttributeSet
import android.util.Patterns
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.MutableLiveData
import com.example.carappraisal.R

class EmailCustomView : AppCompatEditText {

    private val errorMsg = MutableLiveData<String>()
    private val hideErrorMsg = MutableLiveData<Boolean>()
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
        icon = ContextCompat.getDrawable(context, R.drawable.icon_envelope_closed) as Drawable
        onShow(icon)
        inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        doAfterTextChanged { text ->
            if(text?.isNullOrEmpty() == true){
                setError(context.getString(R.string.email_empty))
            }else{
                if(!Patterns.EMAIL_ADDRESS.matcher(text).matches()){
                    setError(context.getString(R.string.wrong_email))
                }else{
                    errorHide()
                }
            }
        }
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

    private fun errorHide(){
        hideErrorMsg.value = true
    }

    private fun setError(msg : String){
        errorMsg.value = msg
    }
}