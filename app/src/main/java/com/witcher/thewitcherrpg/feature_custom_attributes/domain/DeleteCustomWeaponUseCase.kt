package com.witcher.thewitcherrpg.feature_custom_attributes.domain

import com.witcher.thewitcherrpg.core.Resource
import com.witcher.thewitcherrpg.core.domain.model.CustomWeapon
import com.witcher.thewitcherrpg.core.domain.repository.CustomAttributesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteCustomWeaponUseCase @Inject constructor(
    private val repository: CustomAttributesRepository
) {
    operator fun invoke(weapon: CustomWeapon): Flow<Resource<String>> = flow{
        emit(Resource.Loading())

        try {
            repository.deleteWeapon(weapon)
            emit(Resource.Success("Weapon Deleted Successfully"))
        } catch (ex: Exception){
            emit(Resource.Error(ex.localizedMessage ?: "An unexpected error occurred"))
        }
    }
}