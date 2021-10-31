package com.example.thewitcherrpg.feature_character_creation.domain.use_cases

import com.example.thewitcherrpg.R
import com.example.thewitcherrpg.TheWitcherTRPGApp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetDefiningSkillInfoUseCase @Inject constructor() {

    operator fun invoke(defSkill: String): Flow<String> = flow {

        val tags = TheWitcherTRPGApp.getContext()?.resources?.getStringArray(R.array.defSkills_data_array)

        if (tags != null) {
            for (tag in tags) {
                val pair = tag.split(":").toTypedArray()
                val key = pair[0]
                val value = pair[1]

                if (defSkill == key) {
                    emit(value)
                    break
                }
            }
        }
    }
}