package com.example.thewitcherrpg.feature_character_list.presentation

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.thewitcherrpg.core.domain.model.Character
import com.example.thewitcherrpg.databinding.CustomRowBinding
import com.example.thewitcherrpg.feature_character_sheet.presentation.MainActivity

class ListAdapter(con: Context): RecyclerView.Adapter<ListAdapter.CharViewHolder>() {

    private var charList = emptyList<Character>()
    private var context: Context = con

    class CharViewHolder(private val binding: CustomRowBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(name: String, id: Int, ip: String, character: Character, context: Context){

            with (binding){
                nameText.text = name
                //professionText.text = profession
                idText.text = id.toString()
                IPText.text = ip

                rowLayout.setOnClickListener{
                    val intent = Intent(context, MainActivity::class.java).also {
                        it.putExtra("CHARACTER_ID", id)
                    }
                    context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharViewHolder {
        val itemBinding = CustomRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CharViewHolder, position: Int) {
        val currentItem = charList[position]

        holder.bind(currentItem.name, currentItem.id,
            "IP: " + currentItem.iP, currentItem, context)

    }

    override fun getItemCount(): Int {
        return charList.size
    }

    fun setData(character: List<Character>){
        this.charList = character
        notifyDataSetChanged()
    }

}