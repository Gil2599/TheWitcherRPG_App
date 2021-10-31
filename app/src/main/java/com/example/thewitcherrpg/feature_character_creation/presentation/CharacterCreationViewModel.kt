package com.example.thewitcherrpg.feature_character_creation.presentation

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thewitcherrpg.core.Constants
import com.example.thewitcherrpg.feature_character_creation.domain.use_cases.AddCharacterUseCase
import com.example.thewitcherrpg.feature_character_creation.domain.use_cases.GetDefiningSkillInfoUseCase
import com.example.thewitcherrpg.feature_character_creation.domain.use_cases.GetDefiningSkillUseCase
import com.example.thewitcherrpg.feature_character_creation.domain.use_cases.GetRacePerkUseCase
import com.example.thewitcherrpg.feature_character_list.domain.model.Character
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CharacterCreationViewModel @Inject constructor(
    private val addCharacterUseCase: AddCharacterUseCase,
    private val getDefiningSkillUseCase: GetDefiningSkillUseCase,
    private val getRacePerkUseCase: GetRacePerkUseCase,
    private val getDefiningSkillInfoUseCase: GetDefiningSkillInfoUseCase
): ViewModel() {

    private val _name = mutableStateOf("")
    val name: State<String> = _name

    private val _age = mutableStateOf("")
    val age: State<String> = _age

    private val _gender = mutableStateOf("")
    val gender: State<String> = _gender

    private val _race = mutableStateOf("")
    val race: State<String> = _race

    private val _profession = mutableStateOf(Constants.Professions.BARD)
    val profession: State<Constants.Professions> = _profession

    private val _definingSkill = MutableStateFlow("")
    val definingSkill = _definingSkill.asStateFlow()

    private val _definingSkillInfo = MutableStateFlow("")
    val definingSkillInfo = _definingSkillInfo.asStateFlow()

    private val _perks = MutableStateFlow("")
    val perks = _perks.asStateFlow()

    fun addCharacter(character: Character){
        addCharacterUseCase.invoke(character)
    }

    fun setAge(age: String){
        _age.value = age
    }

    fun setName(name: String){
        _name.value = name
    }

    fun setRace(race: String){
        _race.value = race

        getRacePerkUseCase(race).onEach {
            _perks.value = it
        }.launchIn(viewModelScope)

    }

    fun setGender(gender: String){
        _gender.value = gender
    }

    fun setProfession(profession: String){

        _profession.value = when (profession){

            "Bard" -> Constants.Professions.BARD
            "Criminal" -> Constants.Professions.CRIMINAL
            "Craftsman" -> Constants.Professions.CRAFTSMAN
            "Doctor" -> Constants.Professions.DOCTOR
            "Mage" -> Constants.Professions.MAGE
            "Man At Arms" -> Constants.Professions.MAN_AT_ARMS
            "Priest" -> Constants.Professions.PRIEST
            "Witcher" -> Constants.Professions.WITCHER
            "Merchant" -> Constants.Professions.MERCHANT
            "Noble" -> Constants.Professions.NOBLE
            "Peasant" -> Constants.Professions.PEASANT

            else -> Constants.Professions.PEASANT
        }

        getDefiningSkillUseCase(profession).onEach { defSkill ->
            _definingSkill.value = defSkill
        }.launchIn(viewModelScope)

        getDefiningSkillInfoUseCase(definingSkill.value).onEach { info ->
            Log.d("Test", info)
            _definingSkillInfo.value = info
        }.launchIn(viewModelScope)

        /*getDefiningSkillInfoUseCase(definingSkill.value).onEach {
            Log.d("Test", it)
            _definingSkillInfo.value = it
        }.launchIn(viewModelScope)*/
    }
}