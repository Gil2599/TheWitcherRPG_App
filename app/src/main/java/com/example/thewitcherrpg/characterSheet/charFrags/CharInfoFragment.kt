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
import kotlinx.android.synthetic.main.custom_dialog_hp.*



class CharInfoFragment : Fragment() {
    private var _bindind: FragmentCharInfoBinding? = null
    private val binding get() = _bindind!!

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _bindind = DataBindingUtil.inflate(inflater, R.layout.fragment_char_info,container, false)
        val view = binding.root

        binding.lifecycleOwner = this
        binding.sharedViewModel = sharedViewModel

        binding.buttonHP.setOnClickListener(){
            /*val dialog = CustomDialogClass(requireContext())
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()*/
            showDialogHP()
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bindind = null

    }

    private fun showDialogHP() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setContentView(R.layout.custom_dialog_hp)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        dialog.textViewHP.text = sharedViewModel.hp.value.toString()

        dialog.buttonHeal.setOnClickListener(){
            sharedViewModel.onHealthChange(dialog.editTextHP.text.toString().toInt())
            dialog.dismiss()
        }

        dialog.buttonDamage.setOnClickListener {
            sharedViewModel.onHealthChange(-kotlin.math.abs(dialog.editTextHP.text.toString().toInt()
            )
            )
            dialog.dismiss() }
        dialog.show()

    }

}
