package com.example.thewitcherrpg.characterSheet.magic.invocationListAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.thewitcherrpg.R
import com.example.thewitcherrpg.databinding.SpellRowBinding

class ArchPriestInvocationListAdapter(con: Context, val itemClick: (String) -> Unit) : RecyclerView.Adapter<ArchPriestInvocationListAdapter.PriestViewHolder>()  {

    private var spellList = emptyList<String>()
    private var addSpell: Boolean = false
    private lateinit var currentItem: String
    private var context: Context = con

    inner class PriestViewHolder(private val binding: SpellRowBinding): RecyclerView.ViewHolder(binding.root) {
        fun addBind(sign: String, position: Int){
            val pair = sign.split(":").toTypedArray()
            val spellName = pair[0]
            val staCost = "STA Cost: " + pair[1]
            val description = pair[2]
            val range = "Range: " + pair[3]
            val duration = pair[4]
            val defense = pair[5]

            with (binding) {
                spellNameText.text = spellName
                staCostText.text = staCost
                rangeText.text = range
                rowLayout.setOnClickListener(){
                    itemClick(spellList[position])
                }
            }
        }

        fun bind(sign: String, name: String, staCost: String, range: String){
            with (binding){
                spellNameText.text = name
                staCostText.text = staCost
                rangeText.text = range

                rowLayout.setOnClickListener(){
                    itemClick(sign)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PriestViewHolder {
        val itemBinding = SpellRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PriestViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: PriestViewHolder, position: Int) {
        currentItem = spellList[position]

        //Check whether to show all spells or only character spells
        if (addSpell){
            holder.addBind(currentItem, position)
        }
        else{
            val tags = context.resources.getStringArray(R.array.archPriestInvo_list_data)
            for (tag in tags) {

                val pair = tag.split(":").toTypedArray()
                val spellName = pair[0]
                val staCost = "STA Cost: " + pair[1]
                val description = pair[2]
                val range = "Range: " + pair[3]
                val duration = pair[4]
                val defense = pair[5]

                if (currentItem == spellName) {
                    holder.bind(tag, spellName, staCost, range)
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