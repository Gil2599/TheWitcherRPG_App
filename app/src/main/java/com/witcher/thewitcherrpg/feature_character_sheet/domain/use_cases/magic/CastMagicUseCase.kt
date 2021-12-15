package com.witcher.thewitcherrpg.feature_character_sheet.domain.use_cases.magic

import com.witcher.thewitcherrpg.core.Resource
import com.witcher.thewitcherrpg.feature_character_sheet.domain.models.MagicItem
import javax.inject.Inject

class CastMagicUseCase @Inject constructor() {

    operator fun invoke(item: MagicItem, focus: Int, vigor: Int): Resource<Int>{

        val totalCost = if(item.staminaCost!! - focus > 0) item.staminaCost!! - focus
        else 1

        return if (totalCost > vigor){
            val hpCost = 5 * (totalCost - vigor)
            Resource.Error("Not enough vigor.", hpCost)
        } else Resource.Success(totalCost)

    }
}