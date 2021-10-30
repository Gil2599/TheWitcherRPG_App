package com.example.thewitcherrpg.feature_character_sheet.presentation.magic.invocationListAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.thewitcherrpg.R
import com.example.thewitcherrpg.databinding.SpellRowBinding

class NoviceDruidInvocationListAdapter(con: Context, val itemClick: (String) -> Unit) : RecyclerView.Adapter<NoviceDruidInvocationListAdapter.DruidViewHolder>()  {

    private var spellList = emptyList<String>()
    private var addSpell: Boolean = false
    private lateinit var currentItem: String
    private var context: Context = con

    inner class DruidViewHolder(private val binding: SpellRowBinding): RecyclerView.ViewHolder(binding.root) {
        fun addBind(sign: String, position: Int){
            val pair = sign.split(":").toTypedArray()
            val spellName = pair[0]
            val staCost = "STA Cost: " + pair[1]
            val description = pair[2]
            val range = "Range: " + pair[3]
            val duration = pair[4]
            val defense = pair[5]

            with (binding) {
                spellNameText.text = spellName
                staCostText.text = staCost
                rangeText.text = range
                rowLayout.setOnClickListener(){
                    itemClick(spellList[position])
                }
            }
        }

        fun bind(sign: String, name: String, staCost: String, range: String){
            with (binding){
                spellNameText.text = name
                staCostText.text = staCost
                rangeText.text = range

                rowLayout.setOnClickListener(){
                    itemClick(sign)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DruidViewHolder {
        val itemBinding = SpellRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DruidViewHolder(itemBinding)    }

    override fun onBindViewHolder(holder: DruidViewHolder, position: Int) {
        currentItem = spellList[position]

        //Check whether to show all spells or only character spells
        if (addSpell){
            holder.addBind(currentItem, position)
        }
        else{
            val tags = context.resources.getStringArray(R.array.novice_druidInvo_list_data)
            for (tag in tags) {

                val pair = tag.split(":").toTypedArray()
                val spellName = pair[0]
                val staCost = "STA Cost: " + pair[1]
                val description = pair[2]
                val range = "Range: " + pair[3]
                val duration = pair[4]
                val defense = pair[5]

                if (currentItem == spellName) {
                    holder.bind(tag, spellName, staCost, range)
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return spellList.size
    }

    fun setData(spell: List<String>){
        this.spellList = spell
        notifyDataSetChanged()
    }

    fun setAddSpell(value: Boolean){
        addSpell= value
    }
}

class JourneymanDruidInvocationListAdapter(con: Context, val itemClick: (String) -> Unit) : RecyclerView.Adapter<JourneymanDruidInvocationListAdapter.DruidViewHolder>()  {

    private var spellList = emptyList<String>()
    private var addSpell: Boolean = false
    private lateinit var currentItem: String
    private var context: Context = con

    inner class DruidViewHolder(private val binding: SpellRowBinding): RecyclerView.ViewHolder(binding.root) {
        fun addBind(sign: String, position: Int){
            val pair = sign.split(":").toTypedArray()
            val spellName = pair[0]
            val staCost = "STA Cost: " + pair[1]
            val description = pair[2]
            val range = "Range: " + pair[3]
            val duration = pair[4]
            val defense = pair[5]

            with (binding) {
                spellNameText.text = spellName
                staCostText.text = staCost
                rangeText.text = range
                rowLayout.setOnClickListener(){
                    itemClick(spellList[position])
                }
            }
        }

        fun bind(sign: String, name: String, staCost: String, range: String){
            with (binding){
                spellNameText.text = name
                staCostText.text = staCost
                rangeText.text = range

                rowLayout.setOnClickListener(){
                    itemClick(sign)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DruidViewHolder {
        val itemBinding = SpellRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DruidViewHolder(itemBinding)    }

    override fun onBindViewHolder(holder: DruidViewHolder, position: Int) {
        currentItem = spellList[position]

        //Check whether to show all spells or only character spells
        if (addSpell){
            holder.addBind(currentItem, position)
        }
        else{
            val tags = context.resources.getStringArray(R.array.journeyman_druidInvo_list_data)
            for (tag in tags) {

                val pair = tag.split(":").toTypedArray()
                val spellName = pair[0]
                val staCost = "STA Cost: " + pair[1]
                val description = pair[2]
                val range = "Range: " + pair[3]
                val duration = pair[4]
                val defense = pair[5]

                if (currentItem == spellName) {
                    holder.bind(tag, spellName, staCost, range)
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return spellList.size
    }

    fun setData(spell: List<String>){
        this.spellList = spell
        notifyDataSetChanged()
    }

    fun setAddSpell(value: Boolean){
        addSpell= value
    }
}

class MasterDruidInvocationListAdapter(con: Context, val itemClick: (String) -> Unit) : RecyclerView.Adapter<MasterDruidInvocationListAdapter.DruidViewHolder>()  {

    private var spellList = emptyList<String>()
    private var addSpell: Boolean = false
    private lateinit var currentItem: String
    private var context: Context = con

    inner class DruidViewHolder(private val binding: SpellRowBinding): RecyclerView.ViewHolder(binding.root) {
        fun addBind(sign: String, position: Int){
            val pair = sign.split(":").toTypedArray()
            val spellName = pair[0]
            val staCost = "STA Cost: " + pair[1]
            val description = pair[2]
            val range = "Range: " + pair[3]
            val duration = pair[4]
            val defense = pair[5]

            with (binding) {
                spellNameText.text = spellName
                staCostText.text = staCost
                rangeText.text = range
                rowLayout.setOnClickListener(){
                    itemClick(spellList[position])
                }
            }
        }

        fun bind(sign: String, name: String, staCost: String, range: String){
            with (binding){
                spellNameText.text = name
                staCostText.text = staCost
                rangeText.text = range

                rowLayout.setOnClickListener(){
                    itemClick(sign)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DruidViewHolder {
        val itemBinding = SpellRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DruidViewHolder(itemBinding)    }

    override fun onBindViewHolder(holder: DruidViewHolder, position: Int) {
        currentItem = spellList[position]

        //Check whether to show all spells or only character spells
        if (addSpell){
            holder.addBind(currentItem, position)
        }
        else{
            val tags = context.resources.getStringArray(R.array.master_druidInvo_data)
            for (tag in tags) {

                val pair = tag.split(":").toTypedArray()
                val spellName = pair[0]
                val staCost = "STA Cost: " + pair[1]
                val description = pair[2]
                val range = "Range: " + pair[3]
                val duration = pair[4]
                val defense = pair[5]

                if (currentItem == spellName) {
                    holder.bind(tag, spellName, staCost, range)
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return spellList.size
    }

    fun setData(spell: List<String>){
        this.spellList = spell
        notifyDataSetChanged()
    }

    fun setAddSpell(value: Boolean){
        addSpell= value
    }
}