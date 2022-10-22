package com.witcher.thewitcherrpg.feature_custom_attributes.domain

import com.witcher.thewitcherrpg.core.Resource
import com.witcher.thewitcherrpg.core.domain.model.CustomMagic
import com.witcher.thewitcherrpg.core.domain.repository.CustomAttributesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddCustomMagicUseCase @Inject constructor(
    private val repository: CustomAttributesRepository
) {

    operator fun invoke(magic: CustomMagic): Flow<Resource<String>> = flow {
        emit(Resource.Loading())
        try {
            repository.addMagic(magic)
            emit(Resource.Success("Magic Added Successfully"))
        } catch (ex: Exception){
            emit(Resource.Error(ex.localizedMessage ?: "An unexpected error occurred"))
        }
    }
}