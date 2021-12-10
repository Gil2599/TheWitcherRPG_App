package com.example.thewitcherrpg.feature_character_sheet.presentation.skills

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.*
import com.example.thewitcherrpg.R
import com.example.thewitcherrpg.TheWitcherTRPGApp
import com.example.thewitcherrpg.databinding.CustomSkillRowBinding
import com.google.android.material.snackbar.Snackbar


@InverseBindingMethods(
    InverseBindingMethod(
        type = CustomSkillRow::class,
        attribute = "modifier",
        method = "setModifier"
    ),
    InverseBindingMethod(
        type = CustomSkillRow::class,
        attribute = "skillValue",
        method = "setSkillValue"
    )
)


class CustomSkillRow constructor(context: Context, attributeSet: AttributeSet) :
    LinearLayout(context, attributeSet) {
    private var _binding: CustomSkillRowBinding? = null
    private val binding get() = _binding!!

    var editModifier: Boolean = false
    var modifierValue = 0
    var inCharCreation = false

    init {
        _binding = CustomSkillRowBinding.inflate(
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater,
            this
        )

        binding.editTextNumber.setRawInputType(0)

        binding.editTextNumber.setOnLongClickListener {
            binding.textViewModifier.visibility = View.VISIBLE
            binding.editTextNumber.requestFocus()
            editModifier = true
            binding.imageView3.visibility = View.VISIBLE
            true
        }

        binding.editTextNumber.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                if (modifierValue == 0) binding.textViewModifier.visibility = View.INVISIBLE
                editModifier = false
                binding.imageView3.visibility = View.INVISIBLE
            }
        }

        val a = context.obtainStyledAttributes(attributeSet, R.styleable.CustomSkillRow)
        if (a.hasValue(R.styleable.CustomSkillRow_myTag)) {
            val tag = a.getString(R.styleable.CustomSkillRow_myTag)
            if (tag != null) {
                binding.editTextNumber.tag = tag
            }
        }
        a.recycle()
    }

    fun setSkillText(skill: String) {
        binding.textViewSkill.text = skill
    }

    @SuppressLint("SetTextI18n")
    fun setModifier(value: Int) {
        val colors = TypedValue()
        context?.theme?.resolveAttribute(R.attr.colorContainer, colors, true)
        val red = colors.data

        context?.theme?.resolveAttribute(R.attr.colorOnSecondaryContainer, colors, true)
        val green = colors.data

        context?.theme?.resolveAttribute(R.attr.textFillColor, colors, true)
        val textColor = colors.data

        if (value >= 0) {
            binding.textViewModifier.text = "+$value"
            if (value > 0) {
                binding.textViewModifier.setTextColor(green)
            } else {
                binding.textViewModifier.setTextColor(textColor)
            }
        } else {
            binding.textViewModifier.text = value.toString()
            binding.textViewModifier.setTextColor(red)
        }
        if (value == 0) {
            if (!editModifier) binding.textViewModifier.visibility = View.INVISIBLE
        } else binding.textViewModifier.visibility = View.VISIBLE

        modifierValue = value
    }

    fun setInCharCreationMode(professionSkill: Boolean) {
        val colors = TypedValue()
        context?.theme?.resolveAttribute(R.attr.colorError, colors, true)
        val disabled = colors.data

        if (!professionSkill) {
            binding.editTextNumber.isEnabled = false
            binding.textViewSkill.setTextColor(disabled)
        }

        binding.editTextNumber.setOnLongClickListener {
            true
        }
        inCharCreation = true
    }

    fun setSkillValue(value: Int) {
        binding.editTextNumber.setText(value.toString())
    }

    fun setDefSkillColor() {
        binding.textViewSkill.setTextColor(
            ContextCompat.getColor(
                TheWitcherTRPGApp.getContext()!!,
                R.color.relic
            )
        )
    }

    companion object {
        @BindingAdapter(value = ["app:modifier"])
        @JvmStatic
        fun setModifier(view: CustomSkillRow, value: Int): Int {
            view.setModifier(value)
            return value
        }

        @BindingAdapter(value = ["app:skillValue"])
        @JvmStatic
        fun setSkillValue(view: CustomSkillRow, value: Int): Int {
            view.setSkillValue(value)
            return value
        }
    }
}