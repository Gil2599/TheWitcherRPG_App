package com.witcher.thewitcherrpg.feature_custom_attributes.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.witcher.thewitcherrpg.core.Resource
import com.witcher.thewitcherrpg.core.domain.model.CustomEquipment
import com.witcher.thewitcherrpg.core.domain.model.CustomMagic
import com.witcher.thewitcherrpg.core.domain.model.CustomWeapon
import com.witcher.thewitcherrpg.feature_character_sheet.domain.item_types.EquipmentTypes
import com.witcher.thewitcherrpg.feature_character_sheet.domain.item_types.MagicType
import com.witcher.thewitcherrpg.feature_character_sheet.domain.item_types.WeaponTypes
import com.witcher.thewitcherrpg.feature_character_sheet.domain.models.Equipment
import com.witcher.thewitcherrpg.feature_character_sheet.domain.models.EquipmentItem
import com.witcher.thewitcherrpg.feature_character_sheet.domain.models.MagicItem
import com.witcher.thewitcherrpg.feature_character_sheet.domain.models.WeaponItem
import com.witcher.thewitcherrpg.feature_custom_attributes.domain.AddCustomEquipmentUseCase
import com.witcher.thewitcherrpg.feature_custom_attributes.domain.AddCustomWeaponUseCase
import com.witcher.thewitcherrpg.feature_custom_attributes.domain.AddCustomMagicUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CustomAttributeViewModel @Inject constructor(
    private val addCustomMagicUseCase: AddCustomMagicUseCase,
    private val addCustomWeaponUseCase: AddCustomWeaponUseCase,
    private val addCustomEquipmentUseCase: AddCustomEquipmentUseCase
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
    val optionsEquipmentTypeMap = mapOf(
        "Head Armor" to EquipmentType.HEAD,
        "Chest Armor" to EquipmentType.CHEST,
        "Leg Armor" to EquipmentType.LEG,
        "Weapon" to EquipmentType.WEAPON,
        "Shield" to EquipmentType.SHIELD,
        "Armor Set" to EquipmentType.ARMOR_SET,
        "Amulet" to EquipmentType.AMULET,
    )

    private val _currentMagicSelection = MutableLiveData(MagicGeneralType.SPELL)
    private val currentMagicSelection: LiveData<MagicGeneralType> = _currentMagicSelection

    private val _addMagicState = MutableLiveData<Resource<Boolean>>()
    val addMagicState: LiveData<Resource<Boolean>> = _addMagicState

    private val _addEquipmentState = MutableLiveData<Resource<Boolean>>()
    val addEquipmentState: LiveData<Resource<Boolean>> = _addEquipmentState


    private val _currentEquipmentSelection = MutableLiveData(EquipmentType.HEAD)
    private val currentEquipmentSelection: LiveData<EquipmentType> = _currentEquipmentSelection

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

    fun getMagicTypeFromSelection(selection: String): MagicGeneralType? {
        _currentMagicSelection.value = optionsMagicGeneralTypeMap[selection]
        return optionsMagicGeneralTypeMap[selection]
    }

    fun getElementFromSelection(selection: String): Element? {
        return optionsElementsMap[selection]
    }

    fun getMagicLevelSelection(
        typeSelection: MagicGeneralType,
        currentMagicLevel: MagicLevel
    ): String {
        when (typeSelection) {
            MagicGeneralType.SPELL, MagicGeneralType.RITUAL -> {
                return if (currentMagicLevel !in optionsSpellsMap.values) {
                    optionsSpellsMap.keys.first()
                } else optionsSpellsMap.filterValues { it == currentMagicLevel }.keys.first()
            }
            MagicGeneralType.INVOCATION -> {
                return if (currentMagicLevel !in optionsInvocationsMap.values) {
                    optionsInvocationsMap.keys.first()
                } else optionsInvocationsMap.filterValues { it == currentMagicLevel }.keys.first()
            }
            MagicGeneralType.HEX -> {
                return if (currentMagicLevel !in optionsHexesMap.values) {
                    optionsHexesMap.keys.first()
                } else optionsHexesMap.filterValues { it == currentMagicLevel }.keys.first()
            }
            MagicGeneralType.SIGN -> {
                return if (currentMagicLevel !in optionsSignsMap.values) {
                    optionsSignsMap.keys.first()
                } else optionsSignsMap.filterValues { it == currentMagicLevel }.keys.first()
            }
        }
    }

    private fun getMagicType(
        typeSelection: MagicGeneralType,
        levelSelection: MagicLevel
    ): MagicType {
        return when (typeSelection) {
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
    ): Boolean {

        fun getDangerLevel(magicLevel: MagicLevel): String {
            return when (magicLevel) {
                MagicLevel.LOW -> "Low"
                MagicLevel.MEDIUM -> "Medium"
                MagicLevel.HIGH -> "High"
                else -> {
                    "Low"
                }
            }
        }

        if (name.isBlank() || description.isBlank()) {
            return false
        }

        when (type) {
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

        val rangeFinal = if (isSelfRange && range == "0") {
            "Self"
        } else if (isSelfRange && range != "0") {
            "Self or " + range + "m"
        } else {
            range + "m"
        }
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
            element = if (type == MagicGeneralType.INVOCATION) "" else optionsElementsMap.filterValues { it == element }.keys.first(),
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

    fun isWeaponValid(
        equipmentLevel: String,
        equipmentName: String,
        availability: Availability,
        accuracy: Int,
        reliability: Int,
        focus: Int,
        weight: String,
        cost: String,
        effect: String,
        damage: String,
        range: String,
        enhancements: Int,
        concealment: String,
        bludgeoning: Boolean,
        piercing: Boolean,
        slashing: Boolean,
        elemental: Boolean,
        isTwoHanded: Boolean,
        isRelic: Boolean,
    ): Boolean {
        try {
            fun determineDamageType(): String {
                var damageType = ""
                if (bludgeoning) {
                    damageType += if (damageType.isBlank()) "B"
                    else "/B"
                }
                if (piercing) {
                    damageType += if (damageType.isBlank()) "P"
                    else "/P"
                }
                if (slashing) {
                    damageType += if (damageType.isBlank()) "S"
                    else "/S"
                }
                if (elemental) {
                    damageType += if (damageType.isBlank()) "E"
                    else "/E"
                }
                return damageType
            }

            fun determineWeaponType(): WeaponTypes {
                for (item in WeaponTypes.values()) {
                    if (equipmentLevel == item.typeString) {
                        return item
                    }
                }
                return WeaponTypes.SWORD
            }

            val weapon = WeaponItem(
                name = equipmentName,
                damageType = determineDamageType(),
                weaponAccuracy = accuracy,
                availability = determineAvailability(availability),
                damage = damage,
                reliability = reliability,
                currentReliability = reliability,
                hands = if (isTwoHanded) 2 else 1,
                rng = range,
                effect = arrayListOf(effect),
                concealment = concealment,
                enhancements = enhancements,
                weight = weight.toFloat(),
                cost = cost.toInt(),
                type = determineWeaponType(),
                focus = focus,
                isRelic = isRelic,
                isCustom = true
            )
            addCustomWeapon(weapon)
            return true

        } catch (ex: Exception) {
            return false
        }
    }

    fun isEquipmentValid(
        equipmentType: EquipmentType,
        equipmentLevel: String,
        equipmentName: String,
        availability: Availability,
        stoppingPower: Float,
        weight: String,
        cost: String,
        effect: String,
        enhancements: Float,
        encumbrance: Float,
        isRelic: Boolean,
    ) : Boolean {

        fun determineEquipmentLevel() : EquipmentLevel {
            for (item in EquipmentLevel.values()){
                if (equipmentLevel == item.levelString){
                    return item
                }
            }
            return EquipmentLevel.LIGHT
        }

        fun determineEquipmentType() : EquipmentTypes {
            return when (equipmentType) {
                EquipmentType.HEAD -> {
                    when (determineEquipmentLevel()) {
                        EquipmentLevel.LIGHT -> EquipmentTypes.LIGHT_HEAD
                        EquipmentLevel.MEDIUM -> EquipmentTypes.MEDIUM_HEAD
                        EquipmentLevel.HEAVY -> EquipmentTypes.HEAVY_HEAD
                    }
                }
                EquipmentType.CHEST -> {
                    when (determineEquipmentLevel()) {
                        EquipmentLevel.LIGHT -> EquipmentTypes.LIGHT_CHEST
                        EquipmentLevel.MEDIUM -> EquipmentTypes.MEDIUM_CHEST
                        EquipmentLevel.HEAVY -> EquipmentTypes.HEAVY_CHEST
                    }
                }
                EquipmentType.LEG -> {
                    when (determineEquipmentLevel()) {
                        EquipmentLevel.LIGHT -> EquipmentTypes.LIGHT_LEGS
                        EquipmentLevel.MEDIUM -> EquipmentTypes.MEDIUM_LEGS
                        EquipmentLevel.HEAVY -> EquipmentTypes.HEAVY_LEGS
                    }
                }
                EquipmentType.SHIELD -> {
                    when (determineEquipmentLevel()) {
                        EquipmentLevel.LIGHT -> EquipmentTypes.LIGHT_SHIELD
                        EquipmentLevel.MEDIUM -> EquipmentTypes.MEDIUM_SHIELD
                        EquipmentLevel.HEAVY -> EquipmentTypes.HEAVY_SHIELD
                    }
                }
                EquipmentType.ARMOR_SET -> {
                    when (determineEquipmentLevel()) {
                        EquipmentLevel.LIGHT -> EquipmentTypes.LIGHT_ARMOR_SET
                        EquipmentLevel.MEDIUM -> EquipmentTypes.MEDIUM_ARMOR_SET
                        EquipmentLevel.HEAVY -> EquipmentTypes.HEAVY_ARMOR_SET
                    }
                }
                else -> {
                    EquipmentTypes.LIGHT_HEAD
                }
            }
        }


        try {
            val equipmentItem = EquipmentItem(
                name = equipmentName,
                stoppingPower = stoppingPower.toInt(),
                availability = determineAvailability(availability),
                armorEnhancement = enhancements.toString(),
                effect = effect,
                encumbranceValue = encumbrance.toInt(),
                weight = weight.toFloat(),
                cost = cost.toInt(),
                equipmentType = determineEquipmentType(),
                isRelic = isRelic,
                isCustom = true
            )
            addCustomEquipment(equipmentItem)
            return true
        } catch (ex: Exception){
            return false
        }
    }

    private fun determineAvailability(availability: Availability): String {
        return when (availability) {
            Availability.EVERYWHERE -> "E"
            Availability.COMMON -> "C"
            Availability.RARE -> "R"
            Availability.POOR -> "P"
        }
    }

    private fun addCustomMagic(customMagic: MagicItem) {
        addCustomMagicUseCase(CustomMagic(customMagic)).onEach { result ->
            when (result) {
                is Resource.Success -> _addMagicState.value = Resource.Success(true)
                is Resource.Error -> _addMagicState.value =
                    result.message?.let { Resource.Error(it) }
                else -> {}
            }
        }.launchIn(viewModelScope)
    }

    private fun addCustomWeapon(customWeapon: WeaponItem) {
        addCustomWeaponUseCase(CustomWeapon(customWeapon)).onEach { result ->
            when (result) {
                is Resource.Success -> _addEquipmentState.value = Resource.Success(true)
                is Resource.Error -> _addEquipmentState.value =
                    result.message?.let { Resource.Error(it) }
                else -> {}
            }
        }.launchIn(viewModelScope)
    }

    private fun addCustomEquipment(customEquipment: EquipmentItem) {
        addCustomEquipmentUseCase(CustomEquipment(customEquipment)).onEach { result ->
            when (result) {
                is Resource.Success -> _addEquipmentState.value = Resource.Success(true)
                is Resource.Error -> _addEquipmentState.value =
                    result.message?.let { Resource.Error(it) }
                else -> {}
            }
        }.launchIn(viewModelScope)
    }

    fun getEquipmentTypeFromSelection(selection: String): EquipmentType? {
        _currentEquipmentSelection.value = optionsEquipmentTypeMap[selection]
        return _currentEquipmentSelection.value
    }

    fun getEquipmentLevelOptions(equipmentType: EquipmentType): List<String> {
        return when (equipmentType) {
            EquipmentType.HEAD, EquipmentType.CHEST, EquipmentType.LEG, EquipmentType.SHIELD, EquipmentType.ARMOR_SET -> {
                listOf(
                    EquipmentLevel.LIGHT.levelString,
                    EquipmentLevel.MEDIUM.levelString,
                    EquipmentLevel.HEAVY.levelString
                )
            }
            EquipmentType.WEAPON -> {
                WeaponTypes.values().map { it.typeString }
            }
            else -> {
                listOf()
            }
        }
    }

    fun getAvailabilityFromSelection(selection: String): Availability {
        for (item in Availability.values()) {
            if (selection == item.toString) {
                return item
            }
        }
        return Availability.EVERYWHERE
    }

}