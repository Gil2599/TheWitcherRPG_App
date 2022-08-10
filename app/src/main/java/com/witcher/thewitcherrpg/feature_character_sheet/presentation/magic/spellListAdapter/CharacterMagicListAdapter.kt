package com.witcher.thewitcherrpg.feature_character_sheet.presentation.magic.spellListAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.witcher.thewitcherrpg.databinding.MagicHeaderBinding
import com.witcher.thewitcherrpg.databinding.SpellRowBinding
import com.witcher.thewitcherrpg.feature_character_sheet.domain.item_types.MagicType
import com.witcher.thewitcherrpg.feature_character_sheet.domain.models.MagicHeader
import com.witcher.thewitcherrpg.feature_character_sheet.domain.models.MagicItem

class CharacterMagicListAdapter(val itemClick: (MagicItem) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var itemList = emptyList<Any>()
    private lateinit var currentItem: Any

    inner class SpellViewHolder(private val binding: SpellRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MagicItem) {
            when (item.type) {
                MagicType.NOVICE_SPELL,
                MagicType.JOURNEYMAN_SPELL,
                MagicType.MASTER_SPELL,
                MagicType.BASIC_SIGN,
                MagicType.ALTERNATE_SIGN,
                MagicType.NOVICE_DRUID_INVOCATION,
                MagicType.JOURNEYMAN_DRUID_INVOCATION,
                MagicType.MASTER_DRUID_INVOCATION,
                MagicType.NOVICE_PREACHER_INVOCATION,
                MagicType.JOURNEYMAN_PREACHER_INVOCATION,
                MagicType.MASTER_PREACHER_INVOCATION,
                MagicType.ARCH_PRIEST_INVOCATION -> {
                    val staCost =
                        "STA Cost: " + if (item.staminaCost != null) item.staminaCost.toString() else "Variable"
                    val range = "Range: " + item.range

                    with(binding) {
                        spellNameText.text = item.name
                        staCostText.text = staCost
                        elementText.text = item.element
                        rangeText.text = range
                        rowLayout.setOnClickListener {
                            itemClick(itemList[position] as MagicItem)
                        }
                    }
                }
                MagicType.NOVICE_RITUAL,
                MagicType.JOURNEYMAN_RITUAL,
                MagicType.MASTER_RITUAL -> {
                    val staCost =
                        "STA Cost: " + if (item.staminaCost != null) item.staminaCost.toString() else "Variable"
                    val difficulty =
                        "Difficulty: " + if (item.difficulty != null) item.difficulty.toString() else "Variable"

                    with(binding) {
                        spellNameText.text = item.name
                        staCostText.text = staCost
                        rangeText.text = difficulty
                        rowLayout.setOnClickListener {
                            itemClick(itemList[position] as MagicItem)
                        }
                    }
                }
                MagicType.HEX -> {
                    val staCost = "STA Cost: " + item.staminaCost
                    val danger = "Danger: " + item.danger

                    with(binding) {
                        spellNameText.text = item.name
                        staCostText.text = staCost
                        rangeText.text = danger
                        rowLayout.setOnClickListener() {
                            itemClick(itemList[position] as MagicItem)
                        }
                    }
                }
            }
        }
    }

    inner class HeaderViewHolder(private val binding: MagicHeaderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MagicHeader) {
            with(binding){
                headerText.text = item.header
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            TYPE_SPELL -> SpellViewHolder(SpellRowBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            TYPE_HEADER -> HeaderViewHolder(MagicHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> SpellViewHolder(SpellRowBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder){
            is SpellViewHolder -> {
                currentItem = itemList[position]
                holder.bind(currentItem as MagicItem)
            }
            is HeaderViewHolder -> {
                currentItem = itemList[position]
                holder.bind(currentItem as MagicHeader)
            }
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun getItemViewType(position: Int): Int {
        return when(itemList[position]){
            is MagicHeader -> TYPE_HEADER
            is MagicItem -> TYPE_SPELL
            else -> TYPE_UNKNOWN
        }
    }

    fun setData(items: List<Any>) {
        this.itemList = items
        notifyDataSetChanged()
    }

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_SPELL = 1
        private const val TYPE_UNKNOWN = 2
    }

}