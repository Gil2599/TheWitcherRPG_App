package com.witcher.thewitcherrpg.feature_character_sheet.presentation.character_information.child_fragments.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.witcher.thewitcherrpg.databinding.ArmorRowBinding
import com.witcher.thewitcherrpg.feature_character_sheet.domain.models.LifeEvent

class LifeEventsListAdapter(
    val itemClickString: (LifeEvent) -> Unit
) : RecyclerView.Adapter<LifeEventsListAdapter.ViewHolder>() {

    private var eventList = emptyList<LifeEvent>()
    private lateinit var currentItem: LifeEvent

    inner class ViewHolder(private val binding: ArmorRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: LifeEvent, position: Int) {

            val age = "Age: " + item.age.toString()

            with(binding) {
                armorNameText.text = item.title
                stoppingPowerText.text = age

                rowLayout.setOnClickListener {
                    itemClickString(eventList[position])
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
        currentItem = eventList[position]
        holder.bind(currentItem, position)
    }

    override fun getItemCount(): Int {
        return eventList.size
    }

    fun setData(itemList: ArrayList<LifeEvent>) {
        this.eventList = itemList
        notifyDataSetChanged()
    }

}