package com.witcher.thewitcherrpg.feature_character_creation.domain.use_cases

import com.witcher.thewitcherrpg.R
import com.witcher.thewitcherrpg.TheWitcherTRPGApp
import com.witcher.thewitcherrpg.core.Constants
import javax.inject.Inject

class GetProfessionSkillsIndicesUseCase @Inject constructor() {

    operator fun invoke(profession: Constants.Professions): ArrayList<Int> {

        val tags = TheWitcherTRPGApp.getContext()?.resources?.getStringArray(R.array.profession_indices_array)

        val indicesArray = arrayListOf<Int>()

        val professionString = when (profession){
            Constants.Professions.BARD -> "Bard"
            Constants.Professions.CRIMINAL -> "Criminal"
            Constants.Professions.CRAFTSMAN -> "Craftsman"
            Constants.Professions.DOCTOR -> "Doctor"
            Constants.Professions.MAGE -> "Mage"
            Constants.Professions.MAN_AT_ARMS -> "Man At Arms"
            Constants.Professions.PRIEST -> "Priest"
            Constants.Professions.DRUID -> "Druid"
            Constants.Professions.WITCHER -> "Witcher"
            Constants.Professions.MERCHANT -> "Merchant"
            Constants.Professions.NOBLE -> "Noble"
            Constants.Professions.PEASANT -> "Peasant"
        }

        if (tags != null) {
            for (tag in tags) {
                val pair = tag.split(":").toTypedArray()
                val key = pair[0]
                val value = pair[1]

                if (professionString == key) {
                    val indices = value.split(",").toTypedArray()
                    for (index in indices){
                        indicesArray.add(index.toInt())
                    }
                    return indicesArray
                }
            }
        }
        return arrayListOf()
    }
}