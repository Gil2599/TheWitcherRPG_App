package com.example.thewitcherrpg.characterSheet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thewitcherrpg.databinding.FragmentStatsBinding
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.example.thewitcherrpg.characterSheet.SharedViewModel
import com.example.thewitcherrpg.R


class StatsFragment : Fragment() {
    private var _bindind: FragmentStatsBinding? = null
    private val binding get() = _bindind!!

    private var focusedView: EditText? = null
    private var inCharCreation = false

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _bindind = DataBindingUtil.inflate(inflater, R.layout.fragment_stats, container, false)
        val view = binding.root

        binding.lifecycleOwner = this
        binding.sharedViewModel = sharedViewModel

        binding.editIP.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                binding.editIP.clearFocus()
            }
            false
        }

        statsInit()

        binding.buttonPlus.setOnClickListener{
            increaseButton()
        }

        binding.buttonMinus.setOnClickListener{
            decreaseButton()
        }

        return view
    }

    private fun statsInit(){

        binding.editIP.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) { sharedViewModel.setIP(binding.editIP.text.toString().toInt()) }}

        binding.editTextINT.setRawInputType(0)
        binding.editTextINT.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) { binding.editTextINT.background = ContextCompat.getDrawable(requireContext(), R.drawable.unfocused_outline_bkg) }
            else{ focusedView = binding.editTextINT
                binding.editTextINT.background = ContextCompat.getDrawable(requireContext(), R.drawable.outline_bkg) } }

        binding.editTextREF.setRawInputType(0)
        binding.editTextREF.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) { binding.editTextREF.background = ContextCompat.getDrawable(requireContext(), R.drawable.unfocused_outline_bkg) }
            else{ focusedView = binding.editTextREF
                binding.editTextREF.background = ContextCompat.getDrawable(requireContext(), R.drawable.outline_bkg) }}

        binding.editTextDEX.setRawInputType(0)
        binding.editTextDEX.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) { binding.editTextDEX.background = ContextCompat.getDrawable(requireContext(), R.drawable.unfocused_outline_bkg) }
            else{ focusedView = binding.editTextDEX
                binding.editTextDEX.background = ContextCompat.getDrawable(requireContext(), R.drawable.outline_bkg) }}

        binding.editTextBODY.setRawInputType(0)
        binding.editTextBODY.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) { binding.editTextBODY.background = ContextCompat.getDrawable(requireContext(), R.drawable.unfocused_outline_bkg) }
            else{ focusedView = binding.editTextBODY
                binding.editTextBODY.background = ContextCompat.getDrawable(requireContext(), R.drawable.outline_bkg) }}

        binding.editTextSPD.setRawInputType(0)
        binding.editTextSPD.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) { binding.editTextSPD.background = ContextCompat.getDrawable(requireContext(), R.drawable.unfocused_outline_bkg) }
            else{ focusedView = binding.editTextSPD
                binding.editTextSPD.background = ContextCompat.getDrawable(requireContext(), R.drawable.outline_bkg) }}

        binding.editTextEMP.setRawInputType(0)
        binding.editTextEMP.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) { binding.editTextEMP.background = ContextCompat.getDrawable(requireContext(), R.drawable.unfocused_outline_bkg) }
            else{ focusedView = binding.editTextEMP
                binding.editTextEMP.background = ContextCompat.getDrawable(requireContext(), R.drawable.outline_bkg) }}

        binding.editTextCRA.setRawInputType(0)
        binding.editTextCRA.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) { binding.editTextCRA.background = ContextCompat.getDrawable(requireContext(), R.drawable.unfocused_outline_bkg) }
            else{ focusedView = binding.editTextCRA
                binding.editTextCRA.background = ContextCompat.getDrawable(requireContext(), R.drawable.outline_bkg) }}

        binding.editTextWILL.setRawInputType(0)
        binding.editTextWILL.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) { binding.editTextWILL.background = ContextCompat.getDrawable(requireContext(), R.drawable.unfocused_outline_bkg) }
            else{ focusedView = binding.editTextWILL
                binding.editTextWILL.background = ContextCompat.getDrawable(requireContext(), R.drawable.outline_bkg) }}

        binding.editTextLUCK.setRawInputType(0)
        binding.editTextLUCK.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) { binding.editTextLUCK.background = ContextCompat.getDrawable(requireContext(), R.drawable.unfocused_outline_bkg) }
            else{ focusedView = binding.editTextLUCK
                binding.editTextLUCK.background = ContextCompat.getDrawable(requireContext(), R.drawable.outline_bkg) }}

        binding.editTextSTUN.setRawInputType(0)
        binding.editTextSTUN.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) { binding.editTextSTUN.background = ContextCompat.getDrawable(requireContext(), R.drawable.unfocused_outline_bkg) }
            else{ focusedView = binding.editTextSTUN
                binding.editTextSTUN.background = ContextCompat.getDrawable(requireContext(), R.drawable.outline_bkg) } }

        binding.editTextRUN.setRawInputType(0)
        binding.editTextRUN.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) { binding.editTextRUN.background = ContextCompat.getDrawable(requireContext(), R.drawable.unfocused_outline_bkg) }
            else{ focusedView = binding.editTextRUN
                binding.editTextRUN.background = ContextCompat.getDrawable(requireContext(), R.drawable.outline_bkg) } }

        binding.editTextLEAP.setRawInputType(0)
        binding.editTextLEAP.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) { binding.editTextLEAP.background = ContextCompat.getDrawable(requireContext(), R.drawable.unfocused_outline_bkg) }
            else{ focusedView = binding.editTextLEAP
                binding.editTextLEAP.background = ContextCompat.getDrawable(requireContext(), R.drawable.outline_bkg) } }

        binding.editTextMaxHP.setRawInputType(0)
        binding.editTextMaxHP.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) { binding.editTextMaxHP.background = ContextCompat.getDrawable(requireContext(), R.drawable.unfocused_outline_bkg) }
            else{ focusedView = binding.editTextMaxHP
                binding.editTextMaxHP.background = ContextCompat.getDrawable(requireContext(), R.drawable.outline_bkg) } }

        binding.editTextMaxSTA.setRawInputType(0)
        binding.editTextMaxSTA.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) { binding.editTextMaxSTA.background = ContextCompat.getDrawable(requireContext(), R.drawable.unfocused_outline_bkg) }
            else{ focusedView = binding.editTextMaxSTA
                binding.editTextMaxSTA.background = ContextCompat.getDrawable(requireContext(), R.drawable.outline_bkg) } }

        binding.editTextENC.setRawInputType(0)
        binding.editTextENC.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) { binding.editTextENC.background = ContextCompat.getDrawable(requireContext(), R.drawable.unfocused_outline_bkg) }
            else{ focusedView = binding.editTextENC
                binding.editTextENC.background = ContextCompat.getDrawable(requireContext(), R.drawable.outline_bkg) } }

        binding.editTextREC.setRawInputType(0)
        binding.editTextREC.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) { binding.editTextREC.background = ContextCompat.getDrawable(requireContext(), R.drawable.unfocused_outline_bkg) }
            else{ focusedView = binding.editTextREC
                binding.editTextREC.background = ContextCompat.getDrawable(requireContext(), R.drawable.outline_bkg) } }

        binding.editTextPunch.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) { binding.editTextPunch.background = ContextCompat.getDrawable(requireContext(), R.drawable.unfocused_outline_bkg) }
            else{ focusedView = binding.editTextPunch
                binding.editTextPunch.background = ContextCompat.getDrawable(requireContext(), R.drawable.outline_bkg) } }

        binding.editTextKick.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) { binding.editTextKick.background = ContextCompat.getDrawable(requireContext(), R.drawable.unfocused_outline_bkg) }
            else{ focusedView = binding.editTextKick
                binding.editTextKick.background = ContextCompat.getDrawable(requireContext(), R.drawable.outline_bkg) } }

        if(inCharCreation){
            binding.editTextSTUN.focusable = View.NOT_FOCUSABLE
            binding.editTextRUN.focusable = View.NOT_FOCUSABLE
            binding.editTextLEAP.focusable = View.NOT_FOCUSABLE
            binding.editTextMaxHP.focusable = View.NOT_FOCUSABLE
            binding.editTextMaxSTA.focusable = View.NOT_FOCUSABLE
            binding.editTextENC.focusable = View.NOT_FOCUSABLE
            binding.editTextREC.focusable = View.NOT_FOCUSABLE
            binding.editTextPunch.focusable = View.NOT_FOCUSABLE
            binding.editTextKick.focusable = View.NOT_FOCUSABLE
        }
    }

    private fun increaseButton() {

        if (focusedView != null) {

            when (focusedView){
                binding.editTextINT -> sharedViewModel.increaseStat(1)
                binding.editTextREF -> sharedViewModel.increaseStat(2)
                binding.editTextDEX -> sharedViewModel.increaseStat(3)
                binding.editTextBODY -> sharedViewModel.increaseStat(4)
                binding.editTextSPD -> sharedViewModel.increaseStat(5)
                binding.editTextEMP -> sharedViewModel.increaseStat(6)
                binding.editTextCRA -> sharedViewModel.increaseStat(7)
                binding.editTextWILL -> sharedViewModel.increaseStat(8)
                binding.editTextLUCK -> sharedViewModel.increaseStat(9)
                binding.editTextSTUN -> sharedViewModel.increaseStat(10)
                binding.editTextRUN -> sharedViewModel.increaseStat(11)
                binding.editTextLEAP -> sharedViewModel.increaseStat(12)
                binding.editTextMaxHP -> sharedViewModel.increaseStat(13)
                binding.editTextMaxSTA -> sharedViewModel.increaseStat(14)
                binding.editTextENC -> sharedViewModel.increaseStat(15)
                binding.editTextREC -> sharedViewModel.increaseStat(16)

            }
        }
    }

    private fun decreaseButton(){

        if (focusedView != null) {

            when (focusedView){
                binding.editTextINT -> sharedViewModel.decreaseStat(1)
                binding.editTextREF -> sharedViewModel.decreaseStat(2)
                binding.editTextDEX -> sharedViewModel.decreaseStat(3)
                binding.editTextBODY -> sharedViewModel.decreaseStat(4)
                binding.editTextSPD -> sharedViewModel.decreaseStat(5)
                binding.editTextEMP -> sharedViewModel.decreaseStat(6)
                binding.editTextCRA -> sharedViewModel.decreaseStat(7)
                binding.editTextWILL -> sharedViewModel.decreaseStat(8)
                binding.editTextLUCK -> sharedViewModel.decreaseStat(9)
                binding.editTextSTUN -> sharedViewModel.decreaseStat(10)
                binding.editTextRUN -> sharedViewModel.decreaseStat(11)
                binding.editTextLEAP -> sharedViewModel.decreaseStat(12)
                binding.editTextMaxHP -> sharedViewModel.decreaseStat(13)
                binding.editTextMaxSTA -> sharedViewModel.decreaseStat(14)
                binding.editTextENC -> sharedViewModel.decreaseStat(15)
                binding.editTextREC -> sharedViewModel.decreaseStat(16)

            }
        }
    }

    fun inCharCreation(){
        inCharCreation = true
    }

    override fun onPause(){
        super.onPause()
        focusedView = null
    }

}