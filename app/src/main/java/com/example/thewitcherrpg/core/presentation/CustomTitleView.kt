package com.example.thewitcherrpg.core.presentation

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout

class CustomTitleView constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {


    override fun setOnClickListener(l: OnClickListener?) {
        Toast.makeText(context, "Testing", Toast.LENGTH_SHORT).show()
        super.setOnClickListener(l)
    }
}