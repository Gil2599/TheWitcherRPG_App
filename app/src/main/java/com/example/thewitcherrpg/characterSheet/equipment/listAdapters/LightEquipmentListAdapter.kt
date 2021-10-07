package com.example.thewitcherrpg.characterSheet.equipment.listAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.thewitcherrpg.databinding.ArmorRowBinding

class LightEquipmentListAdapter(con: Context, val itemClick: (String) -> Unit) : RecyclerView.Adapter<LightEquipmentListAdapter.LightViewHolder>() {

    private var armorList = emptyList<String>()
    private lateinit var currentItem: String
    private var context: Context = con
    private var item = ""

    inner class LightViewHolder(private val binding: ArmorRowBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(hex: String, position: Int){
            val pair = hex.split(":").toTypedArray()
            val armorName = pair[0]
            val stoppingPower = "Stopping Power: " + pair[1]
            val availability = pair[2]
            val armorEnhancement = pair[3]
            val effect = pair[4]
            val encumbValue = pair[5]
            val weight = "Weight: " + pair[6]
            val price = pair[7]
            val type = pair[8]

            with (binding) {
                spellNameText.text = armorName
                weightText.text = weight
                stoppingPowerText.text = stoppingPower

                rowLayout.setOnClickListener(){

                    item = armorList[position]
                    itemClick(item)

                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LightViewHolder {
        val itemBinding = ArmorRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LightViewHolder(itemBinding)    }

    override fun onBindViewHolder(holder: LightViewHolder, position: Int) {
        currentItem = armorList[position]

        holder.bind(currentItem, position)

    }

    override fun getItemCount(): Int {
        return armorList.size
    }

    fun setData(item: List<String>){
        this.armorList = item
        notifyDataSetChanged()
    }

}