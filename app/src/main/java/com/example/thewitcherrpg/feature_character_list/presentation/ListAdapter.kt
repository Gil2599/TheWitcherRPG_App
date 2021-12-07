package com.example.thewitcherrpg.feature_character_list.presentation

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.thewitcherrpg.core.domain.model.Character
import com.example.thewitcherrpg.databinding.CustomRowBinding
import com.example.thewitcherrpg.feature_character_sheet.presentation.MainActivity
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException

class ListAdapter(con: Context): RecyclerView.Adapter<ListAdapter.CharViewHolder>() {

    private var charList = emptyList<Character>()
    private var context: Context = con

    class CharViewHolder(private val binding: CustomRowBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(character: Character, context: Context){

            with (binding){
                val ip = "IP: " + character.iP.toString()

                nameText.text = character.name
                professionText.text = character.profession.toString()
                IPText.text = ip
                textRace.text = character.race

                if (character.imagePath.isNotEmpty()) loadImageFromStorage(character.imagePath, imageView)
                else imageView.setImageDrawable(null)

                rowLayout.setOnClickListener{
                    val intent = Intent(context, MainActivity::class.java).also {
                        it.putExtra("CHARACTER_ID", character.id)
                    }
                    context.startActivity(intent)
                }
            }
        }

        private fun loadImageFromStorage(path: String, imageView: ImageView) {
            try {
                val f = File(path)
                val b = BitmapFactory.decodeStream(FileInputStream(f))
                imageView.setImageBitmap(b)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharViewHolder {
        val itemBinding = CustomRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CharViewHolder, position: Int) {
        val currentItem = charList[position]
        holder.bind(currentItem, context)
    }

    override fun getItemCount(): Int {
        return charList.size
    }

    fun setData(character: List<Character>){
        this.charList = character
        notifyDataSetChanged()
    }
}