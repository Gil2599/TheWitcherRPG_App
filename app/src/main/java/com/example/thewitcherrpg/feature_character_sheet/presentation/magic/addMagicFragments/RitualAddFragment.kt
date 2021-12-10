package com.example.thewitcherrpg.feature_character_sheet.presentation.magic.addMagicFragments

import android.app.Dialog
import android.graphics.Typeface
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
import com.example.thewitcherrpg.databinding.CustomDialogAddSpellBinding
import com.example.thewitcherrpg.databinding.FragmentRitualAddBinding
import com.example.thewitcherrpg.feature_character_sheet.domain.models.MagicItem
import com.example.thewitcherrpg.feature_character_sheet.presentation.magic.spellListAdapter.MagicListAdapter

class RitualAddFragment : Fragment() {
    private var _binding: FragmentRitualAddBinding? = null
    private val binding get() = _binding!!

    private val mainCharacterViewModel: MainCharacterViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRitualAddBinding.inflate(inflater, container, false)
        val view = binding.root

        //Adds a callback to back button to return to previous fragment in nav graph instead of destroying activity
        val callback = requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            // Handle the back button event
            Navigation.findNavController(view)
                .navigate(R.id.action_ritualAddFragment_to_charMagicFragment)
        }
        callback.isEnabled = true

        listAdaptersInit()

        return view
    }

    private fun listAdaptersInit() {

        //Receive information from recyclerView adapter
        val noviceAdapter = MagicListAdapter{ item ->
            showSpellDialog(item)
        }
        noviceAdapter.setData(mainCharacterViewModel.getMagicList(R.array.novice_rituals_list_data))

        val journeymanAdapter = MagicListAdapter{
            item -> showSpellDialog(item)
        }
        journeymanAdapter.setData(mainCharacterViewModel.getMagicList(R.array.journeyman_rituals_list_data))

        val masterAdapter = MagicListAdapter{
            item -> showSpellDialog(item)
        }
        masterAdapter.setData(mainCharacterViewModel.getMagicList(R.array.master_rituals_list_data))

        binding.recyclerViewNovice.adapter = noviceAdapter
        binding.recyclerViewNovice.layoutManager = LinearLayoutManager(requireContext())

        binding.recyclerViewJourneyman.adapter = journeymanAdapter
        binding.recyclerViewJourneyman.layoutManager = LinearLayoutManager(requireContext())

        binding.recyclerViewMaster.adapter = masterAdapter
        binding.recyclerViewMaster.layoutManager = LinearLayoutManager(requireContext())

        binding.recyclerViewNovice.isNestedScrollingEnabled = false
        binding.recyclerViewJourneyman.isNestedScrollingEnabled = false
        binding.recyclerViewMaster.isNestedScrollingEnabled = false

    }

    private fun showSpellDialog(item: MagicItem) {
        val dialog = Dialog(requireContext())
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val bind: CustomDialogAddSpellBinding = CustomDialogAddSpellBinding.inflate(layoutInflater)
        dialog.setContentView(bind.root)

        val staCost = "<b>" + "STA Cost: " + "</b>" + if (item.staminaCost == null) "Variable" else item.staminaCost
        val effect = "<b>" + "Effect: " + "</b>" + item.description
        val preparation = "<b>" + "Preparation Time: " + "</b>" + item.preparation
        val difficulty = "<b>" + "Difficulty: " + "</b>" + if (item.difficulty == null) "Variable" else item.difficulty
        val duration = "<b>" + "Duration: " + "</b>" + item.duration
        val components = "<b>" + "Components: " + "</b>" + item.components

        bind.customTitle.setTitle(item.name)
        bind.customTitle.setTitleSize(18F)
        bind.addStaCostText.text = HtmlCompat.fromHtml(staCost, HtmlCompat.FROM_HTML_MODE_LEGACY)
        bind.addRangeText.text = HtmlCompat.fromHtml(preparation, HtmlCompat.FROM_HTML_MODE_LEGACY)
        bind.addDefenseText.text = HtmlCompat.fromHtml(components, HtmlCompat.FROM_HTML_MODE_LEGACY)
        bind.addDefenseText.typeface = Typeface.DEFAULT
        bind.addEffectText.text = HtmlCompat.fromHtml(effect, HtmlCompat.FROM_HTML_MODE_LEGACY)
        bind.addEffectText.typeface = Typeface.DEFAULT
        bind.addDurationText.text = HtmlCompat.fromHtml(duration, HtmlCompat.FROM_HTML_MODE_LEGACY)
        bind.addSpellElementText.text =
            HtmlCompat.fromHtml(difficulty, HtmlCompat.FROM_HTML_MODE_LEGACY)

        //Check spell level to add it to correct character spell list
        bind.addSpellbutton.setOnClickListener{
            mainCharacterViewModel.addMagicItem(item)
            dialog.dismiss()
        }
        bind.addSpellCancelButton.setOnClickListener{
            dialog.dismiss()
        }
        dialog.show()
    }

}