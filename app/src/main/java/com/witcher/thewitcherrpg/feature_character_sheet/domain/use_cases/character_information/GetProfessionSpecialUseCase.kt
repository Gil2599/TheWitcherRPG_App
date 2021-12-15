package com.witcher.thewitcherrpg.feature_character_sheet.domain.use_cases.character_information

import com.witcher.thewitcherrpg.R
import com.witcher.thewitcherrpg.TheWitcherTRPGApp
import com.witcher.thewitcherrpg.core.Constants
import javax.inject.Inject

class GetProfessionSpecialUseCase @Inject constructor() {

    operator fun invoke(professions: Constants.Professions): String {

        val professionSpecial =
            TheWitcherTRPGApp.getContext()!!.resources!!.getStringArray(R.array.professions_special_array)

        return getProfessionSkills(professions, professionSpecial)
    }

    private fun getProfessionSkills(
        profession: Constants.Professions,
        specialArray: Array<String>
    ): String {
        for (item in specialArray) {
            val pair = item.split(":").toTypedArray()
            when (profession) {
                Constants.Professions.MAGE -> {
                    if (pair[0] == "Mage") {
                        return pair[1]
                    }
                }
                Constants.Professions.WITCHER -> {
                    if (pair[0] == "Witcher") {
                        return pair[1]
                    }
                }
                Constants.Professions.MERCHANT -> {
                    if (pair[0] == "Merchant") {
                        return pair[1]
                    }
                }
                Constants.Professions.NOBLE -> {
                    if (pair[0] == "Noble") {
                        return pair[1]
                    }
                }
                Constants.Professions.PEASANT -> {
                    if (pair[0] == "Peasant") {
                        return pair[1]
                    }
                }
            }
        }
        return ""
    }
}