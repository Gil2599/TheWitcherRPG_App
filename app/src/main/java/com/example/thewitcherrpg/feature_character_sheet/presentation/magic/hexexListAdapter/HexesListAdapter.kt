package com.example.thewitcherrpg.feature_character_sheet.presentation.magic.hexexListAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.thewitcherrpg.R
import com.example.thewitcherrpg.databinding.SpellRowBinding

class HexesListAdapter(con: Context, val itemClick: (String) -> Unit) : RecyclerView.Adapter<HexesListAdapter.HexesViewHolder>() {

    private var spellList = emptyList<String>()
    private var addSpell: Boolean = false
    private lateinit var currentItem: String
    private var context: Context = con

    inner class HexesViewHolder(private val binding: SpellRowBinding): RecyclerView.ViewHolder(binding.root) {
        fun addBind(hex: String, position: Int){
            val pair = hex.split(":").toTypedArray()
            val spellName = pair[0]
            val staCost = "STA Cost: " + pair[1]
            val danger = "Danger: " + pair[3]

            with (binding) {
                spellNameText.text = spellName
                staCostText.text = staCost
                rangeText.text = danger
                rowLayout.setOnClickListener(){
                    itemClick(spellList[position])
                }
            }
        }

        fun bind(sign: String, name: String, staCost: String, danger: String){
            with (binding){
                spellNameText.text = name
                staCostText.text = staCost
                rangeText.text = danger

                rowLayout.setOnClickListener(){
                    itemClick(sign)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HexesViewHolder {
        val itemBinding = SpellRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HexesViewHolder(itemBinding)    }

    override fun onBindViewHolder(holder: HexesViewHolder, position: Int) {
        currentItem = spellList[position]

        //Check whether to show all spells or only character spells
        if (addSpell){
            holder.addBind(currentItem, position)
        }
        else{
            val tags = context.resources.getStringArray(R.array.hexes_list_data)
            for (tag in tags) {

                val pair = tag!!.split(":").toTypedArray()
                val spellName = pair[0]
                val staCost = "STA Cost: " + pair[1]
                val danger = "Danger: " + pair[3]

                if (currentItem == spellName) {
                    holder.bind(tag, spellName, staCost, danger)
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