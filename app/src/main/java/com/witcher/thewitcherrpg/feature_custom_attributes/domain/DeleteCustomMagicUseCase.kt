package com.witcher.thewitcherrpg.feature_custom_attributes.domain

import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import com.witcher.thewitcherrpg.core.Resource
import com.witcher.thewitcherrpg.core.domain.model.CustomMagic
import com.witcher.thewitcherrpg.core.domain.repository.CustomAttributesRepository
import com.witcher.thewitcherrpg.core.firebase.FirebaseEvents
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteCustomMagicUseCase @Inject constructor(
    private val repository: CustomAttributesRepository
    ) {
    operator fun invoke(magic: CustomMagic): Flow<Resource<String>> = flow{
        emit(Resource.Loading())

        try {
            repository.deleteMagic(magic.magicItem.name)
            emit(Resource.Success("Magic Deleted Successfully"))
        } catch (ex: Exception){
            emit(Resource.Error(ex.localizedMessage ?: "An unexpected error occurred"))
            Firebase.analytics.logEvent(FirebaseEvents.CUSTOM_MAGIC_DELETE_ERROR.name) {
                param("delete_error", ex.localizedMessage ?: "An unexpected error occurred")
            }
        }
    }
}