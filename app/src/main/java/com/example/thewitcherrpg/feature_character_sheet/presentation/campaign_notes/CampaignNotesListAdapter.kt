package com.example.thewitcherrpg.feature_character_sheet.presentation.campaign_notes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.thewitcherrpg.databinding.ArmorRowBinding
import com.example.thewitcherrpg.feature_character_sheet.domain.models.CampaignNote

class CampaignNotesListAdapter(
    val itemClickString: (CampaignNote) -> Unit
) : RecyclerView.Adapter<CampaignNotesListAdapter.ViewHolder>() {

    private var noteList = emptyList<CampaignNote>()
    private lateinit var currentItem: CampaignNote

    inner class ViewHolder(private val binding: ArmorRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CampaignNote, position: Int) {

            val date = "Date Added: " + item.date

            with(binding) {
                armorNameText.text = item.title
                stoppingPowerText.text = date

                rowLayout.setOnClickListener {
                    itemClickString(noteList[position])
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
        currentItem = noteList[position]
        holder.bind(currentItem, position)
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    fun setData(itemList: ArrayList<CampaignNote>) {
        this.noteList = itemList
        notifyDataSetChanged()
    }

}