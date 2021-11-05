package com.example.thewitcherrpg.feature_character_sheet.presentation.magic.addMagicFragments

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.text.HtmlCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thewitcherrpg.R
import com.example.thewitcherrpg.core.presentation.MainCharacterViewModel
import com.example.thewitcherrpg.databinding.CustomDialogAddHexBinding
import com.example.thewitcherrpg.databinding.FragmentHexesAddBinding
import com.example.thewitcherrpg.feature_character_sheet.presentation.magic.MagicItem
import com.example.thewitcherrpg.feature_character_sheet.presentation.magic.spellListAdapters.NoviceListAdapter

class HexesAddFragment : Fragment() {
    private var _binding: FragmentHexesAddBinding? = null
    private val binding get() = _binding!!

    //private val sharedViewModel: SharedViewModel by activityViewModels()
    private val mainCharacterViewModel: MainCharacterViewModel by activityViewModels()

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
        val hexesAdapter = NoviceListAdapter{
                hex -> showSpellDialog(hex)
        }
        hexesAdapter.setData(mainCharacterViewModel.getMagicList(R.array.hexes_list_data))

        binding.recyclerViewHexes.adapter = hexesAdapter
        binding.recyclerViewHexes.layoutManager = LinearLayoutManager(requireContext())

        binding.recyclerViewHexes.isNestedScrollingEnabled = false

    }

    private fun showSpellDialog(item: MagicItem) {
        val dialog = Dialog(requireContext())
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val bind : CustomDialogAddHexBinding = CustomDialogAddHexBinding.inflate(layoutInflater)
        dialog.setContentView(bind.root)

        val spellName = item.name
        val staCost = "<b>" + "STA Cost: " + "</b>" + if (item.staminaCost == null) "Variable" else item.staminaCost
        val effect = "<b>" + "Effect: " + "</b>" + item.description
        val danger = "<b>" + "Danger: " + "</b>" + item.danger
        val lift = "<b>" + "Requirement To Lift: " + "</b>" + item.requirementToLift

        bind.addSpellNameText.text = spellName
        bind.addStaCostText.text = HtmlCompat.fromHtml(staCost, HtmlCompat.FROM_HTML_MODE_LEGACY)
        bind.addEffectText.text = HtmlCompat.fromHtml(effect, HtmlCompat.FROM_HTML_MODE_LEGACY)
        bind.addLiftText.text = HtmlCompat.fromHtml(lift, HtmlCompat.FROM_HTML_MODE_LEGACY)
        bind.addDangerText.text = HtmlCompat.fromHtml(danger, HtmlCompat.FROM_HTML_MODE_LEGACY)

        //Check spell level to add it to correct character spell list
        bind.addSpellbutton.setOnClickListener{
            mainCharacterViewModel.addMagicItem(item)
            dialog.dismiss()
        }

        bind.addSpellCancelButton.setOnClickListener(){
            dialog.dismiss()
        }

        dialog.show()
    }
}