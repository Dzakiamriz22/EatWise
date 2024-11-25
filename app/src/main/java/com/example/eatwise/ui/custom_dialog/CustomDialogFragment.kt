package com.example.eatwise.ui.custom_dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.eatwise.R
import com.example.eatwise.databinding.FragmentCustomDialogBinding

class CustomDialogFragment : DialogFragment(R.layout.fragment_custom_dialog) {
    private val binding by viewBinding(FragmentCustomDialogBinding::bind)

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setLayout(
            (resources.displayMetrics.widthPixels * 0.9).toInt(),
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog?.setCancelable(true)
    }
}