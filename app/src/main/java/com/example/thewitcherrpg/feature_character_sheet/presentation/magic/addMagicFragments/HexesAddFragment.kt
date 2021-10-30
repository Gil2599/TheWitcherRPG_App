package com.example.thewitcherrpg.feature_character_sheet.presentation.magic.addMagicFragments

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.text.HtmlCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thewitcherrpg.R
import com.example.thewitcherrpg.feature_character_sheet.SharedViewModel
import com.example.thewitcherrpg.feature_character_sheet.presentation.magic.hexexListAdapter.HexesListAdapter
import com.example.thewitcherrpg.databinding.CustomDialogAddHexBinding
import com.example.thewitcherrpg.databinding.FragmentHexesAddBinding

class HexesAddFragment : Fragment() {
    private var _binding: FragmentHexesAddBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHexesAddBinding.inflate(inflater, container, false)
        val view = binding.root

        //Adds a callback to back button to return to previous fragment in nav graph instead of destroying activity
        val callback = requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            // Handle the back button event
            Navigation.findNavController(view).navigate(R.id.action_hexesAddFragment_to_charMagicFragment)
        }
        callback.isEnabled = true

        listAdapterInit()

        return view
    }

    private fun listAdapterInit(){

        //Receive information from recyclerView adapter
        val hexesAdapter = HexesListAdapter(requireContext()){
                hex -> showSpellDialog(hex) //Determines which list this spell goes under
        }
        hexesAdapter.setData(resources.getStringArray(R.array.hexes_list_data).toList())
        hexesAdapter.setAddSpell(true) //Sets add spell state to true to show all spells


        binding.recyclerViewHexes.adapter = hexesAdapter
        binding.recyclerViewHexes.layoutManager = LinearLayoutManager(requireContext())

        binding.recyclerViewHexes.isNestedScrollingEnabled = false

    }

    private fun showSpellDialog(hex: String?) {
        val dialog = Dialog(requireContext())
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val bind : CustomDialogAddHexBinding = CustomDialogAddHexBinding.inflate(layoutInflater)
        dialog.setContentView(bind.root)

        val pair = hex!!.split(":").toTypedArray()
        val spellName = pair[0]
        val staCost = "<b>" + "STA Cost: " + "</b>" + pair[1]
        val effect = "<b>" + "Effect: " + "</b>" + pair[2]
        val danger = "<b>" + "Danger: " + "</b>" + pair[3]
        val lift = "<b>" + "Requirement To Lift: " + "</b>" + pair[4]

        bind.addSpellNameText.text = spellName
        bind.addStaCostText.text = HtmlCompat.fromHtml(staCost, HtmlCompat.FROM_HTML_MODE_LEGACY)
        bind.addEffectText.text = HtmlCompat.fromHtml(effect, HtmlCompat.FROM_HTML_MODE_LEGACY)
        bind.addLiftText.text = HtmlCompat.fromHtml(lift, HtmlCompat.FROM_HTML_MODE_LEGACY)
        bind.addDangerText.text = HtmlCompat.fromHtml(danger, HtmlCompat.FROM_HTML_MODE_LEGACY)

        //Check spell level to add it to correct character spell list
        bind.addSpellbutton.setOnClickListener(){

            if (sharedViewModel.addHex(spellName))
                Toast.makeText(context, "$spellName added to ${sharedViewModel.name.value}", Toast.LENGTH_SHORT).show()
            else Toast.makeText(context, "${sharedViewModel.name.value} already knows $spellName", Toast.LENGTH_SHORT).show()

            dialog.dismiss()
        }

        bind.addSpellCancelButton.setOnClickListener(){
            dialog.dismiss()
        }

        dialog.show()
    }
}