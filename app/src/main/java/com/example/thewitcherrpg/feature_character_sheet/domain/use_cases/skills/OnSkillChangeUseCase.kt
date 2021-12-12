package com.example.thewitcherrpg.feature_character_sheet.domain.use_cases.skills

import com.example.thewitcherrpg.core.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OnSkillChangeUseCase @Inject constructor() {

    operator fun invoke(
        value: Int,
        ip: Int,
        increase: Boolean,
        inCharacterCreation: Boolean,
        doubleCost: Boolean = false
    ): Resource<Pair<Int, Int>> {

        var newIP = ip
        var newVal = value

        if (!inCharacterCreation) {
            if (increase && newVal < 10) {
                if (doubleCost){
                    if (newVal == 0 && ip >= 2) {
                        newVal = 1
                        newIP -= 2
                    } else if (ip >= newVal*2 && newVal != 0) {
                        newIP -= newVal*2
                        newVal += 1
                    }
                } else {
                    if (ip >= newVal && ip > 0){
                        if (newVal == 0) {
                            newVal = 1
                            newIP -= 1
                        } else {
                            newIP -= newVal
                            newVal += 1
                        }
                    }
                }
            }

            if (!increase) {
                if (newVal > 0) {

                    newIP += if (newVal == 1){
                        if (doubleCost) 2 else 1
                    }
                    else if (!doubleCost) (newVal - 1) else (newVal - 1)*2
                    newVal -= 1

                } else return Resource.Error("Cannot go below 0")
            }
        } else {
            if (increase) {
                if (ip > 0 && newVal < 6) {
                    newIP -= if (doubleCost) 2 else 1
                    newVal += 1
                } else return Resource.Error("Not enough IP")
            }

            if (!increase) {
                if (newVal > 0) {

                    newIP += if (doubleCost) 2 else 1
                    newVal -= 1
                } else return Resource.Error("Cannot go below 0")
            }
        }
        return Resource.Success(Pair(newIP, newVal))
    }
}