package com.witcher.thewitcherrpg.di

import android.app.Application
import com.witcher.thewitcherrpg.core.data.data_source.CharacterDao
import com.witcher.thewitcherrpg.core.data.data_source.CharacterDatabase
import com.witcher.thewitcherrpg.core.data.repository.CharacterRepositoryImpl
import com.witcher.thewitcherrpg.core.dataStoreRepository.DataStoreRepository
import com.witcher.thewitcherrpg.core.domain.repository.CharacterRepository
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