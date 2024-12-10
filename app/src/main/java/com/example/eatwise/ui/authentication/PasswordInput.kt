package com.example.eatwise.ui.authentication

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import com.example.eatwise.R
import com.google.android.material.textfield.TextInputEditText

class PasswordInput @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.editTextStyle
) : TextInputEditText(context, attrs, defStyleAttr) {

    companion object {
        private const val MIN_PASSWORD_LENGTH = 8
    }

    init {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validatePassword(s)
            }
        })
    }

    private fun validatePassword(input: CharSequence?) {
        error = if (input.isNullOrEmpty() || input.length < MIN_PASSWORD_LENGTH) {
            resources.getString(R.string.invalid_password)
        } else {
            null
        }
    }
}