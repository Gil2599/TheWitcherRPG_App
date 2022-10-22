package com.witcher.thewitcherrpg.feature_custom_attributes.domain

import com.witcher.thewitcherrpg.core.Resource
import com.witcher.thewitcherrpg.core.domain.model.CustomEquipment
import com.witcher.thewitcherrpg.core.domain.repository.CustomAttributesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddCustomEquipmentUseCase @Inject constructor(
    private val repository: CustomAttributesRepository
) {

    operator fun invoke(equipment: CustomEquipment): Flow<Resource<String>> = flow {
        emit(Resource.Loading())
        try {
            repository.addEquipment(equipment)
            emit(Resource.Success("Equipment Added Successfully"))
        } catch (ex: Exception){
            emit(Resource.Error(ex.localizedMessage ?: "An unexpected error occurred"))
        }
    }
}