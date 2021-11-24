package com.example.thewitcherrpg.feature_character_sheet.presentation.magic.addMagicFragments

import android.app.Dialog
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thewitcherrpg.R
import com.example.thewitcherrpg.core.presentation.MainCharacterViewModel
import com.example.thewitcherrpg.databinding.CustomDialogAddSpellBinding
import com.example.thewitcherrpg.databinding.FragmentInvocationAddBinding
import com.example.thewitcherrpg.feature_character_sheet.domain.models.MagicItem
import com.example.thewitcherrpg.feature_character_sheet.presentation.magic.spellListAdapter.MagicListAdapter

class InvocationAddFragment : Fragment() {
    private var _binding: FragmentInvocationAddBinding? = null
    private val binding get() = _binding!!

    private val mainCharacterViewModel: MainCharacterViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInvocationAddBinding.inflate(inflater, container, false)
        val view = binding.root

        val callback = requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            // Handle the back button event
            Navigation.findNavController(view).navigate(R.id.action_invocationAddFragment_to_charMagicFragment)
        }
        callback.isEnabled = true

        //Initialize recyclerView adapters
        druidListAdapters()
        preacherListAdapters()
        archPriestAdapter()

        return view
    }

    private fun druidListAdapters(){

        //Receive information from recyclerView adapter
        val noviceDruidAdapter = MagicListAdapter {
                invocation -> showInvocationDialog(invocation)
        }
        noviceDruidAdapter.setData(mainCharacterViewModel.getMagicList(R.array.novice_druidInvo_list_data))

        val journeymanDruidAdapter = MagicListAdapter {
                invocation -> showInvocationDialog(invocation)
        }
        journeymanDruidAdapter.setData(mainCharacterViewModel.getMagicList(R.array.journeyman_druidInvo_list_data))

        val masterAdapterDruid = MagicListAdapter {
                invocation -> showInvocationDialog(invocation)
        }
        masterAdapterDruid.setData(mainCharacterViewModel.getMagicList(R.array.master_druidInvo_data))

        binding.recyclerViewNoviceDruid.adapter = noviceDruidAdapter
        binding.recyclerViewNoviceDruid.layoutManager = LinearLayoutManager(requireContext())

        binding.recyclerViewJourneymanDruid.adapter = journeymanDruidAdapter
        binding.recyclerViewJourneymanDruid.layoutManager = LinearLayoutManager(requireContext())

        binding.recyclerViewMasterDruid.adapter = masterAdapterDruid
        binding.recyclerViewMasterDruid.layoutManager = LinearLayoutManager(requireContext())

        binding.recyclerViewNoviceDruid.isNestedScrollingEnabled = false
        binding.recyclerViewJourneymanDruid.isNestedScrollingEnabled = false
        binding.recyclerViewMasterDruid.isNestedScrollingEnabled = false
    }

    private fun preacherListAdapters(){

        //Receive information from recyclerView adapter
        val novicePreacherAdapter = MagicListAdapter {
                invocation -> showInvocationDialog(invocation)
        }
        novicePreacherAdapter.setData(mainCharacterViewModel.getMagicList(R.array.novice_preacherInvo_list_data))

        val journeymanPreacherAdapter = MagicListAdapter {
                invocation -> showInvocationDialog(invocation)
        }
        journeymanPreacherAdapter.setData(mainCharacterViewModel.getMagicList(R.array.journeyman_preacherInvo_list_data))

        val masterPreacherAdapter = MagicListAdapter {
                invocation -> showInvocationDialog(invocation)
        }
        masterPreacherAdapter.setData(mainCharacterViewModel.getMagicList(R.array.master_preacherInvo_list_data))

        binding.recyclerViewNovicePreacher.adapter = novicePreacherAdapter
        binding.recyclerViewNovicePreacher.layoutManager = LinearLayoutManager(requireContext())

        binding.recyclerViewJourneymanPreacher.adapter = journeymanPreacherAdapter
        binding.recyclerViewJourneymanPreacher.layoutManager = LinearLayoutManager(requireContext())

        binding.recyclerViewMasterPreacher.adapter = masterPreacherAdapter
        binding.recyclerViewMasterPreacher.layoutManager = LinearLayoutManager(requireContext())

        binding.recyclerViewNovicePreacher.isNestedScrollingEnabled = false
        binding.recyclerViewJourneymanPreacher.isNestedScrollingEnabled = false
        binding.recyclerViewMasterPreacher.isNestedScrollingEnabled = false
    }

    private fun archPriestAdapter(){
        //Receive information from recyclerView adapter
        val archPriestAdapter = MagicListAdapter {
                invocation -> showInvocationDialog(invocation)
        }
        archPriestAdapter.setData(mainCharacterViewModel.getMagicList(R.array.archPriestInvo_list_data))

        binding.recyclerViewArchPriest.adapter = archPriestAdapter
        binding.recyclerViewArchPriest.layoutManager = LinearLayoutManager(requireContext())

        binding.recyclerViewArchPriest.isNestedScrollingEnabled = false
    }


    private fun showInvocationDialog(item: MagicItem) {
        val dialog = Dialog(requireContext())
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val bind : CustomDialogAddSpellBinding = CustomDialogAddSpellBinding.inflate(layoutInflater)
        dialog.setContentView(bind.root)

        val staCost = "<b>" + "STA Cost: " + "</b>" + item.staminaCost
        val effect = "<b>" + "Effect: " + "</b>" + item.description
        val range = "<b>" + "Range: " + "</b>" + item.range
        val duration = "<b>" + "Duration: " + "</b>" + item.duration
        val defense = "<b>" + "Defense: " + "</b>" + item.defense

        bind.customTitle.setTitle(item.name)
        bind.customTitle.setTitleSize(18F)
        bind.addStaCostText.text = HtmlCompat.fromHtml(staCost, HtmlCompat.FROM_HTML_MODE_LEGACY)
        bind.addRangeText.text = HtmlCompat.fromHtml(range, HtmlCompat.FROM_HTML_MODE_LEGACY)
        bind.addDefenseText.text = HtmlCompat.fromHtml(defense, HtmlCompat.FROM_HTML_MODE_LEGACY)
        bind.addEffectText.text = HtmlCompat.fromHtml(effect, HtmlCompat.FROM_HTML_MODE_LEGACY)
        bind.addEffectText.typeface = Typeface.DEFAULT
        bind.addDurationText.text = HtmlCompat.fromHtml(duration, HtmlCompat.FROM_HTML_MODE_LEGACY)

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