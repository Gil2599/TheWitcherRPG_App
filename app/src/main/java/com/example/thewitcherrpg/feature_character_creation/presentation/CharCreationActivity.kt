package com.example.thewitcherrpg.feature_character_creation.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.thewitcherrpg.R
import com.example.thewitcherrpg.TheWitcherTRPGApp
import com.example.thewitcherrpg.feature_character_sheet.SharedViewModel
import com.example.thewitcherrpg.feature_character_list.presentation.CharacterListViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CharCreationActivity : AppCompatActivity() {

    private val characterCreationViewModel: CharacterCreationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_char_creation)

    }

    fun onSaveFinal(){

        characterCreationViewModel.addCharacter()
        finish()
    }
}