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
import com.example.thewitcherrpg.characterSheet.magic.signsListAdapters.AlternateSignsListAdapter
import com.example.thewitcherrpg.characterSheet.magic.signsListAdapters.BasicSignsListAdapter
import com.example.thewitcherrpg.databinding.CustomDialogAddSpellBinding
import com.example.thewitcherrpg.databinding.FragmentSignsAddBinding

class SignsAddFragment : Fragment() {
    private var _binding: FragmentSignsAddBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignsAddBinding.inflate(inflater, container, false)
        val view = binding.root

        val callback = requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            // Handle the back button event
            Navigation.findNavController(view).navigate(R.id.action_signsAddFragment_to_charMagicFragment)
        }
        callback.isEnabled = true

        listAdaptersInit()

        return view
    }

    private fun listAdaptersInit(){

        //Receive information from recyclerView adapter
        val basicAdapter = BasicSignsListAdapter(requireContext()){
                sign -> showSignDialog(sign, SignLevel.BASIC) //Determines which list this spell goes under
        }
        basicAdapter.setData(resources.getStringArray(R.array.basic_signs_list_data).toList())
        basicAdapter.setAddSpell(true) //Sets add spell state to true to show all spells

        val alternateAdapter = AlternateSignsListAdapter(requireContext()){
                spell -> showSignDialog(spell, SignLevel.ALTERNATE)
        }
        alternateAdapter.setData(resources.getStringArray(R.array.alternate_signs_list_data).toList())
        alternateAdapter.setAddSpell(true) //Sets add spell state to true to show all spells

        binding.recyclerViewBasic.adapter = basicAdapter
        binding.recyclerViewBasic.layoutManager = LinearLayoutManager(requireContext())

        binding.recyclerViewAlternate.adapter = alternateAdapter
        binding.recyclerViewAlternate.layoutManager = LinearLayoutManager(requireContext())

        binding.recyclerViewBasic.isNestedScrollingEnabled = false
        binding.recyclerViewAlternate.isNestedScrollingEnabled = false

    }

    private fun showSignDialog(spell: String?, level: SignLevel) {
        val dialog = Dialog(requireContext())
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val bind : CustomDialogAddSpellBinding = CustomDialogAddSpellBinding.inflate(layoutInflater)
        dialog.setContentView(bind.root)

        val pair = spell!!.split(":").toTypedArray()
        val spellName = pair[0]
        val staCost = "<b>" + "STA Cost: " + "</b>" + pair[1]
        val effect = "<b>" + "Effect: " + "</b>" + pair[2]
        val range = "<b>" + "Range: " + "</b>" + pair[3]
        val duration = "<b>" + "Duration: " + "</b>" + pair[4]
        val defense = "<b>" + "Defense: " + "</b>" + pair[5]
        val element = pair[6]

        bind.addSpellNameText.text = spellName
        bind.addStaCostText.text = HtmlCompat.fromHtml(staCost, HtmlCompat.FROM_HTML_MODE_LEGACY)
        bind.addRangeText.text = HtmlCompat.fromHtml(range, HtmlCompat.FROM_HTML_MODE_LEGACY)
        bind.addDefenseText.text = HtmlCompat.fromHtml(defense, HtmlCompat.FROM_HTML_MODE_LEGACY)
        bind.addEffectText.text = HtmlCompat.fromHtml(effect, HtmlCompat.FROM_HTML_MODE_LEGACY)
        bind.addDurationText.text = HtmlCompat.fromHtml(duration, HtmlCompat.FROM_HTML_MODE_LEGACY)
        bind.addSpellElementText.text = element

        //Check spell level to add it to correct character spell list
        bind.addSpellbutton.setOnClickListener(){
            when (level){
                SignLevel.BASIC -> {
                    if (sharedViewModel.addBasicSign(spellName))
                        Toast.makeText(context, "$spellName added to ${sharedViewModel.name.value}", Toast.LENGTH_SHORT).show()

                    else Toast.makeText(context, "${sharedViewModel.name.value} already knows $spellName", Toast.LENGTH_SHORT).show()

                }
                SignLevel.ALTERNATE -> {
                    if (sharedViewModel.addAlternateSign(spellName))
                        Toast.makeText(context, "$spellName added to ${sharedViewModel.name.value}", Toast.LENGTH_SHORT).show()

                    else Toast.makeText(context, "${sharedViewModel.name.value} already knows $spellName", Toast.LENGTH_SHORT).show()
                }
            }

            dialog.dismiss()
        }

        bind.addSpellCancelButton.setOnClickListener(){
            dialog.dismiss()
        }

        dialog.show()
    }

    //Enum class to determine spell level
    enum class SignLevel {
        BASIC,
        ALTERNATE
    }

}