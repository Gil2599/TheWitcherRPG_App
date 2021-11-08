package com.example.thewitcherrpg.feature_character_creation.domain.use_cases

import android.util.Log
import com.example.thewitcherrpg.R
import com.example.thewitcherrpg.TheWitcherTRPGApp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRacePerkUseCase @Inject constructor() {

    operator fun invoke(race: String): Flow<String> = flow {

        val tags = TheWitcherTRPGApp.getContext()?.resources?.getStringArray(R.array.races_data_array)

        if (tags != null) {
            for (tag in tags) {
                val pair = tag.split(":").toTypedArray()
                val key = pair[0]
                val value = pair[1]

                if (race == key) {
                    //Log.d("Test", key)
                    emit(value)
                    break
                }
            }
        }
    }

}