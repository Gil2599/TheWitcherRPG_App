package com.example.thewitcherrpg.feature_character_sheet.presentation.equipment.listAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.thewitcherrpg.feature_character_sheet.presentation.equipment.EquipmentItem
import com.example.thewitcherrpg.databinding.ArmorRowBinding

class LightEquipmentListAdapter(con: Context, val itemClickString: (String) -> Unit,
                                val itemClickObject: (EquipmentItem) -> Unit
                                ) : RecyclerView.Adapter<LightEquipmentListAdapter.LightViewHolder>() {

    private var armorStringList: List<String>? = null
    private var armorObjectList: List<EquipmentItem>? = null
    private lateinit var currentItemString: String
    private lateinit var currentItemObject: EquipmentItem
    private var itemString = ""
    private var itemObject = EquipmentItem()

    inner class LightViewHolder(private val binding: ArmorRowBinding): RecyclerView.ViewHolder(binding.root) {

        fun bindString(armorString: String, position: Int){
            val pair = armorString.split(":").toTypedArray()
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
                armorNameText.text = armorName
                weightText.text = weight
                stoppingPowerText.text = stoppingPower

                rowLayout.setOnClickListener{

                    itemString = armorStringList!![position]
                    itemClickString(itemString)

                }
            }
        }

        fun bindObject(armorItem: EquipmentItem, position: Int){

            val weight = "Weight: " + armorItem.weight.toString()
            val stoppingPower = "Stopping Power: " + armorItem.stoppingPower.toString()


            with(binding){

                armorNameText.text = armorItem.name
                weightText.text = weight
                stoppingPowerText.text = stoppingPower

                rowLayout.setOnClickListener{

                    itemObject = armorObjectList!![position]
                    itemClickObject(itemObject)

                }

            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LightViewHolder {
        val itemBinding = ArmorRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LightViewHolder(itemBinding)    }

    override fun onBindViewHolder(holder: LightViewHolder, position: Int) {

        if (!armorStringList.isNullOrEmpty()){
            currentItemString = armorStringList!![position]
            holder.bindString(currentItemString, position)
        }

        if (!armorObjectList.isNullOrEmpty()){
            currentItemObject = armorObjectList!![position]
            holder.bindObject(currentItemObject, position)
        }

    }

    override fun getItemCount(): Int {
        if (!armorStringList.isNullOrEmpty()) return armorStringList!!.size
        if (!armorObjectList.isNullOrEmpty()) return armorObjectList!!.size

        return 0
    }

    fun setData(itemStringList: List<String>? = null, itemArray: ArrayList<EquipmentItem>? = null){

        this.armorStringList = itemStringList
        this.armorObjectList = itemArray

        notifyDataSetChanged()
    }

}