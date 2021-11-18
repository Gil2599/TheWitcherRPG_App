package com.example.thewitcherrpg.feature_character_sheet.domain.use_cases.magic

import com.example.thewitcherrpg.core.Resource
import com.example.thewitcherrpg.feature_character_sheet.domain.item_models.MagicItem
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