package com.witcher.thewitcherrpg.feature_character_sheet.domain.use_cases.character_information

import com.witcher.thewitcherrpg.R
import com.witcher.thewitcherrpg.TheWitcherTRPGApp
import com.witcher.thewitcherrpg.core.Constants
import javax.inject.Inject

class GetProfessionGearUseCase @Inject constructor() {

    operator fun invoke(professions: Constants.Professions): ArrayList<String> {

        val professionGear =
            TheWitcherTRPGApp.getContext()!!.resources!!.getStringArray(R.array.professions_gear_array)

        return getProfessionSkills(professions, professionGear)
    }

    private fun getProfessionSkills(
        profession: Constants.Professions,
        gearArray: Array<String>
    ): ArrayList<String> {

        val skills: ArrayList<String> = arrayListOf()
        for (item in gearArray) {
            val pair = item.split(":").toTypedArray()
            when (profession) {
                Constants.Professions.BARD -> {
                    if (pair[0] == "Bard") {
                        val gearList = pair[1].split(",").toTypedArray()
                        for (gear in gearList) {
                            skills.add(gear)
                        }
                        return skills
                    }
                }
                Constants.Professions.CRIMINAL -> {
                    if (pair[0] == "Criminal") {
                        val gearList = pair[1].split(",").toTypedArray()
                        for (gear in gearList) {
                            skills.add(gear)
                        }
                        return skills
                    }
                }
                Constants.Professions.CRAFTSMAN -> {
                    if (pair[0] == "Craftsman") {
                        val gearList = pair[1].split(",").toTypedArray()
                        for (gear in gearList) {
                            skills.add(gear)
                        }
                        return skills
                    }
                }
                Constants.Professions.DOCTOR -> {
                    if (pair[0] == "Doctor") {
                        val gearList = pair[1].split(",").toTypedArray()
                        for (gear in gearList) {
                            skills.add(gear)
                        }
                        return skills
                    }
                }
                Constants.Professions.MAGE -> {
                    if (pair[0] == "Mage") {
                        val gearList = pair[1].split(",").toTypedArray()
                        for (gear in gearList) {
                            skills.add(gear)
                        }
                        return skills
                    }
                }
                Constants.Professions.MAN_AT_ARMS -> {
                    if (pair[0] == "Man At Arms") {
                        val gearList = pair[1].split(",").toTypedArray()
                        for (gear in gearList) {
                            skills.add(gear)
                        }
                        return skills
                    }
                }
                Constants.Professions.PRIEST -> {
                    if (pair[0] == "Priest") {
                        val gearList = pair[1].split(",").toTypedArray()
                        for (gear in gearList) {
                            skills.add(gear)
                        }
                        return skills
                    }
                }
                Constants.Professions.WITCHER -> {
                    if (pair[0] == "Witcher") {
                        val gearList = pair[1].split(",").toTypedArray()
                        for (gear in gearList) {
                            skills.add(gear)
                        }
                        return skills
                    }
                }
                Constants.Professions.MERCHANT -> {
                    if (pair[0] == "Merchant") {
                        val gearList = pair[1].split(",").toTypedArray()
                        for (gear in gearList) {
                            skills.add(gear)
                        }
                        return skills
                    }
                }
                Constants.Professions.NOBLE -> {
                    if (pair[0] == "Noble") {
                        val gearList = pair[1].split(",").toTypedArray()
                        for (gear in gearList) {
                            skills.add(gear)
                        }
                        return skills
                    }
                }
                Constants.Professions.PEASANT -> {
                    if (pair[0] == "Peasant") {
                        val gearList = pair[1].split(",").toTypedArray()
                        for (gear in gearList) {
                            skills.add(gear)
                        }
                        return skills
                    }
                }
            }
        }
        return arrayListOf()
    }
}