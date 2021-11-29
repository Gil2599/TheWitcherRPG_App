package com.example.thewitcherrpg.feature_character_sheet.presentation.equipment.listAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.thewitcherrpg.R
import com.example.thewitcherrpg.TheWitcherTRPGApp
import com.example.thewitcherrpg.databinding.ArmorRowBinding
import com.example.thewitcherrpg.feature_character_sheet.domain.item_types.EquipmentTypes
import com.example.thewitcherrpg.feature_character_sheet.domain.models.ArmorSet
import com.example.thewitcherrpg.feature_character_sheet.domain.models.EquipmentItem

class ArmorSetListAdapter(
    val itemClickString: (ArmorSet) -> Unit
) : RecyclerView.Adapter<ArmorSetListAdapter.ViewHolder>() {

    private var armorList = emptyList<ArmorSet>()
    private lateinit var currentItem: ArmorSet

    inner class ViewHolder(private val binding: ArmorRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ArmorSet, position: Int) {

            val weight = "Cost: " + item.cost.toString()
            val stoppingPower = "Stopping Power: " + item.stoppingPower.toString()

            with(binding) {
                armorNameText.text = item.name
                costText.text = weight
                stoppingPowerText.text = stoppingPower

                if (item.isRelic) {
                    armorNameText.setTextColor(
                        ContextCompat.getColor(
                            TheWitcherTRPGApp.getContext()!!,
                            R.color.relic
                        )
                    )
                    costText.setTextColor(
                        ContextCompat.getColor(
                            TheWitcherTRPGApp.getContext()!!,
                            R.color.relic
                        )
                    )
                    stoppingPowerText.setTextColor(
                        ContextCompat.getColor(
                            TheWitcherTRPGApp.getContext()!!,
                            R.color.relic
                        )
                    )
                }

                rowLayout.setOnClickListener {
                    itemClickString(armorList[position])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            ArmorRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        currentItem = armorList[position]
        holder.bind(currentItem, position)
    }

    override fun getItemCount(): Int {
        return armorList.size
    }

    fun setData(itemList: ArrayList<ArmorSet>) {
        this.armorList = itemList
        notifyDataSetChanged()
    }
}