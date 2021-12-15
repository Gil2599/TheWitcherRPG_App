package com.witcher.thewitcherrpg.feature_character_creation.domain.use_cases

data class CharacterCreationUseCases(
    val addCharacterUseCase: AddCharacterUseCase,
    val getDefiningSkillInfoUseCase: GetDefiningSkillInfoUseCase,
    val getDefiningSkillUseCase: GetDefiningSkillUseCase,
    val getRacePerkUseCase: GetRacePerkUseCase,
    val getProfessionSkillsIndicesUseCase: GetProfessionSkillsIndicesUseCase
)