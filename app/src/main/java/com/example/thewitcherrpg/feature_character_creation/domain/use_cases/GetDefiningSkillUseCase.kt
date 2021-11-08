package com.example.thewitcherrpg.feature_character_creation.domain.use_cases

import android.util.Log
import com.example.thewitcherrpg.R
import com.example.thewitcherrpg.TheWitcherTRPGApp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetDefiningSkillUseCase @Inject constructor() {

    operator fun invoke(profession: String): Flow<String> = flow {

        val tags = TheWitcherTRPGApp.getContext()?.resources?.getStringArray(R.array.professions_defSkills_array)

        if (tags != null) {
            for (tag in tags) {
                val pair = tag.split(":").toTypedArray()
                val key = pair[0]
                val value = pair[1]

                if (profession == key) {
                    emit(value)
                    break
                }
            }
        }
    }
}