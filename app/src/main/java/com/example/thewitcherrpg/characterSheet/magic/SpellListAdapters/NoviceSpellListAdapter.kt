package com.example.thewitcherrpg.characterSheet.magic.SpellListAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.thewitcherrpg.R
import kotlinx.android.synthetic.main.spell_row.view.*
import kotlinx.android.synthetic.main.spell_row.view.rowLayout
import javax.security.auth.callback.Callback

class NoviceSpellListAdapter(con: Context, val itemClick: (String) -> Unit) : RecyclerView.Adapter<NoviceSpellListAdapter.MyViewHolder>() {

    private var spellList = emptyList<String>()
    private var context: Context = con
    private lateinit var currentItem: String

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.spell_row, parent, false))

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        currentItem = spellList[position]

        val pair = currentItem.split(":").toTypedArray()
        val spellName = pair[0]
        val staCost = "STA Cost: " + pair[1]
        val description = pair[2]
        val range = "Range: " + pair[3]
        val duration = pair[4]
        val defense = pair[5]
        val element = pair[6]

        holder.itemView.spell_name_text.text = spellName
        holder.itemView.sta_cost_text.text = staCost
        holder.itemView.range_text.text = range
        holder.itemView.element_text.text = element

        holder.itemView.rowLayout.setOnClickListener {
            itemClick(spellList[position])
        }

    }

    override fun getItemCount(): Int {
        return spellList.size
    }

    fun setData(spell: List<String>){
        this.spellList = spell
        notifyDataSetChanged()
    }

}