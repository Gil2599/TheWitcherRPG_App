package com.example.thewitcherrpg.feature_character_creation.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.thewitcherrpg.R
import com.example.thewitcherrpg.feature_character_sheet.SharedViewModel
import com.example.thewitcherrpg.feature_character_list.presentation.CharacterListViewModel



class CharCreationActivity : AppCompatActivity() {

    private lateinit var mCharListViewModel: CharacterListViewModel
    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_char_creation)

        sharedViewModel.inCharacterCreation = true
        mCharListViewModel = ViewModelProvider(this)[CharacterListViewModel::class.java]

    }

    fun onSaveFinal(){

        val finalCharacter = sharedViewModel.onSaveFinal()
        mCharListViewModel.addChar(finalCharacter)

        finish()
    }
}