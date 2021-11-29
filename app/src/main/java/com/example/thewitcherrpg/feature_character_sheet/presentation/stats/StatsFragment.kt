package com.example.thewitcherrpg.feature_character_sheet.presentation.stats

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.text.*
import android.text.style.ForegroundColorSpan
import android.view.*
import androidx.fragment.app.Fragment
import com.example.thewitcherrpg.databinding.FragmentStatsBinding
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.bind
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.thewitcherrpg.R
import com.example.thewitcherrpg.TheWitcherTRPGApp
import com.example.thewitcherrpg.core.presentation.MainCharacterViewModel
import com.example.thewitcherrpg.databinding.CustomDialogEditStatsBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class StatsFragment : Fragment() {
    private var _binding: FragmentStatsBinding? = null
    private val binding get() = _binding!!

    private var focusedView: EditText? = null
    private var editModifier = false

    private val mainCharacterViewModel: MainCharacterViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_stats, container, false)
        val view = binding.root

        binding.lifecycleOwner = this
        binding.sharedViewModel = mainCharacterViewModel

        setObservers()

        binding.editIP.setOnClickListener {
            showDialogIP()
        }

        statsInit()

        binding.buttonPlus.setOnClickListener {
            increaseButton()
        }

        binding.buttonMinus.setOnClickListener {
            decreaseButton()
        }

        return view
    }

    @SuppressLint("SetTextI18n")
    private fun statsInit() {

        binding.editTextINT.setRawInputType(0)
        binding.editTextINT.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                binding.editTextINT.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.unfocused_outline_bkg)
                editModifier = false
                binding.editTextINT.text = getSpannableString(
                    "INT: ${
                        mainCharacterViewModel.intelligence.value.plus(
                            mainCharacterViewModel.intelligenceModifier.value
                        )
                    }", editModifier, mainCharacterViewModel.intelligence.value
                )
            } else {
                focusedView = binding.editTextINT
                binding.editTextINT.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.outline_bkg)
            }
        }
        binding.editTextINT.setOnLongClickListener {
            binding.editTextINT.setText("INT: ${mainCharacterViewModel.intelligence.value}" + " [${mainCharacterViewModel.intelligenceModifier.value}]")
            editModifier = true
            true
        }

        binding.editTextREF.setRawInputType(0)
        binding.editTextREF.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                binding.editTextREF.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.unfocused_outline_bkg)
                if (focusedView != binding.editTextREF) editModifier = false
            } else {
                focusedView = binding.editTextREF
                binding.editTextREF.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.outline_bkg)
            }
        }

        binding.editTextDEX.setRawInputType(0)
        binding.editTextDEX.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                binding.editTextDEX.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.unfocused_outline_bkg)
                editModifier = false
            } else {
                focusedView = binding.editTextDEX
                binding.editTextDEX.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.outline_bkg)
            }
        }

        binding.editTextBODY.setRawInputType(0)
        binding.editTextBODY.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                binding.editTextBODY.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.unfocused_outline_bkg)
                editModifier = false
            } else {
                focusedView = binding.editTextBODY
                binding.editTextBODY.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.outline_bkg)
            }
        }

        binding.editTextSPD.setRawInputType(0)
        binding.editTextSPD.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                binding.editTextSPD.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.unfocused_outline_bkg)
                editModifier = false
            } else {
                focusedView = binding.editTextSPD
                binding.editTextSPD.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.outline_bkg)
            }
        }

        binding.editTextEMP.setRawInputType(0)
        binding.editTextEMP.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                binding.editTextEMP.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.unfocused_outline_bkg)
                editModifier = false
            } else {
                focusedView = binding.editTextEMP
                binding.editTextEMP.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.outline_bkg)
            }
        }

        binding.editTextCRA.setRawInputType(0)
        binding.editTextCRA.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                binding.editTextCRA.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.unfocused_outline_bkg)
                editModifier = false
            } else {
                focusedView = binding.editTextCRA
                binding.editTextCRA.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.outline_bkg)
            }
        }

        binding.editTextWILL.setRawInputType(0)
        binding.editTextWILL.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                binding.editTextWILL.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.unfocused_outline_bkg)
                editModifier = false
            } else {
                focusedView = binding.editTextWILL
                binding.editTextWILL.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.outline_bkg)
            }
        }

        binding.editTextLUCK.setRawInputType(0)
        binding.editTextLUCK.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                binding.editTextLUCK.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.unfocused_outline_bkg)
                editModifier = false
            } else {
                focusedView = binding.editTextLUCK
                binding.editTextLUCK.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.outline_bkg)
            }
        }

        binding.editTextSTUN.setRawInputType(0)
        binding.editTextSTUN.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                binding.editTextSTUN.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.unfocused_outline_bkg)
                editModifier = false
            } else {
                focusedView = binding.editTextSTUN
                binding.editTextSTUN.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.outline_bkg)
            }
        }

        binding.editTextRUN.setRawInputType(0)
        binding.editTextRUN.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                binding.editTextRUN.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.unfocused_outline_bkg)
                editModifier = false
            } else {
                focusedView = binding.editTextRUN
                binding.editTextRUN.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.outline_bkg)
            }
        }

        binding.editTextLEAP.setRawInputType(0)
        binding.editTextLEAP.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                binding.editTextLEAP.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.unfocused_outline_bkg)
                editModifier = false
            } else {
                focusedView = binding.editTextLEAP
                binding.editTextLEAP.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.outline_bkg)
            }
        }

        binding.editTextMaxHP.setRawInputType(0)
        binding.editTextMaxHP.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                binding.editTextMaxHP.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.unfocused_outline_bkg)
                editModifier = false
            } else {
                focusedView = binding.editTextMaxHP
                binding.editTextMaxHP.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.outline_bkg)
            }
        }

        binding.editTextMaxSTA.setRawInputType(0)
        binding.editTextMaxSTA.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                binding.editTextMaxSTA.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.unfocused_outline_bkg)
                editModifier = false
            } else {
                focusedView = binding.editTextMaxSTA
                binding.editTextMaxSTA.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.outline_bkg)
            }
        }

        binding.editTextENC.setRawInputType(0)
        binding.editTextENC.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                binding.editTextENC.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.unfocused_outline_bkg)
                editModifier = false
            } else {
                focusedView = binding.editTextENC
                binding.editTextENC.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.outline_bkg)
            }
        }

        binding.editTextREC.setRawInputType(0)
        binding.editTextREC.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                binding.editTextREC.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.unfocused_outline_bkg)
                editModifier = false
            } else {
                focusedView = binding.editTextREC
                binding.editTextREC.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.outline_bkg)
            }
        }

        binding.editTextPunch.focusable = View.NOT_FOCUSABLE
        binding.editTextKick.focusable = View.NOT_FOCUSABLE

        if (mainCharacterViewModel.inCharacterCreation.value) {
            binding.editTextSTUN.focusable = View.NOT_FOCUSABLE
            binding.editTextRUN.focusable = View.NOT_FOCUSABLE
            binding.editTextLEAP.focusable = View.NOT_FOCUSABLE
            binding.editTextMaxHP.focusable = View.NOT_FOCUSABLE
            binding.editTextMaxSTA.focusable = View.NOT_FOCUSABLE
            binding.editTextENC.focusable = View.NOT_FOCUSABLE
            binding.editTextREC.focusable = View.NOT_FOCUSABLE
        }
    }

    private fun increaseButton() {

        if (focusedView != null) {
            val stat = focusedView!!.tag.toString()
            if (!editModifier) {
                mainCharacterViewModel.onStatChange(stat, true)
            } else {
                mainCharacterViewModel.onStatModifierChange(stat, true)
            }
        }
    }

    private fun decreaseButton() {

        if (focusedView != null) {
            val stat = focusedView!!.tag.toString()
            if (!editModifier) {
                mainCharacterViewModel.onStatChange(stat, false)
            } else {
                mainCharacterViewModel.onStatModifierChange(stat, false)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        focusedView?.clearFocus()
        focusedView = null
    }

    private fun showDialogIP() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val bind: CustomDialogEditStatsBinding =
            CustomDialogEditStatsBinding.inflate(layoutInflater)
        dialog.setContentView(bind.root)

        bind.textViewCurrent.text = "CURRENT IP"

        bind.textView.text = mainCharacterViewModel.ip.value.toString()

        bind.editText.requestFocus()

        bind.buttonPlus.setOnClickListener {
            val value =
                if (bind.editText.text.isEmpty()) 0 else bind.editText.text.toString().toInt()
            mainCharacterViewModel.onIpChange(value)
            dialog.dismiss()
        }

        bind.buttonMinus.setOnClickListener {
            val value =
                if (bind.editText.text.isEmpty()) 0 else bind.editText.text.toString().toInt()
            mainCharacterViewModel.onIpChange(-value)
            dialog.dismiss()
        }
        dialog.show()
    }

    @SuppressLint("SetTextI18n")
    private fun setObservers() {
        lifecycleScope.launch(Dispatchers.IO) {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    mainCharacterViewModel.intelligenceModifier.collect { value ->
                        if (editModifier) binding.editTextINT.setText("INT: ${mainCharacterViewModel.intelligence.value}" + " [$value]")
                    }
                }
                launch {
                    mainCharacterViewModel.intelligence.collect { value ->
                        binding.editTextINT.text = getSpannableString(
                            "INT: ${
                                value.plus(
                                    mainCharacterViewModel.intelligenceModifier.value
                                )
                            }", editModifier, value
                        )
                    }
                }
            }
        }
    }

    private fun getSpannableString(
        string: String,
        editModifierMode: Boolean,
        statValue: Int
    ): SpannableStringBuilder {
        var value = ""
        var stat = ""
        if (!editModifierMode) {
            val stringArray = string.split(": ").toTypedArray()
            stat = stringArray[0]
            value = stringArray[1]
        }
        val spannable = SpannableStringBuilder(string)
        if (value.toInt() > statValue) {
            spannable.setSpan(
                ForegroundColorSpan(
                    ContextCompat.getColor(
                        TheWitcherTRPGApp.getContext()!!,
                        R.color.green
                    )
                ), stat.length + 1, string.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        } else if (value.toInt() < statValue) {
            spannable.setSpan(
                ForegroundColorSpan(
                    ContextCompat.getColor(
                        TheWitcherTRPGApp.getContext()!!,
                        R.color.light_red
                    )
                ), stat.length + 1, string.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        return spannable
    }
}
