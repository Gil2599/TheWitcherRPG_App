package com.example.thewitcherrpg.characterSheet.charFrags

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.example.thewitcherrpg.R
import com.example.thewitcherrpg.databinding.FragmentCharInfoBinding
import com.example.thewitcherrpg.characterSheet.SharedViewModel
import com.example.thewitcherrpg.databinding.CustomDialogCharSpellBinding
import com.example.thewitcherrpg.databinding.CustomDialogEditStatsBinding


class QuickStatsFragment : Fragment() {
    private var _binding: FragmentCharInfoBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_char_info,container, false)
        val view = binding.root

        binding.lifecycleOwner = this
        binding.sharedViewModel = sharedViewModel

        binding.buttonHP.setOnClickListener(){
            showDialogHP()
        }
        binding.buttonSTA.setOnClickListener(){
            showDialogSTA()
        }
        binding.buttonCROWNS.setOnClickListener(){
            showDialogCrowns()
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }

    private fun showDialogHP() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val bind : CustomDialogEditStatsBinding = CustomDialogEditStatsBinding.inflate(layoutInflater)
        dialog.setContentView(bind.root)

        bind.textView.text = sharedViewModel.hp.value.toString()

        bind.editText.requestFocus()

        bind.buttonPlus.setOnClickListener(){
            val value = if (bind.editText.text.isEmpty()) 0 else bind.editText.text.toString().toInt()
            sharedViewModel.onHealthChange(value)
            dialog.dismiss()
        }

        bind.buttonMinus.setOnClickListener {
            val value = if (bind.editText.text.isEmpty()) 0 else bind.editText.text.toString().toInt()
            sharedViewModel.onHealthChange(-kotlin.math.abs(value)
            )
            dialog.dismiss() }
        dialog.show()
    }

    private fun showDialogSTA() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val bind : CustomDialogEditStatsBinding = CustomDialogEditStatsBinding.inflate(layoutInflater)
        dialog.setContentView(bind.root)

        bind.textViewCurrent.text = "CURRENT STAMINA"

        bind.textView.text = sharedViewModel.sta.value.toString()

        bind.editText.requestFocus()

        bind.buttonPlus.setOnClickListener(){
            val value = if (bind.editText.text.isEmpty()) 0 else bind.editText.text.toString().toInt()
            sharedViewModel.onStaminaChange(value)
            dialog.dismiss()
        }

        bind.buttonMinus.setOnClickListener {
            val value = if (bind.editText.text.isEmpty()) 0 else bind.editText.text.toString().toInt()
            sharedViewModel.onStaminaChange(-kotlin.math.abs(value)
            )
            dialog.dismiss() }
        dialog.show()
    }

    private fun showDialogCrowns() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val bind : CustomDialogEditStatsBinding = CustomDialogEditStatsBinding.inflate(layoutInflater)
        dialog.setContentView(bind.root)

        bind.textViewCurrent.text = "CURRENT CROWNS"

        bind.textView.text = sharedViewModel.crowns.value.toString()

        bind.editText.requestFocus()

        bind.buttonPlus.setOnClickListener(){
            sharedViewModel.onCrownsChange(bind.editText.text.toString().toInt())
            dialog.dismiss()
        }

        bind.buttonMinus.setOnClickListener {
            sharedViewModel.onCrownsChange(-kotlin.math.abs(bind.editText.text.toString().toInt())
            )
            dialog.dismiss() }
        dialog.show()
    }
}
