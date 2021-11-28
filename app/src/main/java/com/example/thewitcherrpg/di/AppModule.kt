package com.example.thewitcherrpg.di

import android.app.Application
import com.example.thewitcherrpg.core.data.data_source.CharacterDao
import com.example.thewitcherrpg.core.data.data_source.CharacterDatabase
import com.example.thewitcherrpg.core.data.repository.CharacterRepositoryImpl
import com.example.thewitcherrpg.core.dataStoreRepository.DataStoreRepository
import com.example.thewitcherrpg.core.domain.repository.CharacterRepository
import com.example.thewitcherrpg.feature_character_creation.domain.use_cases.*
import com.example.thewitcherrpg.feature_character_list.domain.use_cases.CharacterListUseCases
import com.example.thewitcherrpg.feature_character_list.domain.use_cases.GetCharacterListUseCase
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
    fun provideRepository(db: CharacterDatabase): CharacterRepository{
        return CharacterRepositoryImpl(db.getDao())
    }

    @Provides
    @Singleton
    fun provideDao(characterDatabase: CharacterDatabase): CharacterDao {
        return characterDatabase.getDao()
    }

    @Provides
    @Singleton
    fun provideCharacterSheetUseCases(repository: CharacterRepository): CharacterListUseCases {
        return CharacterListUseCases(
            getCharacterListUseCase = GetCharacterListUseCase(repository)
        )
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