package com.example.thewitcherrpg.feature_character_sheet.presentation.equipment.listAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.thewitcherrpg.databinding.ArmorRowBinding
import com.example.thewitcherrpg.feature_character_sheet.domain.item_models.EquipmentItem

class LightEquipmentListAdapter(
    val itemClickString: (EquipmentItem) -> Unit
) : RecyclerView.Adapter<LightEquipmentListAdapter.LightViewHolder>() {

    private var armorList = emptyList<EquipmentItem>()
    private lateinit var currentItem: EquipmentItem

    inner class LightViewHolder(private val binding: ArmorRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: EquipmentItem, position: Int) {

            val weight = "Weight: " + item.weight.toString()
            val stoppingPower = "Stopping Power: " + item.stoppingPower.toString()

            with(binding) {
                armorNameText.text = item.name
                weightText.text = weight
                stoppingPowerText.text = stoppingPower

                rowLayout.setOnClickListener {
                    itemClickString(armorList[position])

                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LightViewHolder {
        val itemBinding =
            ArmorRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LightViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: LightViewHolder, position: Int) {
        currentItem = armorList[position]
        holder.bind(currentItem, position)
    }

    override fun getItemCount(): Int {
        return armorList.size
    }

    fun setData(itemList: ArrayList<EquipmentItem>) {
        this.armorList = itemList
        notifyDataSetChanged()
    }

}