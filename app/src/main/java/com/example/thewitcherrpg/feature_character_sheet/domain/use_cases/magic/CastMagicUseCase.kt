package com.example.thewitcherrpg.feature_character_sheet.domain.use_cases.magic

import com.example.thewitcherrpg.core.Resource
import com.example.thewitcherrpg.feature_character_sheet.domain.item_models.MagicItem
import javax.inject.Inject

class CastMagicUseCase @Inject constructor() {

    operator fun invoke(item: MagicItem, stamina: Int, vigor: Int): Resource<Int>{

        return if (item.staminaCost!! > vigor){
            val hpCost = 5 * (item.staminaCost!! - vigor)
            Resource.Error("Not enough vigor.", hpCost)
        } else Resource.Success(item.staminaCost!!)

    }
}