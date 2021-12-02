package com.example.thewitcherrpg.feature_character_sheet.presentation.skills

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import com.example.thewitcherrpg.R
import com.example.thewitcherrpg.databinding.CustomSkillRowBinding

class CustomSkillRow constructor(context: Context, attributeSet: AttributeSet) :
    LinearLayout(context, attributeSet) {
    private var _binding: CustomSkillRowBinding? = null
    private val binding get() = _binding!!

    private var editModifier: Boolean = false

    init {
        _binding = CustomSkillRowBinding.inflate(
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater,
            this
        )

        binding.editTextNumber.setRawInputType(0)
        binding.editTextNumber.setOnLongClickListener {
            Log.d("test", "long click")
            binding.editTextNumber.requestFocus()
            editModifier = true
            true
        }
        binding.editTextNumber.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                Log.d("test", "lost focus")
                editModifier = false
            } else
            {
                Log.d("test", "has focus")
            }
        }

        val a = context.obtainStyledAttributes(attributeSet, R.styleable.CustomSkillRow)
        if (a.hasValue(R.styleable.CustomSkillRow_myTag)){
            val tag = a.getString(R.styleable.CustomSkillRow_myTag)
            if (tag != null) {
                binding.editTextNumber.tag = tag
            }
        }
        a.recycle()
    }

    fun setSkillText(skill: String){
        binding.textViewSkill.text = skill
    }

    @SuppressLint("SetTextI18n")
    fun setModifier(value: Int){
        if (value >= 0){
            binding.textViewModifier.text = "+$value"
        }

    }

    fun getEditText(): EditText{
        return binding.editTextNumber
    }

    fun hideModifier(){
        binding.textViewModifier.visibility = View.INVISIBLE
    }

    fun setSkillValue(value: Int){
        binding.editTextNumber.setText(value.toString())
    }
}