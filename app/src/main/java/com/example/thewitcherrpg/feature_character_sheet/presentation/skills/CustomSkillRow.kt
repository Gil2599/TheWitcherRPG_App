package com.example.thewitcherrpg.feature_character_sheet.presentation.skills

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.example.thewitcherrpg.R
import com.example.thewitcherrpg.TheWitcherTRPGApp
import com.example.thewitcherrpg.databinding.CustomEquippedRowBinding
import com.example.thewitcherrpg.databinding.CustomSkillRowBinding
import com.example.thewitcherrpg.feature_character_sheet.domain.models.EquipmentItem

/**
 * TODO: document your custom view class.
 */
class CustomSkillRow constructor(context: Context, attributeSet: AttributeSet) :
    LinearLayout(context, attributeSet) {
    private var _binding: CustomSkillRowBinding? = null
    private val binding get() = _binding!!

    init {
        _binding = CustomSkillRowBinding.inflate(
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater,
            this
        )
    }

    fun setSkillText(skill: String){
        binding.textViewSkill.text = skill
    }
}