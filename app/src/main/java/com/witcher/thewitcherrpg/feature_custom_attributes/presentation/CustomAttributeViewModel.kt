package com.witcher.thewitcherrpg.feature_custom_attributes.presentation

import androidx.compose.material.SnackbarDuration
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.witcher.thewitcherrpg.core.Resource
import com.witcher.thewitcherrpg.core.domain.model.CustomMagic
import com.witcher.thewitcherrpg.feature_character_sheet.domain.item_types.MagicType
import com.witcher.thewitcherrpg.feature_character_sheet.domain.models.MagicItem
import com.witcher.thewitcherrpg.feature_custom_attributes.domain.AddCustomMagicUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CustomAttributeViewModel @Inject constructor(
    private val addCustomMagicUseCase: AddCustomMagicUseCase
) : ViewModel() {

    val optionsMagicGeneralTypeMap = mapOf(
        "Spell" to MagicGeneralType.SPELL,
        "Invocation" to MagicGeneralType.INVOCATION,
        "Ritual" to MagicGeneralType.RITUAL,
        "Hex" to MagicGeneralType.HEX,
        "Sign" to MagicGeneralType.SIGN
    )
    private val optionsSpellsMap = mapOf(
        "Novice" to MagicLevel.NOVICE,
        "Journeyman" to MagicLevel.JOURNEYMAN,
        "Master" to MagicLevel.MASTER
    )
    private val optionsSignsMap = mapOf(
        "Basic" to MagicLevel.BASIC,
        "Alternate" to MagicLevel.ALTERNATE
    )
    private val optionsInvocationsMap = mapOf(
        "Novice Druid" to MagicLevel.NOVICE_DRUID,
        "Journeyman Druid" to MagicLevel.JOURNEYMAN_DRUID,
        "Master Druid" to MagicLevel.MASTER_DRUID,
        "Novice Preacher" to MagicLevel.NOVICE_PREACHER,
        "Journeyman Preacher" to MagicLevel.JOURNEYMAN_PREACHER,
        "Master Preacher" to MagicLevel.MASTER_PREACHER,
        "Arch Priest" to MagicLevel.ARCH_PRIEST,
    )
    private val optionsHexesMap = mapOf(
        "Low" to MagicLevel.LOW,
        "Medium" to MagicLevel.MEDIUM,
        "High" to MagicLevel.HIGH
    )
    val optionsElementsMap = mapOf(
        "Air" to Element.AIR,
        "Earth" to Element.EARTH,
        "Fire" to Element.FIRE,
        "Water" to Element.WATER,
        "Mixed Element" to Element.MIXED_ELEMENT
    )

    private val _currentMagicSelection = MutableLiveData(MagicGeneralType.SPELL)
    private val currentMagicSelection: LiveData<MagicGeneralType> = _currentMagicSelection

    private val _addMagicState = MutableLiveData<Resource<Boolean>>()
    val addMagicState: LiveData<Resource<Boolean>> = _addMagicState

    fun getMagicLevelOptions(magicGeneralType: MagicGeneralType): List<String> {
        return when (magicGeneralType) {
            MagicGeneralType.SPELL, MagicGeneralType.RITUAL -> optionsSpellsMap.keys.toList()
            MagicGeneralType.INVOCATION -> optionsInvocationsMap.keys.toList()
            MagicGeneralType.HEX -> optionsHexesMap.keys.toList()
            MagicGeneralType.SIGN -> optionsSignsMap.keys.toList()
        }
    }

    fun getMagicLevelFromSelection(selection: String): MagicLevel? {
        return when (currentMagicSelection.value) {
            MagicGeneralType.SPELL, MagicGeneralType.RITUAL -> {
                optionsSpellsMap[selection]
            }
            MagicGeneralType.INVOCATION -> {
                optionsInvocationsMap[selection]
            }
            MagicGeneralType.SIGN -> {
                optionsSignsMap[selection]
            }
            MagicGeneralType.HEX -> {
                optionsHexesMap[selection]
            }
            else -> {
                optionsSpellsMap["Novice"]
            }
        }
    }

    fun getMagicTypeFromSelection(selection: String) : MagicGeneralType? {
        _currentMagicSelection.value = optionsMagicGeneralTypeMap[selection]
        return optionsMagicGeneralTypeMap[selection]
    }

    fun getElementFromSelection(selection: String) : Element? {
        return optionsElementsMap[selection]
    }

    fun getMagicLevelSelection(typeSelection: MagicGeneralType, currentMagicLevel: MagicLevel) : String {
        when (typeSelection){
            MagicGeneralType.SPELL, MagicGeneralType.RITUAL -> {
                return if (currentMagicLevel !in optionsSpellsMap.values){
                    optionsSpellsMap.keys.first()
                } else optionsSpellsMap.filterValues { it == currentMagicLevel }.keys.first()
            }
            MagicGeneralType.INVOCATION -> {
                return if (currentMagicLevel !in optionsInvocationsMap.values){
                    optionsInvocationsMap.keys.first()
                } else optionsInvocationsMap.filterValues { it == currentMagicLevel }.keys.first()
            }
            MagicGeneralType.HEX -> {
                return if (currentMagicLevel !in optionsHexesMap.values){
                    optionsHexesMap.keys.first()
                } else optionsHexesMap.filterValues { it == currentMagicLevel }.keys.first()
            }
            MagicGeneralType.SIGN -> {
                return if (currentMagicLevel !in optionsSignsMap.values){
                    optionsSignsMap.keys.first()
                } else optionsSignsMap.filterValues { it == currentMagicLevel }.keys.first()
            }
        }
    }

    fun getMagicType(typeSelection: MagicGeneralType, levelSelection: MagicLevel) : MagicType {
        return when (typeSelection){
            MagicGeneralType.SPELL -> {
                when (levelSelection) {
                    MagicLevel.NOVICE -> MagicType.NOVICE_SPELL
                    MagicLevel.JOURNEYMAN -> MagicType.JOURNEYMAN_SPELL
                    MagicLevel.MASTER -> MagicType.MASTER_SPELL
                    else -> {
                        MagicType.NOVICE_SPELL
                    }
                }
            }
            MagicGeneralType.INVOCATION -> {
                when (levelSelection) {
                    MagicLevel.NOVICE_DRUID -> MagicType.NOVICE_DRUID_INVOCATION
                    MagicLevel.JOURNEYMAN_DRUID -> MagicType.JOURNEYMAN_DRUID_INVOCATION
                    MagicLevel.MASTER_DRUID -> MagicType.MASTER_DRUID_INVOCATION
                    MagicLevel.NOVICE_PREACHER -> MagicType.NOVICE_PREACHER_INVOCATION
                    MagicLevel.JOURNEYMAN_PREACHER -> MagicType.JOURNEYMAN_PREACHER_INVOCATION
                    MagicLevel.MASTER_PREACHER -> MagicType.MASTER_PREACHER_INVOCATION
                    MagicLevel.ARCH_PRIEST -> MagicType.ARCH_PRIEST_INVOCATION
                    else -> {
                        MagicType.NOVICE_DRUID_INVOCATION
                    }
                }
            }
            MagicGeneralType.RITUAL -> {
                when (levelSelection) {
                    MagicLevel.NOVICE -> MagicType.NOVICE_RITUAL
                    MagicLevel.JOURNEYMAN -> MagicType.JOURNEYMAN_RITUAL
                    MagicLevel.MASTER -> MagicType.MASTER_RITUAL
                    else -> {
                        MagicType.NOVICE_RITUAL
                    }
                }
            }
            MagicGeneralType.HEX -> {
                when (levelSelection) {
                    MagicLevel.LOW -> MagicType.HEX
                    MagicLevel.MEDIUM -> MagicType.HEX
                    MagicLevel.HIGH -> MagicType.HEX
                    else -> {
                        MagicType.HEX
                    }
                }
            }
            MagicGeneralType.SIGN -> {
                when (levelSelection) {
                    MagicLevel.BASIC -> MagicType.BASIC_SIGN
                    MagicLevel.ALTERNATE -> MagicType.ALTERNATE_SIGN
                    else -> {
                        MagicType.BASIC_SIGN
                    }
                }
            }
        }
    }

    fun isMagicValid(
        type: MagicGeneralType,
        magicLevel: MagicLevel,
        name: String,
        staminaCost: Int,
        description: String,
        range: String,
        duration: String,
        defenseOrPrepTime: String,
        element: Element,
        difficulty: Int,
        componentsOrReqToLift: String,
        isSelfRange: Boolean,
        isVariableDiff: Boolean,
        isVariableSta: Boolean
    ) : Boolean {

        if (name.isBlank() || description.isBlank()){
            return false
        }

        when (type){
            MagicGeneralType.SPELL, MagicGeneralType.INVOCATION, MagicGeneralType.SIGN -> {
                if (defenseOrPrepTime.isBlank() || duration.isBlank()) return false
            }
            MagicGeneralType.RITUAL -> {
                if (componentsOrReqToLift.isBlank() || duration.isBlank() || defenseOrPrepTime.isBlank()) return false
            }
            MagicGeneralType.HEX -> {
                if (componentsOrReqToLift.isBlank()) return false
            }
        }
        val rangeFinal = if (isSelfRange && range.isBlank()) "Self" else "Self or " + range + "m"
        val difficultyFinal = if (isVariableDiff) null else difficulty
        val staminaFinal = if (isVariableSta) null else staminaCost

        val magicItem = MagicItem(
            type = getMagicType(type, magicLevel),
            name = name,
            staminaCost = staminaFinal,
            description = description,
            range = rangeFinal,
            duration = duration,
            defense = defenseOrPrepTime,
            element = optionsElementsMap.filterValues { it == element }.keys.first(),
            difficulty = difficultyFinal,
            preparation = defenseOrPrepTime,
            components = componentsOrReqToLift,
            requirementToLift = componentsOrReqToLift,
            danger = getDangerLevel(magicLevel),
            isCustom = true
        )

        addCustomMagic(magicItem)
        return true
    }

    private fun getDangerLevel(magicLevel: MagicLevel): String{
        return when (magicLevel) {
            MagicLevel.LOW -> "Low"
            MagicLevel.MEDIUM -> "Medium"
            MagicLevel.HIGH -> "High"
            else -> {"Low"}
        }
    }

    private fun addCustomMagic(customMagic: MagicItem) {
        addCustomMagicUseCase(CustomMagic(customMagic)).onEach { result ->
            when (result){
                is Resource.Success -> _addMagicState.value = Resource.Success(true)
                is Resource.Error -> _addMagicState.value = result.message?.let { Resource.Error(it) }
                else -> {}
            }
        }.launchIn(viewModelScope)
    }
}