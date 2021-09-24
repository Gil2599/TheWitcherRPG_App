package com.example.thewitcherrpg.characterSheet.equipment.listAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.thewitcherrpg.R
import com.example.thewitcherrpg.databinding.ArmorRowBinding

class LightEquipmentListAdapter(con: Context, val itemClick: (ArrayList<Any>) -> Unit) : RecyclerView.Adapter<LightEquipmentListAdapter.LightViewHolder>() {

    private var armorList = emptyList<String>()
    private var addSpell: Boolean = false
    private lateinit var currentItem: String
    private var context: Context = con
    private lateinit var array: ArrayList<Any>

    inner class LightViewHolder(private val binding: ArmorRowBinding): RecyclerView.ViewHolder(binding.root) {
        fun addBind(hex: String, position: Int){
            val pair = hex.split(":").toTypedArray()
            val armorName = pair[0]
            val stoppingPower = "Stopping Power: " + pair[1]
            val availability = pair[2]
            val armorEnhancement = pair[3]
            val effect = pair[4]
            val encumbValue = pair[5]
            val weight = "Weight: " + pair[6]
            val price = pair[6]
            val type = pair[7]

            with (binding) {
                spellNameText.text = armorName
                weightText.text = weight
                stoppingPowerText.text = stoppingPower
                rowLayout.setOnClickListener(){

                    when (type){
                        "head" -> {array[0] = ArmorType.HEAD
                                    array[1] = armorList[position]
                                    itemClick(array)}

                        "chest" -> {array[0] = ArmorType.CHEST
                            array[1] = armorList[position]
                            itemClick(array)}
                    }

                }
            }
        }

        fun bind(sign: String, name: String, weight: String, sp: String){
            with (binding){
                spellNameText.text = name
                weightText.text = weight
                stoppingPowerText.text = sp

                rowLayout.setOnClickListener(){

                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LightViewHolder {
        val itemBinding = ArmorRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LightViewHolder(itemBinding)    }

    override fun onBindViewHolder(holder: LightViewHolder, position: Int) {
        currentItem = armorList[position]

        //Check whether to show all spells or only character spells
        if (addSpell){
            holder.addBind(currentItem, position)
        }
        else{
            val tags = context.resources.getStringArray(R.array.hexes_list_data)
            for (tag in tags) {

                val pair = tag.split(":").toTypedArray()
                val armorName = pair[0]
                val stoppingPower = "Stopping Power: " + pair[1]
                val availability = pair[2]
                val armorEnhancement = pair[3]
                val effect = pair[4]
                val encumbValue = pair[5]
                val weight = "Weight: " + pair[6]
                val price = pair[6]

                if (currentItem == armorName) {
                    holder.bind(tag, armorName, weight, stoppingPower)
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return armorList.size
    }

    fun setData(spell: List<String>){
        this.armorList = spell
        notifyDataSetChanged()
    }

    fun setAddSpell(value: Boolean){
        addSpell= value
    }

    enum class ArmorType {
        HEAD,
        CHEST,
        LEG
    }
}