package com.example.eatwise.ui.authentication

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import com.example.eatwise.R
import com.google.android.material.textfield.TextInputEditText
import java.util.regex.Pattern

class EmailInput @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.editTextStyle
) : TextInputEditText(context, attrs, defStyleAttr) {

    private val emailPattern: Pattern = Pattern.compile(
        "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    )

    init {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateEmail(s)
            }
        })
    }

    private fun validateEmail(input: CharSequence?) {
        error = if (input.isNullOrEmpty() || !emailPattern.matcher(input).matches()) {
            resources.getString(R.string.invalid_email)
        } else {
            null
        }
    }
}