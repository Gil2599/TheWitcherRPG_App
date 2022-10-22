package com.witcher.thewitcherrpg.feature_character_sheet.domain.use_cases.magic

import com.witcher.thewitcherrpg.core.Resource
import com.witcher.thewitcherrpg.core.domain.model.CustomMagic
import com.witcher.thewitcherrpg.core.domain.repository.CustomAttributesRepository
import com.witcher.thewitcherrpg.feature_character_sheet.domain.models.MagicItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCustomMagicUseCase @Inject constructor(
    private val repository: CustomAttributesRepository
) {

    operator fun invoke(): Flow<Resource<List<CustomMagic>>> = flow {
        emit(Resource.Loading())

        try {
            repository.getMagic().collect{
                emit(Resource.Success(it))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }

    }
}