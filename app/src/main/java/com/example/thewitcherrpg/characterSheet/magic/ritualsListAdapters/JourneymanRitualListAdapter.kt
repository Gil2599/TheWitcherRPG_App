package com.example.thewitcherrpg.characterSheet.magic.ritualsListAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.thewitcherrpg.R
import com.example.thewitcherrpg.databinding.SpellRowBinding

class JourneymanRitualListAdapter(con: Context, val itemClick: (String) -> Unit) : RecyclerView.Adapter<JourneymanRitualListAdapter.JourneymanViewHolder>() {

    private var spellList = emptyList<String>()
    private var addSpell: Boolean = false
    private lateinit var currentItem: String
    private var context: Context = con

    inner class JourneymanViewHolder(private val binding: SpellRowBinding): RecyclerView.ViewHolder(binding.root) {
        fun addBind(sign: String, position: Int){
            val pair = sign.split(":").toTypedArray()
            val spellName = pair[0]
            val staCost = "STA Cost: " + pair[1]
            val description = pair[2]
            val preparation = "Preparation Time: " + pair[3]
            val difficulty = "Difficulty: " + pair[4]
            val duration = pair[5]
            val components = pair[6]

            with (binding) {
                spellNameText.text = spellName
                staCostText.text = staCost
                rangeText.text = difficulty
                rowLayout.setOnClickListener(){
                    itemClick(spellList[position])
                }
            }
        }

        fun bind(sign: String, name: String, staCost: String, difficulty: String){
            with (binding){
                spellNameText.text = name
                staCostText.text = staCost
                rangeText.text = difficulty

                rowLayout.setOnClickListener(){
                    itemClick(sign)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JourneymanViewHolder {
        val itemBinding = SpellRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return JourneymanViewHolder(itemBinding)    }

    override fun onBindViewHolder(holder: JourneymanViewHolder, position: Int) {
        currentItem = spellList[position]

        //Check whether to show all spells or only character spells
        if (addSpell){
            holder.addBind(currentItem, position)
        }
        else{
            val tags = context.resources.getStringArray(R.array.journeyman_rituals_list_data)
            for (tag in tags) {

                val pair = tag.split(":").toTypedArray()
                val spellName = pair[0]
                val staCost = "STA Cost: " + pair[1]
                val description = pair[2]
                val preparation = "Preparation Time: " + pair[3]
                val difficulty = "Difficulty: " + pair[4]
                val duration = pair[5]
                val components = pair[6]

                if (currentItem == spellName) {
                    holder.bind(tag, spellName, staCost, difficulty)
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return spellList.size
    }

    fun setData(spell: List<String>){
        this.spellList = spell
        notifyDataSetChanged()
    }

    fun setAddSpell(value: Boolean){
        addSpell= value
    }
}