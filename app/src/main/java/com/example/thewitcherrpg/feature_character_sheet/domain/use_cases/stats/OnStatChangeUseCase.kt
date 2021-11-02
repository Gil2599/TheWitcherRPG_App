package com.example.thewitcherrpg.feature_character_sheet.domain.use_cases.stats

import com.example.thewitcherrpg.core.Resource
import javax.inject.Inject

class OnStatChangeUseCase @Inject constructor() {

    operator fun invoke(value: Int, ip: Int, increase: Boolean, inCharacterCreation: Boolean): Resource<Pair<Int, Int>> {

        var newIP = ip
        var newVal = value

        if(!inCharacterCreation) {
            if (increase) {
                if (newVal == 0) newVal = 1

                if (ip >= newVal) {
                    newIP -= newVal
                } else return Resource.Error("Not enough IP")
            }

            if (!increase) {
                if (newVal > 0) {
                    if (newVal == 1) newVal = 2

                    newIP += (newVal - 1)
                } else return Resource.Error("Cannot go below 0")
            }
            //_iP.value = newIP
        }
        else {
            if (increase) {
                if (ip > 0 && newVal < 6) {
                    newIP -= 1
                    newVal += 1
                } else return Resource.Error("Not enough IP")
            }

            if (!increase) {
                if (newVal > 0) {

                    newIP += 1
                    newVal -= 1
                } else return Resource.Error("Cannot go below 0")
            }
            //_iP.value = newIP
        }
        return Resource.Success(Pair(newIP, newVal))
    }

}