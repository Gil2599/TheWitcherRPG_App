package com.example.thewitcherrpg.core.presentation

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.thewitcherrpg.R
import com.example.thewitcherrpg.databinding.CustomTitleBinding

class CustomTitleView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet) :
    ConstraintLayout(context, attributeSet) {
    private var _binding: CustomTitleBinding? = null
    private val binding get() = _binding!!

    init {
        _binding = CustomTitleBinding.inflate(context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater, this)
    }

    fun setTitle(title: String){
        binding.textView58.text = title
    }

    fun setTitleSize(size: Float){
        binding.textView58.textSize = size
    }
}