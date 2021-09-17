package com.example.thewitcherrpg.characterSheet.magic.SpellListAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.thewitcherrpg.R
import kotlinx.android.synthetic.main.spell_row.view.*

class CharSpellsListAdapter(con: Context): RecyclerView.Adapter<CharSpellsListAdapter.MyViewHolder>() {

    private var spellList = emptyList<String>()
    private var context: Context = con

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.spell_row, parent, false))

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = spellList[position]

        holder.itemView.spell_name_text.text = currentItem

        val tags = context.resources.getStringArray(R.array.novice_spells_list_data)
        for (tag in tags) {

            val pair = tag.split(":").toTypedArray()
            val spellName = pair[0]
            val staCost = "STA Cost: " + pair[1]
            val description = pair[2]
            val range = "Range: " + pair[3]
            val duration = pair[4]
            val defense = pair[5]

            if (currentItem == spellName) {
                //holder.itemView.spell_name_text.text = spellName
                holder.itemView.sta_cost_text.text = staCost
                holder.itemView.range_text.text = range
            }
        }



        /*holder.itemView.race_text.text = currentItem.race
        holder.itemView.id_text.text = currentItem.id.toString()

        val ip = "IP: " + currentItem.iP
        holder.itemView.IP_text.text = ip

        holder.itemView.rowLayout.setOnClickListener{
            val intent = Intent(context, MainActivity::class.java).also {
                it.putExtra("EXTRA_DATA", currentItem)
            }
            context.startActivity(intent)
        }*/

    }

    override fun getItemCount(): Int {
        return spellList.size
    }

    fun setData(spell: List<String>){
        this.spellList = spell
        notifyDataSetChanged()
    }

    private fun getSpellInfo(spell: String){

    }

}