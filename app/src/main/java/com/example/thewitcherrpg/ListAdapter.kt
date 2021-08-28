package com.example.thewitcherrpg

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.thewitcherrpg.characterSheet.MainActivity
import com.example.thewitcherrpg.data.Character
import kotlinx.android.synthetic.main.custom_row.view.*

class ListAdapter(con: Context): RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var charList = emptyList<Character>()
    private var context: Context = con

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row, parent, false))

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = charList[position]

        holder.itemView.name_text.text = currentItem.name
        holder.itemView.race_text.text = currentItem.race
        holder.itemView.id_text.text = currentItem.id.toString()

        val ip = "IP: " + currentItem.iP
        holder.itemView.IP_text.text = ip

        holder.itemView.rowLayout.setOnClickListener{
            val intent = Intent(context, MainActivity::class.java).also {
                it.putExtra("EXTRA_DATA", currentItem)
            }
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return charList.size
    }

    fun setData(character: List<Character>){
        this.charList = character
        notifyDataSetChanged()
    }

}