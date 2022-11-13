package com.witcher.thewitcherrpg.feature_character_sheet.domain.use_cases.equipment

import com.witcher.thewitcherrpg.core.Resource
import com.witcher.thewitcherrpg.core.domain.model.CustomEquipment
import com.witcher.thewitcherrpg.core.domain.repository.CustomAttributesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCustomEquipmentUseCase @Inject constructor(
    private val repository: CustomAttributesRepository
) {

    operator fun invoke(): Flow<Resource<List<CustomEquipment>>> = channelFlow {
        send(Resource.Loading())

        try {
            repository.getEquipment().collect{
                send(Resource.Success(it))
            }
        } catch (e: Exception) {
            send(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }

    }
}