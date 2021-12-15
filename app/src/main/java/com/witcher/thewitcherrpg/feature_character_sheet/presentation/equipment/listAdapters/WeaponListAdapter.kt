package com.witcher.thewitcherrpg.feature_character_sheet.presentation.equipment.listAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.witcher.thewitcherrpg.R
import com.witcher.thewitcherrpg.TheWitcherTRPGApp
import com.witcher.thewitcherrpg.databinding.ArmorRowBinding
import com.witcher.thewitcherrpg.feature_character_sheet.domain.item_types.WeaponTypes
import com.witcher.thewitcherrpg.feature_character_sheet.domain.models.WeaponItem

class WeaponListAdapter(
    val itemClickString: (WeaponItem) -> Unit
) : RecyclerView.Adapter<WeaponListAdapter.ViewHolder>() {

    private var armorList = emptyList<WeaponItem>()
    private lateinit var currentItem: WeaponItem

    inner class ViewHolder(private val binding: ArmorRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: WeaponItem, position: Int) {

            if (item.type == WeaponTypes.AMULET){
                binding.stoppingPowerText.text = "Effect: " + item.effect.toString()
                binding.costText.text = "Cost: " + item.cost
            }
            else {
                binding.stoppingPowerText.text = "Reliability: " + item.reliability.toString()
                binding.costText.text = "Damage: " + item.damage
            }

            with(binding) {
                armorNameText.text = item.name

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

    fun setData(itemList: ArrayList<WeaponItem>) {
        this.armorList = itemList
        notifyDataSetChanged()
    }

}