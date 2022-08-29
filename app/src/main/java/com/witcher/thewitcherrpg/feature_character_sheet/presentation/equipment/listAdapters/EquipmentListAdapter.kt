package com.witcher.thewitcherrpg.feature_character_sheet.presentation.equipment.listAdapters

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.witcher.thewitcherrpg.R
import com.witcher.thewitcherrpg.TheWitcherTRPGApp
import com.witcher.thewitcherrpg.databinding.ArmorRowBinding
import com.witcher.thewitcherrpg.databinding.MagicHeaderBinding
import com.witcher.thewitcherrpg.feature_character_sheet.domain.item_types.EquipmentTypes
import com.witcher.thewitcherrpg.feature_character_sheet.domain.item_types.WeaponTypes
import com.witcher.thewitcherrpg.feature_character_sheet.domain.models.*


class EquipmentListAdapter(val itemClick: (Equipment) -> Unit, isDarkMode: Boolean) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var itemList = emptyList<Any>()
    private lateinit var currentItem: Any
    private val relicColor = if (isDarkMode) ContextCompat.getColor(TheWitcherTRPGApp.getContext()!!, R.color.relic)
    else ContextCompat.getColor(TheWitcherTRPGApp.getContext()!!, R.color.dark_relic)

    inner class WeaponViewHolder(private val binding: ArmorRowBinding) : RecyclerView.ViewHolder(binding.root) {

        private val defaultColor: ColorStateList = binding.armorNameText.textColors

        fun bind(item: WeaponItem) {

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
                        relicColor
                    )
                    costText.setTextColor(
                        relicColor
                    )
                    stoppingPowerText.setTextColor(
                        relicColor
                    )
                } else {
                    armorNameText.setTextColor(
                        defaultColor
                    )
                    costText.setTextColor(
                        defaultColor
                    )
                    stoppingPowerText.setTextColor(
                        defaultColor
                    )
                }

                rowLayout.setOnClickListener {
                    itemClick(item)

                }
            }

        }
    }

    inner class ArmorItemViewHolder(private val binding: ArmorRowBinding) : RecyclerView.ViewHolder(binding.root) {

        private val defaultColor: ColorStateList = binding.armorNameText.textColors

        fun bind(item: EquipmentItem) {

            if (item.equipmentType != EquipmentTypes.MISC_CUSTOM) {
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
                    } else {
                        armorNameText.setTextColor(
                            defaultColor
                        )
                        costText.setTextColor(
                            defaultColor
                        )
                        stoppingPowerText.setTextColor(
                            defaultColor
                        )
                    }

                    rowLayout.setOnClickListener {
                        itemClick(item)

                    }
                }
            } else {
                val weight = "Quantity: " + item.quantity.toString()
                val stoppingPower = "Cost: " + item.cost.toString()

                with(binding) {
                    armorNameText.text = item.name
                    costText.text = weight
                    stoppingPowerText.text = stoppingPower

                    rowLayout.setOnClickListener {
                        itemClick(item)

                    }
                }
            }
        }

        fun bind(item: ArmorSet) {
            val weight = "Cost: " + item.cost.toString()
            val stoppingPower = "Stopping Power: " + item.stoppingPower.toString()

            with(binding) {
                armorNameText.text = item.name
                costText.text = weight
                stoppingPowerText.text = stoppingPower

                if (item.isRelic) {
                    armorNameText.setTextColor(
                        relicColor
                    )
                    costText.setTextColor(
                        relicColor
                    )
                    stoppingPowerText.setTextColor(
                        relicColor
                    )
                } else {
                    armorNameText.setTextColor(
                        defaultColor
                    )
                    costText.setTextColor(
                        defaultColor
                    )
                    stoppingPowerText.setTextColor(
                        defaultColor
                    )
                }

                rowLayout.setOnClickListener {
                    itemClick(item)

                }
            }
        }
    }

    inner class HeaderViewHolder(private val binding: MagicHeaderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ListHeader) {
            with(binding){
                headerText.text = item.header
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            TYPE_WEAPON -> WeaponViewHolder(ArmorRowBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            TYPE_ARMOR_ITEM -> ArmorItemViewHolder(ArmorRowBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            TYPE_ARMOR_SET -> ArmorItemViewHolder(ArmorRowBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            TYPE_HEADER -> HeaderViewHolder(MagicHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> WeaponViewHolder(ArmorRowBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder){
            is WeaponViewHolder -> {
                currentItem = itemList[position]
                holder.bind(currentItem as WeaponItem)
            }
            is ArmorItemViewHolder -> {
                currentItem = itemList[position]
                if (currentItem is EquipmentItem) {
                    holder.bind(currentItem as EquipmentItem)
                } else if (currentItem is ArmorSet) {
                    holder.bind(currentItem as ArmorSet)
                }
            }
            is HeaderViewHolder -> {
                currentItem = itemList[position]
                holder.bind(currentItem as ListHeader)
            }
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun getItemViewType(position: Int): Int {
        return when(itemList[position]){
            is ListHeader -> TYPE_HEADER
            is WeaponItem -> TYPE_WEAPON
            is EquipmentItem -> TYPE_ARMOR_ITEM
            is ArmorSet -> TYPE_ARMOR_ITEM
            else -> TYPE_UNKNOWN
        }
    }

    fun setData(items: List<Any>) {
        this.itemList = items
        notifyDataSetChanged()
    }

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_WEAPON = 1
        private const val TYPE_ARMOR_ITEM = 2
        private const val TYPE_ARMOR_SET = 3
        private const val TYPE_UNKNOWN = 4
    }

}