package com.witcher.thewitcherrpg.core.presentation

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.witcher.thewitcherrpg.databinding.CustomTitleBinding

class CustomTitleView constructor(context: Context, attributeSet: AttributeSet? = null) :
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