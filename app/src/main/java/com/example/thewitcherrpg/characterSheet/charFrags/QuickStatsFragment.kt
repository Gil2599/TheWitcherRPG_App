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
        dialog.setContentView(R.layout.custom_dialog_edit_stats)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        //dialog.textView.text = sharedViewModel.hp.value.toString()

        //dialog.editText.requestFocus()

        /*dialog.buttonPlus.setOnClickListener(){
            val value = if (dialog.editText.text.isEmpty()) 0 else dialog.editText.text.toString().toInt()
            sharedViewModel.onHealthChange(value)
            dialog.dismiss()
        }

        dialog.buttonMinus.setOnClickListener {
            val value = if (dialog.editText.text.isEmpty()) 0 else dialog.editText.text.toString().toInt()
            sharedViewModel.onHealthChange(-kotlin.math.abs(value)
            )
            dialog.dismiss() }*/
        dialog.show()
    }

    private fun showDialogSTA() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setContentView(R.layout.custom_dialog_edit_stats)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        /*dialog.textViewCurrent.text = "CURRENT STAMINA"

        dialog.textView.text = sharedViewModel.sta.value.toString()

        dialog.editText.requestFocus()

        dialog.buttonPlus.setOnClickListener(){
            val value = if (dialog.editText.text.isEmpty()) 0 else dialog.editText.text.toString().toInt()
            sharedViewModel.onStaminaChange(value)
            dialog.dismiss()
        }

        dialog.buttonMinus.setOnClickListener {
            val value = if (dialog.editText.text.isEmpty()) 0 else dialog.editText.text.toString().toInt()
            sharedViewModel.onStaminaChange(-kotlin.math.abs(value)
            )
            dialog.dismiss() }*/
        dialog.show()
    }

    private fun showDialogCrowns() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setContentView(R.layout.custom_dialog_edit_stats)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        /*dialog.textViewCurrent.text = "CURRENT CROWNS"

        dialog.textView.text = sharedViewModel.crowns.value.toString()

        dialog.editText.requestFocus()

        dialog.buttonPlus.setOnClickListener(){
            sharedViewModel.onCrownsChange(dialog.editText.text.toString().toInt())
            dialog.dismiss()
        }

        dialog.buttonMinus.setOnClickListener {
            sharedViewModel.onCrownsChange(-kotlin.math.abs(dialog.editText.text.toString().toInt())
            )
            dialog.dismiss() }*/
        dialog.show()
    }
}
