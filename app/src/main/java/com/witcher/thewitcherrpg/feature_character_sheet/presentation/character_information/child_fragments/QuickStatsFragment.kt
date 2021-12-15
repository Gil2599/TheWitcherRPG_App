package com.witcher.thewitcherrpg.feature_character_sheet.presentation.character_information.child_fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.witcher.thewitcherrpg.R
import com.witcher.thewitcherrpg.core.presentation.MainCharacterViewModel
import com.witcher.thewitcherrpg.databinding.CustomDialogEditStatsBinding
import com.witcher.thewitcherrpg.databinding.FragmentCharQuickStatsBinding
import kotlinx.coroutines.launch


class QuickStatsFragment : Fragment() {
    private var _binding: FragmentCharQuickStatsBinding? = null
    private val binding get() = _binding!!

    private val mainCharacterViewModel: MainCharacterViewModel by activityViewModels()

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_char_quick_stats,container, false)
        val view = binding.root

        binding.lifecycleOwner = this
        binding.sharedViewModel = mainCharacterViewModel

        binding.buttonHP.setOnClickListener {
            showDialogHP()
        }
        binding.buttonSTA.setOnClickListener {
            showDialogSTA()
        }
        binding.buttonCROWNS.setOnClickListener {
            showDialogCrowns()
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                /*launch {
                    mainCharacterViewModel.hpModifier.collect { value ->
                        binding.buttonHP.text = "${mainCharacterViewModel.hp.value}/${mainCharacterViewModel.maxHp.value + value}"
                    }
                }
                launch {
                    mainCharacterViewModel.hp.collect { value ->
                        binding.buttonHP.text = "${value}/${mainCharacterViewModel.maxHp.value + mainCharacterViewModel.hpModifier.value}"
                    }
                }
                launch {
                    mainCharacterViewModel.staModifier.collect { value ->
                        binding.buttonHP.text = "${mainCharacterViewModel.sta.value}/${mainCharacterViewModel.maxSta.value + value}"
                    }
                }
                launch {
                    mainCharacterViewModel.sta.collect { value ->
                        binding.buttonHP.text = "${value}/${mainCharacterViewModel.sta.value + mainCharacterViewModel.staModifier.value}"
                    }
                }*/
            }
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

        bind.textView.text = mainCharacterViewModel.hp.value.toString()

        bind.editText.requestFocus()

        bind.buttonPlus.setOnClickListener(){
            val value = if (bind.editText.text.isEmpty()) 0 else bind.editText.text.toString().toInt()
            mainCharacterViewModel.onHpChange(value, true)
            dialog.dismiss()
        }

        bind.buttonMinus.setOnClickListener {
            val value = if (bind.editText.text.isEmpty()) 0 else bind.editText.text.toString().toInt()
            mainCharacterViewModel.onHpChange(value, false)
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

        bind.textView.text = mainCharacterViewModel.sta.value.toString()

        bind.editText.requestFocus()

        bind.buttonPlus.setOnClickListener{
            val value = if (bind.editText.text.isEmpty()) 0 else bind.editText.text.toString().toInt()
            mainCharacterViewModel.onStaminaChange(value, true)
            dialog.dismiss()
        }

        bind.buttonMinus.setOnClickListener{
            val value = if (bind.editText.text.isEmpty()) 0 else bind.editText.text.toString().toInt()
            mainCharacterViewModel.onStaminaChange(-kotlin.math.abs(value), false)
            dialog.dismiss()
        }
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

        bind.textView.text = mainCharacterViewModel.crowns.value.toString()

        bind.editText.requestFocus()

        bind.buttonPlus.setOnClickListener{
            val value = if (bind.editText.text.isEmpty()) 0 else bind.editText.text.toString().toInt()
            mainCharacterViewModel.onCrownsChange(value)
            dialog.dismiss()
        }

        bind.buttonMinus.setOnClickListener{
            val value = if (bind.editText.text.isEmpty()) 0 else bind.editText.text.toString().toInt()
            mainCharacterViewModel.onCrownsChange(-value)
            dialog.dismiss() }
        dialog.show()
    }
}
