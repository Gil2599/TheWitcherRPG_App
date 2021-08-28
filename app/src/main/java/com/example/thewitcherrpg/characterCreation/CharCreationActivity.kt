package com.example.thewitcherrpg.characterCreation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.thewitcherrpg.R
import com.example.thewitcherrpg.characterSheet.SharedViewModel
import com.example.thewitcherrpg.data.CharacterViewModel



class CharCreationActivity : AppCompatActivity() {

    private lateinit var mCharViewModel: CharacterViewModel
    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_char_creation)

        sharedViewModel.inCharacterCreation = true
        mCharViewModel = ViewModelProvider(this).get(CharacterViewModel::class.java)

    }

    fun onSaveFinal(){

        val finalCharacter = sharedViewModel.onSaveFinal()
        mCharViewModel.addChar(finalCharacter)

        finish()
    }
}