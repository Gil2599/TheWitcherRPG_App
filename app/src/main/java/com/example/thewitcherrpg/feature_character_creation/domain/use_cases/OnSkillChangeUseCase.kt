package com.example.thewitcherrpg.feature_character_creation.domain.use_cases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OnSkillChangeUseCase @Inject constructor() {

    operator fun invoke(value: Int, ip: Int, increase: Boolean): Flow<Pair<Int, Int>> = flow {

        var newIP = ip
        var newVal = value

        if (increase) {
            if (newVal == 0) newVal = 1

            if (ip >= newVal) {
                newIP -= newVal
            } //else return false
        }

        if (!increase) {
            if (newVal > 0) {
                if (newVal == 1) newVal = 2

                newIP += (newVal - 1)
            } //else return false
        }
        //ip = newIP

    }
}