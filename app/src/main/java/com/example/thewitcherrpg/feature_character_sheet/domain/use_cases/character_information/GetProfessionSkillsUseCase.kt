package com.example.thewitcherrpg.feature_character_sheet.domain.use_cases.character_information

import com.example.thewitcherrpg.R
import com.example.thewitcherrpg.TheWitcherTRPGApp
import com.example.thewitcherrpg.core.Constants
import javax.inject.Inject

class GetProfessionSkillsUseCase @Inject constructor() {

    operator fun invoke(professions: Constants.Professions): ArrayList<String> {

        val professionSkills =
            TheWitcherTRPGApp.getContext()!!.resources!!.getStringArray(R.array.professions_skills_array)

        return getProfessionSkills(professions, professionSkills)
    }

    private fun getProfessionSkills(
        profession: Constants.Professions,
        skillsList: Array<String>
    ): ArrayList<String> {

        val skills: ArrayList<String> = arrayListOf()
        for (item in skillsList) {
            val pair = item.split(":").toTypedArray()
            when (profession) {
                Constants.Professions.BARD -> {
                    if (pair[0] == "Bard") {
                        val skillList = pair[1].split(",").toTypedArray()
                        for (skill in skillList) {
                            skills.add(skill)
                        }
                        return skills
                    }
                }
                Constants.Professions.CRIMINAL -> {
                    if (pair[0] == "Criminal") {
                        val skillList = pair[1].split(",").toTypedArray()
                        for (skill in skillList) {
                            skills.add(skill)
                        }
                        return skills
                    }
                }
                Constants.Professions.CRAFTSMAN -> {
                    if (pair[0] == "Craftsman") {
                        val skillList = pair[1].split(",").toTypedArray()
                        for (skill in skillList) {
                            skills.add(skill)
                        }
                        return skills
                    }
                }
                Constants.Professions.DOCTOR -> {
                    if (pair[0] == "Doctor") {
                        val skillList = pair[1].split(",").toTypedArray()
                        for (skill in skillList) {
                            skills.add(skill)
                        }
                        return skills
                    }
                }
                Constants.Professions.MAGE -> {
                    if (pair[0] == "Mage") {
                        val skillList = pair[1].split(",").toTypedArray()
                        for (skill in skillList) {
                            skills.add(skill)
                        }
                        return skills
                    }
                }
                Constants.Professions.MAN_AT_ARMS -> {
                    if (pair[0] == "Man At Arms") {
                        val skillList = pair[1].split(",").toTypedArray()
                        for (skill in skillList) {
                            skills.add(skill)
                        }
                        return skills
                    }
                }
                Constants.Professions.PRIEST -> {
                    if (pair[0] == "Priest") {
                        val skillList = pair[1].split(",").toTypedArray()
                        for (skill in skillList) {
                            skills.add(skill)
                        }
                        return skills
                    }
                }
                Constants.Professions.WITCHER -> {
                    if (pair[0] == "Witcher") {
                        val skillList = pair[1].split(",").toTypedArray()
                        for (skill in skillList) {
                            skills.add(skill)
                        }
                        return skills
                    }
                }
                Constants.Professions.MERCHANT -> {
                    if (pair[0] == "Merchant") {
                        val skillList = pair[1].split(",").toTypedArray()
                        for (skill in skillList) {
                            skills.add(skill)
                        }
                        return skills
                    }
                }
                Constants.Professions.NOBLE -> {
                    if (pair[0] == "Noble") {
                        val skillList = pair[1].split(",").toTypedArray()
                        for (skill in skillList) {
                            skills.add(skill)
                        }
                        return skills
                    }
                }
                Constants.Professions.PEASANT -> {
                    if (pair[0] == "Peasant") {
                        val skillList = pair[1].split(",").toTypedArray()
                        for (skill in skillList) {
                            skills.add(skill)
                        }
                        return skills
                    }
                }
            }
        }
        return arrayListOf()
    }
}