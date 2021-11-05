package com.example.thewitcherrpg.feature_character_sheet.domain.use_cases.magic

import com.example.thewitcherrpg.R
import com.example.thewitcherrpg.TheWitcherTRPGApp
import com.example.thewitcherrpg.feature_character_sheet.presentation.magic.MagicItem
import com.example.thewitcherrpg.feature_character_sheet.presentation.magic.MagicType
import javax.inject.Inject

class GetMagicListUseCase @Inject constructor() {

    operator fun invoke(source: Int): ArrayList<MagicItem> {

        val magicStringArray = TheWitcherTRPGApp.getContext()!!.resources!!.getStringArray(source)

        return when (source) {
            R.array.novice_spells_list_data -> getSpellListInfo(
                magicStringArray,
                MagicType.NOVICE_SPELL
            )

            R.array.journeyman_spells_list_data -> getSpellListInfo(
                magicStringArray,
                MagicType.JOURNEYMAN_SPELL
            )

            R.array.master_spells_list_data -> getSpellListInfo(
                magicStringArray,
                MagicType.MASTER_SPELL
            )
            R.array.novice_rituals_list_data -> getRitualListInfo(
                magicStringArray,
                MagicType.NOVICE_RITUAL
            )

            R.array.journeyman_rituals_list_data -> getRitualListInfo(
                magicStringArray,
                MagicType.JOURNEYMAN_RITUAL
            )

            R.array.master_rituals_list_data -> getRitualListInfo(
                magicStringArray,
                MagicType.MASTER_RITUAL
            )
            R.array.hexes_list_data -> getHexListInfo(magicStringArray)


            else -> arrayListOf()
        }
    }

    private fun getSpellListInfo(itemList: Array<String>, type: MagicType): ArrayList<MagicItem> {

        val magicArray: ArrayList<MagicItem> = arrayListOf()

        for (tag in itemList) {
            val pair = tag.split(":").toTypedArray()
            val spellName = pair[0]
            val staCost = pair[1]
            val description = pair[2]
            val range = pair[3]
            val duration = pair[4]
            val defense = pair[5]
            val element = pair[6]

            magicArray.add(
                MagicItem(
                    type = type,
                    name = spellName,
                    try {
                        staCost.toInt()
                    } catch (ex: NumberFormatException) {
                        null
                    },
                    description = description,
                    range = range,
                    duration = duration,
                    defense = defense,
                    element = element
                )
            )
        }
        return magicArray
    }

    private fun getRitualListInfo(itemList: Array<String>, type: MagicType): ArrayList<MagicItem> {

        val magicArray: ArrayList<MagicItem> = arrayListOf()

        for (tag in itemList) {
            val pair = tag.split(":").toTypedArray()
            val spellName = pair[0]
            val staCost = pair[1]
            val description = pair[2]
            val preparation = pair[3]
            val difficulty = pair[4]
            val duration = pair[5]
            val components = pair[6]

            magicArray.add(
                MagicItem(
                    type = type,
                    name = spellName,
                    staminaCost = try {
                        staCost.toInt()
                    } catch (ex: NumberFormatException) {
                        null
                    },
                    description = description,
                    preparation = preparation,
                    difficulty = try {
                        difficulty.toInt()
                    } catch (ex: NumberFormatException) {
                        null
                    },
                    duration = duration,
                    components = components
                )
            )
        }
        return magicArray
    }

    private fun getHexListInfo(itemList: Array<String>): ArrayList<MagicItem> {

        val magicArray: ArrayList<MagicItem> = arrayListOf()

        for (tag in itemList) {
            val pair = tag.split(":").toTypedArray()
            val spellName = pair[0]
            val staCost = pair[1]
            val description = pair[2]
            val danger = pair[3]
            val lift = pair[4]

            magicArray.add(
                MagicItem(
                    type = MagicType.HEX,
                    name = spellName,
                    staminaCost = try {
                        staCost.toInt()
                    } catch (ex: NumberFormatException) {
                        null
                    },
                    description = description,
                    danger = danger,
                    requirementToLift = lift
                )
            )
        }
        return magicArray
    }

}
