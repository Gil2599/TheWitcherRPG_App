package com.example.thewitcherrpg.feature_character_sheet.domain.use_cases.profession_tree

import com.example.thewitcherrpg.core.Resource
import javax.inject.Inject

class OnProfessionSkillChangeUseCase @Inject constructor() {

    operator fun invoke(
        value: Int,
        ip: Int,
        increase: Boolean,
        nextSkill: Int
    ): Resource<Pair<Int, Int>> {

        var newIP = ip
        var newVal = value

        if (increase) {
            if (value >= 10) return Resource.Error("Cannot go past 10")

            if (ip >= newVal) {
                if (newVal == 0) {
                    newVal = 1
                    newIP -= 1
                } else {
                    newIP -= newVal
                    newVal += 1
                }
            } else return Resource.Error("Not enough IP")
        }

        if (!increase) {
            if (value > 0 && nextSkill > 0) return Resource.Error("Next skill is activated")

            if (newVal > 0) {

                newIP += if (newVal == 1) 1
                else (newVal - 1)
                newVal -= 1

            } else return Resource.Error("Cannot go below 0")
        }

        return Resource.Success(Pair(newIP, newVal))
    }

}