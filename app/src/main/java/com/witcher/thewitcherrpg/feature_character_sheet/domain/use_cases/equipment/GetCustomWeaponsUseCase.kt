package com.witcher.thewitcherrpg.feature_character_sheet.domain.use_cases.equipment

import com.witcher.thewitcherrpg.core.Resource
import com.witcher.thewitcherrpg.core.domain.model.CustomWeapon
import com.witcher.thewitcherrpg.core.domain.repository.CustomAttributesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCustomWeaponsUseCase @Inject constructor(
    private val repository: CustomAttributesRepository
) {

    operator fun invoke(): Flow<Resource<List<CustomWeapon>>> = flow {
        emit(Resource.Loading())

        try {
            repository.getWeapon().collect{
                emit(Resource.Success(it))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }

    }
}