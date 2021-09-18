package com.example.thewitcherrpg.characterSheet.magic

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.HtmlCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thewitcherrpg.R
import com.example.thewitcherrpg.characterSheet.SharedViewModel
import com.example.thewitcherrpg.characterSheet.magic.SpellListAdapters.JourneymanSpellListAdapter
import com.example.thewitcherrpg.characterSheet.magic.SpellListAdapters.NoviceSpellListAdapter
import com.example.thewitcherrpg.databinding.FragmentSpellsBinding
import kotlinx.android.synthetic.main.custom_dialog_add_spell.defense_text
import kotlinx.android.synthetic.main.custom_dialog_add_spell.duration_text
import kotlinx.android.synthetic.main.custom_dialog_add_spell.effect_text
import kotlinx.android.synthetic.main.custom_dialog_add_spell.range_text
import kotlinx.android.synthetic.main.custom_dialog_add_spell.spell_name_text
import kotlinx.android.synthetic.main.custom_dialog_add_spell.sta_cost_text
import kotlinx.android.synthetic.main.custom_dialog_char_spell.*

class MagicFragment : Fragment() {
    private var _binding: FragmentSpellsBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: SharedViewModel by activityViewModels()

    private var buttonClicked: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentSpellsBinding.inflate(inflater, container, false)
        val view = binding.root

        //Remove or change magic categories depending on profession
        if(sharedViewModel.profession.value == "Witcher"){
            (binding.addSpellButton.parent as ViewGroup).removeView(binding.addSpellButton)
            (binding.addRitualButton.parent as ViewGroup).removeView(binding.addRitualButton)
            (binding.addHexButton.parent as ViewGroup).removeView(binding.addHexButton)
        }
        else if (sharedViewModel.profession.value == "Priest"){
            binding.addSpellButton.setImageResource(R.drawable.ic_celtic_knot_icon)
        }

        listAdaptersInit()

        binding.addButton.setOnClickListener(){
            onAddButtonClicked()
        }
        binding.addSpellButton.setOnClickListener(){
            Navigation.findNavController(view).navigate(R.id.action_spellsFragment_to_spellAddFragment)
        }

        binding.addSpellButton.animate().scaleX(0.8F).scaleY(0.8F).alpha(0F).translationY(50F).duration = 0
        binding.addRitualButton.animate().scaleX(0.8F).scaleY(0.8F).alpha(0F).translationY(50F).duration = 0
        binding.addHexButton.animate().scaleX(0.8F).scaleY(0.8F).alpha(0F).translationY(50F).duration = 0
        binding.addSignButton.animate().scaleX(0.8F).scaleY(0.8F).alpha(0F).translationY(50F).duration = 0

        return view
    }

    //Implement animations and logic for expandable action button
    private fun onAddButtonClicked() {
        setAnimation()
        buttonClicked = !buttonClicked
    }

    private fun setAnimation(){

        if (!buttonClicked){
            binding.addSpellButton.animate()
                .withStartAction(Runnable {
                    binding.addSpellButton.visibility = View.VISIBLE })
                .alpha(1F)
                .setDuration(300)
                .translationY(-10F)

            binding.addRitualButton.animate()
                .withStartAction(Runnable {
                    binding.addRitualButton.visibility = View.VISIBLE })
                .alpha(1F)
                .setDuration(300)
                .translationY(-10F)

            binding.addHexButton.animate()
                .withStartAction(Runnable {
                    binding.addRitualButton.visibility = View.VISIBLE })
                .alpha(1F)
                .setDuration(300)
                .translationY(-10F)

            binding.addSignButton.animate()
                .withStartAction(Runnable {
                    binding.addRitualButton.visibility = View.VISIBLE })
                .alpha(1F)
                .setDuration(300)
                .translationY(-10F)

            binding.addButton.animate()
                .rotation(135F) }

        else{
            binding.addSpellButton.animate()
                .alpha(0F)
                .setDuration(300)
                .translationY(50F)
                .withEndAction(Runnable {
                    binding.addSpellButton.visibility = View.GONE })

            binding.addRitualButton.animate()
                .alpha(0F)
                .setDuration(300)
                .translationY(50F)
                .withEndAction(Runnable {
                    binding.addSpellButton.visibility = View.GONE })

            binding.addHexButton.animate()
                .alpha(0F)
                .setDuration(300)
                .translationY(50F)
                .withEndAction(Runnable {
                    binding.addSpellButton.visibility = View.GONE })

            binding.addSignButton.animate()
                .alpha(0F)
                .setDuration(300)
                .translationY(50F)
                .withEndAction(Runnable {
                    binding.addSpellButton.visibility = View.GONE })

            //binding.addButton.startAnimation(rotateClose)
            binding.addButton.animate()
                .rotation(0F).duration = 300
        }
    }

    private fun listAdaptersInit(){

        val noviceAdapter = NoviceSpellListAdapter(requireContext()){
                spell -> showSpellDialog(spell)
        }
        sharedViewModel.noviceSpellList.observe(viewLifecycleOwner, { spell ->
            noviceAdapter.setData(spell)
        })

        val journeymanAdapter = JourneymanSpellListAdapter(requireContext())
        journeymanAdapter.setData(resources.getStringArray(R.array.journeyman_spells_list_data).toList())

        binding.recyclerViewNovice.adapter = noviceAdapter
        binding.recyclerViewNovice.layoutManager = LinearLayoutManager(requireContext())

        binding.recyclerViewJourneyman.adapter = journeymanAdapter
        binding.recyclerViewJourneyman.layoutManager = LinearLayoutManager(requireContext())

        binding.recyclerViewNovice.isNestedScrollingEnabled = false
        binding.recyclerViewJourneyman.isNestedScrollingEnabled = false

    }

    private fun showSpellDialog(spell: String?) {
        val dialog = Dialog(requireContext())
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setContentView(R.layout.custom_dialog_char_spell)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val pair = spell!!.split(":").toTypedArray()
        val spellName = pair[0]
        val staCost = "<b>" + "STA Cost: " + "</b>" + pair[1]
        val effect = "<b>" + "Effect: " + "</b>" + pair[2]
        val range = "<b>" + "Range: " + "</b>" + pair[3]
        val duration = "<b>" + "Duration: " + "</b>" + pair[4]
        val defense = "<b>" + "Defense: " + "</b>" + pair[5]
        val element = pair[6]

        dialog.spell_name_text.text = spellName
        dialog.sta_cost_text.text = HtmlCompat.fromHtml(staCost, HtmlCompat.FROM_HTML_MODE_LEGACY)
        dialog.range_text.text = HtmlCompat.fromHtml(range, HtmlCompat.FROM_HTML_MODE_LEGACY)
        dialog.defense_text.text = HtmlCompat.fromHtml(defense, HtmlCompat.FROM_HTML_MODE_LEGACY)
        dialog.effect_text.text = HtmlCompat.fromHtml(effect, HtmlCompat.FROM_HTML_MODE_LEGACY)
        dialog.duration_text.text = HtmlCompat.fromHtml(duration, HtmlCompat.FROM_HTML_MODE_LEGACY)
        dialog.element_text.text = element
        //textview.setText(Html.fromHtml(resources.getString(R.string.text)));

        dialog.castSpellbutton.setOnClickListener(){
            dialog.dismiss()
        }
        dialog.cancel_button.setOnClickListener(){
            dialog.dismiss()
        }
        dialog.removebutton.setOnClickListener(){
            sharedViewModel.removeNoviceSpell(spellName)
            Toast.makeText(context, "$spellName removed from ${sharedViewModel.name.value}", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        dialog.show()
    }



}

