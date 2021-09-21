package com.example.thewitcherrpg.characterSheet.magic.addMagicFragments

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
import com.example.thewitcherrpg.characterSheet.SharedViewModel
import com.example.thewitcherrpg.characterSheet.magic.invocationListAdapter.*
import com.example.thewitcherrpg.databinding.FragmentInvocationAddBinding
import kotlinx.android.synthetic.main.custom_dialog_add_spell.*

class InvocationAddFragment : Fragment() {
    private var _binding: FragmentInvocationAddBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: SharedViewModel by activityViewModels()

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

    private fun showSpellDialog(spell: String?, level: String?) {
        val dialog = Dialog(requireContext())
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setContentView(R.layout.custom_dialog_add_spell)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val pair = spell!!.split(":").toTypedArray()
        val spellName = pair[0]
        val staCost = "<b>" + "STA Cost: " + "</b>" + pair[1]
        val effect = "<b>" + "Effect: " + "</b>" + pair[2]
        val range = "<b>" + "Range: " + "</b>" + pair[3]
        val duration = "<b>" + "Duration: " + "</b>" + pair[4]
        val defense = "<b>" + "Defense: " + "</b>" + pair[5]

        dialog.spell_name_text.text = spellName
        dialog.sta_cost_text.text = HtmlCompat.fromHtml(staCost, HtmlCompat.FROM_HTML_MODE_LEGACY)
        dialog.range_text.text = HtmlCompat.fromHtml(range, HtmlCompat.FROM_HTML_MODE_LEGACY)
        dialog.defense_text.text = HtmlCompat.fromHtml(defense, HtmlCompat.FROM_HTML_MODE_LEGACY)
        dialog.effect_text.text = HtmlCompat.fromHtml(effect, HtmlCompat.FROM_HTML_MODE_LEGACY)
        dialog.duration_text.text = HtmlCompat.fromHtml(duration, HtmlCompat.FROM_HTML_MODE_LEGACY)
        dialog.addSpellbutton.text = "Learn Invocation"

        //Check spell level to add it to correct character spell list
        dialog.addSpellbutton.setOnClickListener(){
            when (level){
                "noviceDruid" -> {
                    if (sharedViewModel.addNoviceDruidInvo(spellName))
                        Toast.makeText(context, "$spellName added to ${sharedViewModel.name.value}", Toast.LENGTH_SHORT).show()
                    else Toast.makeText(context, "${sharedViewModel.name.value} already knows $spellName", Toast.LENGTH_SHORT).show()
                }
                "journeymanDruid" -> {
                    if (sharedViewModel.addJourneymanDruidInvo(spellName))
                        Toast.makeText(context, "$spellName added to ${sharedViewModel.name.value}", Toast.LENGTH_SHORT).show()
                    else Toast.makeText(context, "${sharedViewModel.name.value} already knows $spellName", Toast.LENGTH_SHORT).show()
                }
                "masterDruid" -> {
                    if (sharedViewModel.addMasterDruidInvo(spellName))
                        Toast.makeText(context, "$spellName added to ${sharedViewModel.name.value}", Toast.LENGTH_SHORT).show()
                    else Toast.makeText(context, "${sharedViewModel.name.value} already knows $spellName", Toast.LENGTH_SHORT).show()
                }
                "novicePreacher" -> {
                    if (sharedViewModel.addNovicePreacherInvo(spellName))
                        Toast.makeText(context, "$spellName added to ${sharedViewModel.name.value}", Toast.LENGTH_SHORT).show()
                    else Toast.makeText(context, "${sharedViewModel.name.value} already knows $spellName", Toast.LENGTH_SHORT).show()
                }
                "journeymanPreacher" -> {
                    if (sharedViewModel.addJourneymanPreacherInvo(spellName))
                        Toast.makeText(context, "$spellName added to ${sharedViewModel.name.value}", Toast.LENGTH_SHORT).show()
                    else Toast.makeText(context, "${sharedViewModel.name.value} already knows $spellName", Toast.LENGTH_SHORT).show()
                }
                "masterPreacher" -> {
                    if (sharedViewModel.addMasterPreacherInvo(spellName))
                        Toast.makeText(context, "$spellName added to ${sharedViewModel.name.value}", Toast.LENGTH_SHORT).show()
                    else Toast.makeText(context, "${sharedViewModel.name.value} already knows $spellName", Toast.LENGTH_SHORT).show()
                }
                "archPriest" -> {
                    if (sharedViewModel.addArchPriestInvo(spellName))
                        Toast.makeText(context, "$spellName added to ${sharedViewModel.name.value}", Toast.LENGTH_SHORT).show()
                    else Toast.makeText(context, "${sharedViewModel.name.value} already knows $spellName", Toast.LENGTH_SHORT).show()
                }
            }

            dialog.dismiss()
        }

        dialog.add_spell_cancel_button.setOnClickListener(){
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun druidListAdapters(){

        //Receive information from recyclerView adapter
        val noviceDruidAdapter = NoviceDruidInvocationListAdapter(requireContext()){
                spell -> showSpellDialog(spell, "noviceDruid") //Determines which list this spell goes under
        }
        noviceDruidAdapter.setData(resources.getStringArray(R.array.novice_druidInvo_list_data).toList())
        noviceDruidAdapter.setAddSpell(true) //Sets add spell state to true to show all spells

        val journeymanDruidAdapter = JourneymanDruidInvocationListAdapter(requireContext()){
                spell -> showSpellDialog(spell, "journeymanDruid")
        }
        journeymanDruidAdapter.setData(resources.getStringArray(R.array.journeyman_druidInvo_list_data).toList())
        journeymanDruidAdapter.setAddSpell(true) //Sets add spell state to true to show all spells

        val masterAdapterDruid = MasterDruidInvocationListAdapter(requireContext()){
                spell -> showSpellDialog(spell, "masterDruid")
        }
        masterAdapterDruid.setData(resources.getStringArray(R.array.master_druidInvo_data).toList())
        masterAdapterDruid.setAddSpell(true) //Sets add spell state to true to show all spells

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
        val novicePreacherAdapter = NovicePreacherInvocationListAdapter(requireContext()){
                spell -> showSpellDialog(spell, "novicePreacher") //Determines which list this spell goes under
        }
        novicePreacherAdapter.setData(resources.getStringArray(R.array.novice_preacherInvo_list_data).toList())
        novicePreacherAdapter.setAddSpell(true) //Sets add spell state to true to show all spells

        val journeymanPreacherAdapter = JourneymanPreacherInvocationListAdapter(requireContext()){
                spell -> showSpellDialog(spell, "journeymanPreacher")
        }
        journeymanPreacherAdapter.setData(resources.getStringArray(R.array.journeyman_preacherInvo_list_data).toList())
        journeymanPreacherAdapter.setAddSpell(true) //Sets add spell state to true to show all spells

        val masterPreacherAdapter = MasterPreacherInvocationListAdapter(requireContext()){
                spell -> showSpellDialog(spell, "masterPreacher")
        }
        masterPreacherAdapter.setData(resources.getStringArray(R.array.master_preacherInvo_list_data).toList())
        masterPreacherAdapter.setAddSpell(true) //Sets add spell state to true to show all spells

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
        val archPriestAdapter = NovicePreacherInvocationListAdapter(requireContext()){
                spell -> showSpellDialog(spell, "archPriest") //Determines which list this spell goes under
        }
        archPriestAdapter.setData(resources.getStringArray(R.array.archPriestInvo_list_data).toList())
        archPriestAdapter.setAddSpell(true) //Sets add spell state to true to show all spells

        binding.recyclerViewArchPriest.adapter = archPriestAdapter
        binding.recyclerViewArchPriest.layoutManager = LinearLayoutManager(requireContext())

        binding.recyclerViewArchPriest.isNestedScrollingEnabled = false
    }

}