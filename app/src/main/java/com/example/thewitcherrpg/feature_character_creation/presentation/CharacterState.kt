package com.example.thewitcherrpg.feature_character_creation.presentation

data class CharacterState (
    val isLoading: Boolean = false,
    val success: Boolean = false,
    val error: String = ""
)