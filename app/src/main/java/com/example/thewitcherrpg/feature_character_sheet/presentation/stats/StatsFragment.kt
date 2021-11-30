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
import kotlinx.coroutines.withContext


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

        lifecycleScope.launch {
            setObservers()
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
        if (!mainCharacterViewModel.inCharacterCreation.value) {
            binding.editTextINT.setOnLongClickListener {
                binding.editTextINT.requestFocus()
                binding.editTextINT.setText("INT: ${mainCharacterViewModel.intelligence.value}" + " [${mainCharacterViewModel.intelligenceModifier.value}]")
                editModifier = true
                true
            }
        }

        binding.editTextREF.setRawInputType(0)
        binding.editTextREF.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                binding.editTextREF.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.unfocused_outline_bkg)
                editModifier = false
                binding.editTextREF.text = getSpannableString(
                    "REF: ${
                        mainCharacterViewModel.ref.value.plus(
                            mainCharacterViewModel.refModifier.value
                        )
                    }", editModifier, mainCharacterViewModel.ref.value
                )
            } else {
                focusedView = binding.editTextREF
                binding.editTextREF.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.outline_bkg)
            }
        }
        if (!mainCharacterViewModel.inCharacterCreation.value) {
            binding.editTextREF.setOnLongClickListener {
                binding.editTextREF.requestFocus()
                binding.editTextREF.setText("REF: ${mainCharacterViewModel.ref.value}" + " [${mainCharacterViewModel.refModifier.value}]")
                editModifier = true
                true
            }
        }

        binding.editTextDEX.setRawInputType(0)
        binding.editTextDEX.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                binding.editTextDEX.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.unfocused_outline_bkg)
                editModifier = false
                binding.editTextDEX.text = getSpannableString(
                    "DEX: ${
                        mainCharacterViewModel.dex.value.plus(
                            mainCharacterViewModel.dexModifier.value
                        )
                    }", editModifier, mainCharacterViewModel.dex.value
                )
            } else {
                focusedView = binding.editTextDEX
                binding.editTextDEX.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.outline_bkg)
            }
        }
        if (!mainCharacterViewModel.inCharacterCreation.value) {
            binding.editTextDEX.setOnLongClickListener {
                binding.editTextDEX.requestFocus()
                binding.editTextDEX.setText("DEX: ${mainCharacterViewModel.dex.value}" + " [${mainCharacterViewModel.dexModifier.value}]")
                editModifier = true
                true
            }
        }

        binding.editTextBODY.setRawInputType(0)
        binding.editTextBODY.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                binding.editTextBODY.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.unfocused_outline_bkg)
                editModifier = false
                binding.editTextBODY.text = getSpannableString(
                    "BODY: ${
                        mainCharacterViewModel.body.value.plus(
                            mainCharacterViewModel.bodyModifier.value
                        )
                    }", editModifier, mainCharacterViewModel.body.value
                )
            } else {
                focusedView = binding.editTextBODY
                binding.editTextBODY.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.outline_bkg)
            }
        }
        if (!mainCharacterViewModel.inCharacterCreation.value) {
            binding.editTextBODY.setOnLongClickListener {
                binding.editTextBODY.requestFocus()
                binding.editTextBODY.setText("BODY: ${mainCharacterViewModel.body.value}" + " [${mainCharacterViewModel.bodyModifier.value}]")
                editModifier = true
                true
            }
        }

        binding.editTextSPD.setRawInputType(0)
        binding.editTextSPD.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                binding.editTextSPD.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.unfocused_outline_bkg)
                editModifier = false
                binding.editTextSPD.text = getSpannableString(
                    "SPD: ${
                        mainCharacterViewModel.spd.value.plus(
                            mainCharacterViewModel.spdModifier.value
                        )
                    }", editModifier, mainCharacterViewModel.spd.value
                )
            } else {
                focusedView = binding.editTextSPD
                binding.editTextSPD.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.outline_bkg)
            }
        }
        if (!mainCharacterViewModel.inCharacterCreation.value) {
            binding.editTextSPD.setOnLongClickListener {
                binding.editTextSPD.requestFocus()
                binding.editTextSPD.setText("SPD: ${mainCharacterViewModel.spd.value}" + " [${mainCharacterViewModel.spdModifier.value}]")
                editModifier = true
                true
            }
        }

        binding.editTextEMP.setRawInputType(0)
        binding.editTextEMP.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                binding.editTextEMP.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.unfocused_outline_bkg)
                editModifier = false
                binding.editTextEMP.text = getSpannableString(
                    "EMP: ${
                        mainCharacterViewModel.emp.value.plus(
                            mainCharacterViewModel.empModifier.value
                        )
                    }", editModifier, mainCharacterViewModel.emp.value
                )
            } else {
                focusedView = binding.editTextEMP
                binding.editTextEMP.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.outline_bkg)
            }
        }
        if (!mainCharacterViewModel.inCharacterCreation.value) {
            binding.editTextEMP.setOnLongClickListener {
                binding.editTextEMP.requestFocus()
                binding.editTextEMP.setText("EMP: ${mainCharacterViewModel.emp.value}" + " [${mainCharacterViewModel.empModifier.value}]")
                editModifier = true
                true
            }
        }

        binding.editTextCRA.setRawInputType(0)
        binding.editTextCRA.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                binding.editTextCRA.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.unfocused_outline_bkg)
                editModifier = false
                binding.editTextCRA.text = getSpannableString(
                    "CRA: ${
                        mainCharacterViewModel.cra.value.plus(
                            mainCharacterViewModel.craModifier.value
                        )
                    }", editModifier, mainCharacterViewModel.cra.value
                )
            } else {
                focusedView = binding.editTextCRA
                binding.editTextCRA.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.outline_bkg)
            }
        }
        if (!mainCharacterViewModel.inCharacterCreation.value) {
            binding.editTextCRA.setOnLongClickListener {
                binding.editTextCRA.requestFocus()
                binding.editTextCRA.setText("CRA: ${mainCharacterViewModel.cra.value}" + " [${mainCharacterViewModel.craModifier.value}]")
                editModifier = true
                true
            }
        }

        binding.editTextWILL.setRawInputType(0)
        binding.editTextWILL.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                binding.editTextWILL.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.unfocused_outline_bkg)
                editModifier = false
                binding.editTextWILL.text = getSpannableString(
                    "WILL: ${
                        mainCharacterViewModel.will.value.plus(
                            mainCharacterViewModel.willModifier.value
                        )
                    }", editModifier, mainCharacterViewModel.will.value
                )
            } else {
                focusedView = binding.editTextWILL
                binding.editTextWILL.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.outline_bkg)
            }
        }
        if (!mainCharacterViewModel.inCharacterCreation.value) {
            binding.editTextWILL.setOnLongClickListener {
                binding.editTextWILL.requestFocus()
                binding.editTextWILL.setText("WILL: ${mainCharacterViewModel.will.value}" + " [${mainCharacterViewModel.willModifier.value}]")
                editModifier = true
                true
            }
        }

        binding.editTextLUCK.setRawInputType(0)
        binding.editTextLUCK.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                binding.editTextLUCK.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.unfocused_outline_bkg)
                editModifier = false
                binding.editTextLUCK.text = getSpannableString(
                    "LUCK: ${
                        mainCharacterViewModel.luck.value.plus(
                            mainCharacterViewModel.luckModifier.value
                        )
                    }", editModifier, mainCharacterViewModel.luck.value
                )
            } else {
                focusedView = binding.editTextLUCK
                binding.editTextLUCK.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.outline_bkg)
            }
        }
        if (!mainCharacterViewModel.inCharacterCreation.value) {
            binding.editTextLUCK.setOnLongClickListener {
                binding.editTextLUCK.requestFocus()
                binding.editTextLUCK.setText("LUCK: ${mainCharacterViewModel.luck.value}" + " [${mainCharacterViewModel.luckModifier.value}]")
                editModifier = true
                true
            }
        }

        binding.editTextSTUN.setRawInputType(0)
        binding.editTextSTUN.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                binding.editTextSTUN.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.unfocused_outline_bkg)
                editModifier = false
                binding.editTextSTUN.text = getSpannableString(
                    "STUN: ${
                        mainCharacterViewModel.stun.value.plus(
                            mainCharacterViewModel.stunModifier.value
                        )
                    }", editModifier, mainCharacterViewModel.stun.value
                )
            } else {
                focusedView = binding.editTextSTUN
                binding.editTextSTUN.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.outline_bkg)
            }
        }
        if (!mainCharacterViewModel.inCharacterCreation.value) {
            binding.editTextSTUN.setOnLongClickListener {
                binding.editTextSTUN.requestFocus()
                binding.editTextSTUN.setText("STUN: ${mainCharacterViewModel.stun.value}" + " [${mainCharacterViewModel.stunModifier.value}]")
                editModifier = true
                true
            }
        }

        binding.editTextRUN.setRawInputType(0)
        binding.editTextRUN.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                binding.editTextRUN.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.unfocused_outline_bkg)
                editModifier = false
                binding.editTextRUN.text = getSpannableString(
                    "RUN: ${
                        mainCharacterViewModel.run.value.plus(
                            mainCharacterViewModel.runModifier.value
                        )
                    }", editModifier, mainCharacterViewModel.run.value
                )
            } else {
                focusedView = binding.editTextRUN
                binding.editTextRUN.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.outline_bkg)
            }
        }
        if (!mainCharacterViewModel.inCharacterCreation.value) {
            binding.editTextRUN.setOnLongClickListener {
                binding.editTextRUN.requestFocus()
                binding.editTextRUN.setText("RUN: ${mainCharacterViewModel.run.value}" + " [${mainCharacterViewModel.runModifier.value}]")
                editModifier = true
                true
            }
        }

        binding.editTextLEAP.setRawInputType(0)
        binding.editTextLEAP.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                binding.editTextLEAP.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.unfocused_outline_bkg)
                editModifier = false
                binding.editTextLEAP.text = getSpannableString(
                    "LEAP: ${
                        mainCharacterViewModel.leap.value.plus(
                            mainCharacterViewModel.leapModifier.value
                        )
                    }", editModifier, mainCharacterViewModel.leap.value
                )
            } else {
                focusedView = binding.editTextLEAP
                binding.editTextLEAP.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.outline_bkg)
            }
        }
        if (!mainCharacterViewModel.inCharacterCreation.value) {
            binding.editTextLEAP.setOnLongClickListener {
                binding.editTextLEAP.requestFocus()
                binding.editTextLEAP.setText("LEAP: ${mainCharacterViewModel.leap.value}" + " [${mainCharacterViewModel.leapModifier.value}]")
                editModifier = true
                true
            }
        }

        binding.editTextMaxHP.setRawInputType(0)
        binding.editTextMaxHP.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                binding.editTextMaxHP.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.unfocused_outline_bkg)
                editModifier = false
                binding.editTextMaxHP.text = getSpannableString(
                    "MAX HP: ${
                        mainCharacterViewModel.maxHp.value.plus(
                            mainCharacterViewModel.hpModifier.value
                        )
                    }", editModifier, mainCharacterViewModel.maxHp.value
                )
            } else {
                focusedView = binding.editTextMaxHP
                binding.editTextMaxHP.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.outline_bkg)
            }
        }
        if (!mainCharacterViewModel.inCharacterCreation.value) {
            binding.editTextMaxHP.setOnLongClickListener {
                binding.editTextMaxHP.requestFocus()
                binding.editTextMaxHP.setText("MAX HP: ${mainCharacterViewModel.maxHp.value}" + " [${mainCharacterViewModel.hpModifier.value}]")
                editModifier = true
                true
            }
        }

        binding.editTextMaxSTA.setRawInputType(0)
        binding.editTextMaxSTA.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                binding.editTextMaxSTA.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.unfocused_outline_bkg)
                editModifier = false
                binding.editTextMaxSTA.text = getSpannableString(
                    "MAX STA: ${
                        mainCharacterViewModel.maxSta.value.plus(
                            mainCharacterViewModel.staModifier.value
                        )
                    }", editModifier, mainCharacterViewModel.maxSta.value
                )
            } else {
                focusedView = binding.editTextMaxSTA
                binding.editTextMaxSTA.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.outline_bkg)
            }
        }
        if (!mainCharacterViewModel.inCharacterCreation.value) {
            binding.editTextMaxSTA.setOnLongClickListener {
                binding.editTextMaxSTA.requestFocus()
                binding.editTextMaxSTA.setText("MAX STA: ${mainCharacterViewModel.maxSta.value}" + " [${mainCharacterViewModel.staModifier.value}]")
                editModifier = true
                true
            }
        }

        binding.editTextENC.setRawInputType(0)
        binding.editTextENC.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                binding.editTextENC.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.unfocused_outline_bkg)
                editModifier = false
                binding.editTextENC.text = getSpannableString(
                    "ENC: ${
                        mainCharacterViewModel.enc.value.plus(
                            mainCharacterViewModel.encModifier.value
                        )
                    }", editModifier, mainCharacterViewModel.enc.value
                )
            } else {
                focusedView = binding.editTextENC
                binding.editTextENC.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.outline_bkg)
            }
        }
        if (!mainCharacterViewModel.inCharacterCreation.value) {
            binding.editTextENC.setOnLongClickListener {
                binding.editTextENC.requestFocus()
                binding.editTextENC.setText("ENC: ${mainCharacterViewModel.enc.value}" + " [${mainCharacterViewModel.encModifier.value}]")
                editModifier = true
                true
            }
        }

        binding.editTextREC.setRawInputType(0)
        binding.editTextREC.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                binding.editTextREC.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.unfocused_outline_bkg)
                editModifier = false
                binding.editTextREC.text = getSpannableString(
                    "REC: ${
                        mainCharacterViewModel.rec.value.plus(
                            mainCharacterViewModel.recModifier.value
                        )
                    }", editModifier, mainCharacterViewModel.rec.value
                )
            } else {
                focusedView = binding.editTextREC
                binding.editTextREC.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.outline_bkg)
            }
        }
        if (!mainCharacterViewModel.inCharacterCreation.value) {
            binding.editTextREC.setOnLongClickListener {
                binding.editTextREC.requestFocus()
                binding.editTextREC.setText("REC: ${mainCharacterViewModel.rec.value}" + " [${mainCharacterViewModel.recModifier.value}]")
                editModifier = true
                true
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
    private suspend fun setObservers() = withContext(Dispatchers.Main) {
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
            launch {
                mainCharacterViewModel.refModifier.collect { value ->
                    if (editModifier) binding.editTextREF.setText("REF: ${mainCharacterViewModel.ref.value}" + " [$value]")
                }
            }
            launch {
                mainCharacterViewModel.ref.collect { value ->
                    binding.editTextREF.text = getSpannableString(
                        "REF: ${
                            value.plus(
                                mainCharacterViewModel.refModifier.value
                            )
                        }", editModifier, value
                    )
                }
            }
            launch {
                mainCharacterViewModel.dexModifier.collect { value ->
                    if (editModifier) binding.editTextDEX.setText("DEX: ${mainCharacterViewModel.dex.value}" + " [$value]")
                }
            }
            launch {
                mainCharacterViewModel.dex.collect { value ->
                    binding.editTextDEX.text = getSpannableString(
                        "DEX: ${
                            value.plus(
                                mainCharacterViewModel.dexModifier.value
                            )
                        }", editModifier, value
                    )
                }
            }
            launch {
                mainCharacterViewModel.bodyModifier.collect { value ->
                    if (editModifier) binding.editTextBODY.setText("BODY: ${mainCharacterViewModel.body.value}" + " [$value]")
                }
            }
            launch {
                mainCharacterViewModel.body.collect { value ->
                    binding.editTextBODY.text = getSpannableString(
                        "BODY: ${
                            value.plus(
                                mainCharacterViewModel.bodyModifier.value
                            )
                        }", editModifier, value
                    )
                }
            }
            launch {
                mainCharacterViewModel.spdModifier.collect { value ->
                    if (editModifier) binding.editTextSPD.setText("SPD: ${mainCharacterViewModel.spd.value}" + " [$value]")
                }
            }
            launch {
                mainCharacterViewModel.spd.collect { value ->
                    binding.editTextSPD.text = getSpannableString(
                        "SPD: ${
                            value.plus(
                                mainCharacterViewModel.spdModifier.value
                            )
                        }", editModifier, value
                    )
                }
            }
            launch {
                mainCharacterViewModel.empModifier.collect { value ->
                    if (editModifier) binding.editTextEMP.setText("EMP: ${mainCharacterViewModel.emp.value}" + " [$value]")
                }
            }
            launch {
                mainCharacterViewModel.emp.collect { value ->
                    binding.editTextEMP.text = getSpannableString(
                        "EMP: ${
                            value.plus(
                                mainCharacterViewModel.empModifier.value
                            )
                        }", editModifier, value
                    )
                }
            }
            launch {
                mainCharacterViewModel.craModifier.collect { value ->
                    if (editModifier) binding.editTextCRA.setText("CRA: ${mainCharacterViewModel.cra.value}" + " [$value]")
                }
            }
            launch {
                mainCharacterViewModel.cra.collect { value ->
                    binding.editTextCRA.text = getSpannableString(
                        "CRA: ${
                            value.plus(
                                mainCharacterViewModel.craModifier.value
                            )
                        }", editModifier, value
                    )
                }
            }
            launch {
                mainCharacterViewModel.willModifier.collect { value ->
                    if (editModifier) binding.editTextWILL.setText("WILL: ${mainCharacterViewModel.will.value}" + " [$value]")
                }
            }
            launch {
                mainCharacterViewModel.will.collect { value ->
                    binding.editTextWILL.text = getSpannableString(
                        "WILL: ${
                            value.plus(
                                mainCharacterViewModel.willModifier.value
                            )
                        }", editModifier, value
                    )
                }
            }
            launch {
                mainCharacterViewModel.luckModifier.collect { value ->
                    if (editModifier) binding.editTextLUCK.setText("LUCK: ${mainCharacterViewModel.luck.value}" + " [$value]")
                }
            }
            launch {
                mainCharacterViewModel.luck.collect { value ->
                    binding.editTextLUCK.text = getSpannableString(
                        "LUCK: ${
                            value.plus(
                                mainCharacterViewModel.luckModifier.value
                            )
                        }", editModifier, value
                    )
                }
            }
            launch {
                mainCharacterViewModel.stunModifier.collect { value ->
                    if (editModifier) binding.editTextSTUN.setText("STUN: ${mainCharacterViewModel.stun.value}" + " [$value]")
                }
            }
            launch {
                mainCharacterViewModel.stun.collect { value ->
                    binding.editTextSTUN.text = getSpannableString(
                        "STUN: ${
                            value.plus(
                                mainCharacterViewModel.stunModifier.value
                            )
                        }", editModifier, value
                    )
                }
            }
            launch {
                mainCharacterViewModel.runModifier.collect { value ->
                    if (editModifier) binding.editTextRUN.setText("RUN: ${mainCharacterViewModel.run.value}" + " [$value]")
                }
            }
            launch {
                mainCharacterViewModel.run.collect { value ->
                    binding.editTextRUN.text = getSpannableString(
                        "RUN: ${
                            value.plus(
                                mainCharacterViewModel.runModifier.value
                            )
                        }", editModifier, value
                    )
                }
            }
            launch {
                mainCharacterViewModel.leapModifier.collect { value ->
                    if (editModifier) binding.editTextLEAP.setText("LEAP: ${mainCharacterViewModel.leap.value}" + " [$value]")
                }
            }
            launch {
                mainCharacterViewModel.leap.collect { value ->
                    binding.editTextLEAP.text = getSpannableString(
                        "LEAP: ${
                            value.plus(
                                mainCharacterViewModel.leapModifier.value
                            )
                        }", editModifier, value
                    )
                }
            }
            launch {
                mainCharacterViewModel.hpModifier.collect { value ->
                    if (editModifier) binding.editTextMaxHP.setText("MAX HP: ${mainCharacterViewModel.maxHp.value}" + " [$value]")
                }
            }
            launch {
                mainCharacterViewModel.maxHp.collect { value ->
                    binding.editTextMaxHP.text = getSpannableString(
                        "MAX HP: ${
                            value.plus(
                                mainCharacterViewModel.hpModifier.value
                            )
                        }", editModifier, value
                    )
                }
            }
            launch {
                mainCharacterViewModel.staModifier.collect { value ->
                    if (editModifier) binding.editTextMaxSTA.setText("MAX STA: ${mainCharacterViewModel.maxSta.value}" + " [$value]")
                }
            }
            launch {
                mainCharacterViewModel.maxSta.collect { value ->
                    binding.editTextMaxSTA.text = getSpannableString(
                        "MAX STA: ${
                            value.plus(
                                mainCharacterViewModel.staModifier.value
                            )
                        }", editModifier, value
                    )
                }
            }
            launch {
                mainCharacterViewModel.encModifier.collect { value ->
                    if (editModifier) binding.editTextENC.setText("ENC: ${mainCharacterViewModel.enc.value}" + " [$value]")
                }
            }
            launch {
                mainCharacterViewModel.enc.collect { value ->
                    binding.editTextENC.text = getSpannableString(
                        "ENC: ${
                            value.plus(
                                mainCharacterViewModel.encModifier.value
                            )
                        }", editModifier, value
                    )
                }
            }
            launch {
                mainCharacterViewModel.recModifier.collect { value ->
                    if (editModifier) binding.editTextREC.setText("REC: ${mainCharacterViewModel.rec.value}" + " [$value]")
                }
            }
            launch {
                mainCharacterViewModel.rec.collect { value ->
                    binding.editTextREC.text = getSpannableString(
                        "REC: ${
                            value.plus(
                                mainCharacterViewModel.recModifier.value
                            )
                        }", editModifier, value
                    )
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
