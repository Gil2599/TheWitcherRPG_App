package com.witcher.thewitcherrpg.di

import android.app.Application
import com.witcher.thewitcherrpg.core.data.characterData.data_source.CharacterDao
import com.witcher.thewitcherrpg.core.data.characterData.data_source.CharacterDatabase
import com.witcher.thewitcherrpg.core.data.characterData.repository.CharacterRepositoryImpl
import com.witcher.thewitcherrpg.core.data.customAttributesData.data_source.CustomAttributesDao
import com.witcher.thewitcherrpg.core.data.customAttributesData.data_source.CustomAttributesDatabase
import com.witcher.thewitcherrpg.core.data.customAttributesData.repository.CustomAttributesRepositoryImpl
import com.witcher.thewitcherrpg.core.dataStoreRepository.DataStoreRepository
import com.witcher.thewitcherrpg.core.domain.repository.CharacterRepository
import com.witcher.thewitcherrpg.core.domain.repository.CustomAttributesRepository
import com.witcher.thewitcherrpg.feature_character_creation.domain.use_cases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDataStoreRepository(context: Application): DataStoreRepository {
        return DataStoreRepository(context)
    }

    @Provides
    @Singleton
    fun provideCharacterDatabase(context: Application): CharacterDatabase {
        return CharacterDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideCustomAttributesDatabase(context: Application): CustomAttributesDatabase {
        return CustomAttributesDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideRepository(db: CharacterDatabase): CharacterRepository{
        return CharacterRepositoryImpl(db.getDao())
    }

    @Provides
    @Singleton
    fun provideCustomAttributeRepository(db: CustomAttributesDatabase): CustomAttributesRepository{
        return CustomAttributesRepositoryImpl(db.getDao())
    }

    @Provides
    @Singleton
    fun provideDao(characterDatabase: CharacterDatabase): CharacterDao {
        return characterDatabase.getDao()
    }

    @Provides
    @Singleton
    fun provideCustomAttributesDao(customAttributesDatabase: CustomAttributesDatabase): CustomAttributesDao {
        return customAttributesDatabase.getDao()
    }

    @Provides
    @Singleton
    fun provideCharacterCreationUseCases(repository: CharacterRepository): CharacterCreationUseCases{
        return CharacterCreationUseCases(
            addCharacterUseCase = AddCharacterUseCase(repository),
            getDefiningSkillInfoUseCase = GetDefiningSkillInfoUseCase(),
            getDefiningSkillUseCase = GetDefiningSkillUseCase(),
            getRacePerkUseCase = GetRacePerkUseCase(),
            getProfessionSkillsIndicesUseCase = GetProfessionSkillsIndicesUseCase()
        )
    }
}