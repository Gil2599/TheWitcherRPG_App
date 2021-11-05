package com.example.thewitcherrpg.feature_character_sheet.presentation.magic.spellListAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.thewitcherrpg.databinding.SpellRowBinding
import com.example.thewitcherrpg.feature_character_sheet.presentation.magic.MagicItem

class JourneymanListAdapter(val itemClick: (MagicItem) -> Unit): RecyclerView.Adapter<JourneymanListAdapter.JourneymanViewHolder>() {

    private var itemList = emptyList<MagicItem>()
    private lateinit var currentItem: MagicItem

    inner class JourneymanViewHolder(private val binding: SpellRowBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MagicItem, position: Int){

            val staCost = "STA Cost: " + if (item.staminaCost != null) item.staminaCost.toString() else "Variable"
            val range = "Range: " + item.range

            with (binding) {
                spellNameText.text = item.name
                staCostText.text = staCost
                elementText.text = item.element
                rangeText.text = range
                rowLayout.setOnClickListener{
                    itemClick(itemList[position])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JourneymanViewHolder {
        val itemBinding = SpellRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return JourneymanViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: JourneymanViewHolder, position: Int) {
        currentItem = itemList[position]
        holder.bind(currentItem, position)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun setData(spell: List<MagicItem>){
        this.itemList = spell
        notifyDataSetChanged()
    }
}