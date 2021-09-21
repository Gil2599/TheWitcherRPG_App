package com.example.thewitcherrpg

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.thewitcherrpg.characterSheet.MainActivity
import com.example.thewitcherrpg.data.Character
import com.example.thewitcherrpg.databinding.CustomRowBinding
import com.example.thewitcherrpg.databinding.SpellRowBinding

class ListAdapter(con: Context): RecyclerView.Adapter<ListAdapter.CharViewHolder>() {

    private var charList = emptyList<Character>()
    private var context: Context = con

    class CharViewHolder(private val binding: CustomRowBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(name: String, profession: String, id: String, ip: String, character: Character, context: Context){

            with (binding){
                nameText.text = name
                professionText.text = profession
                idText.text = id
                IPText.text = ip

                rowLayout.setOnClickListener(){
                    val intent = Intent(context, MainActivity::class.java).also {
                        it.putExtra("EXTRA_DATA", character)
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

        holder.bind(currentItem.name, currentItem.profession, currentItem.id.toString(),
            "IP: " + currentItem.iP, currentItem, context)

        /*holder.itemView.name_text.text = currentItem.name
        holder.itemView.race_text.text = currentItem.profession
        holder.itemView.id_text.text = currentItem.id.toString()

        val ip = "IP: " + currentItem.iP
        holder.itemView.IP_text.text = ip

        holder.itemView.rowLayout.setOnClickListener{
            val intent = Intent(context, MainActivity::class.java).also {
                it.putExtra("EXTRA_DATA", currentItem)
            }
            context.startActivity(intent)
        }*/

    }

    override fun getItemCount(): Int {
        return charList.size
    }

    fun setData(character: List<Character>){
        this.charList = character
        notifyDataSetChanged()
    }

}