package com.example.thewitcherrpg.feature_character_creation.presentation

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thewitcherrpg.core.Constants
import com.example.thewitcherrpg.feature_character_creation.domain.use_cases.*
import com.example.thewitcherrpg.feature_character_list.domain.model.Character
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CharacterCreationViewModel @Inject constructor(
    private val addCharacterUseCase: AddCharacterUseCase,
    private val getDefiningSkillUseCase: GetDefiningSkillUseCase,
    private val getRacePerkUseCase: GetRacePerkUseCase,
    private val getDefiningSkillInfoUseCase: GetDefiningSkillInfoUseCase,
    private val onSkillChangeUseCase: OnSkillChangeUseCase
): ViewModel() {

    private val _inCharacterCreation = mutableStateOf(false)
    val inCharacterCreation: State<Boolean> = _inCharacterCreation

    val name = MutableLiveData<String>("")
    //val name: State<String> = _name

    private val _age = mutableStateOf("")
    val age: State<String> = _age

    private val _gender = mutableStateOf("")
    val gender: State<String> = _gender

    private val _race = mutableStateOf("")
    val race: State<String> = _race

    private val _profession = mutableStateOf(Constants.Professions.BARD)
    val profession: State<Constants.Professions> = _profession

    private val _definingSkill = MutableStateFlow("")
    val definingSkill = _definingSkill.asStateFlow()

    private val _definingSkillInfo = MutableStateFlow("")
    val definingSkillInfo = _definingSkillInfo.asStateFlow()

    private val _perks = MutableStateFlow("")
    val perks = _perks.asStateFlow()

    private var _ip = MutableStateFlow(70)
    val ip = _ip.asStateFlow()

    //Profession Skills
    private var _professionSkillA1 = MutableStateFlow(0)
    val professionSkillA1 = _professionSkillA1.asStateFlow()

    private var _professionSkillA2 = MutableStateFlow(0)
    val professionSkillA2 = _professionSkillA2.asStateFlow()

    private var _professionSkillA3 = MutableStateFlow(0)
    val professionSkillA3 = _professionSkillA3.asStateFlow()

    private var _professionSkillB1 = MutableStateFlow(0)
    val professionSkillB1 = _professionSkillB1.asStateFlow()

    private var _professionSkillB2 = MutableStateFlow(0)
    val professionSkillB2 = _professionSkillB2.asStateFlow()

    private var _professionSkillB3 = MutableStateFlow(0)
    val professionSkillB3 = _professionSkillB3.asStateFlow()

    private var _professionSkillC1 = MutableStateFlow(0)
    val professionSkillC1 = _professionSkillC1.asStateFlow()

    private var _professionSkillC2 = MutableStateFlow(0)
    val professionSkillC2 = _professionSkillC2.asStateFlow()

    private var _professionSkillC3 = MutableStateFlow(0)
    val professionSkillC3 = _professionSkillC3.asStateFlow()

    //Stats
    private var _intelligence = MutableStateFlow(0)
    val intelligence = _intelligence.asStateFlow()

    private var _ref = MutableStateFlow(0)
    val ref = _ref.asStateFlow()

    private var _dex = MutableStateFlow(0)
    val dex = _dex.asStateFlow()

    private var _body = MutableStateFlow(0)
    val body = _body.asStateFlow()

    private var _spd = MutableStateFlow(0)
    val spd = _spd.asStateFlow()

    private var _emp = MutableStateFlow(0)
    val emp = _emp.asStateFlow()

    private var _cra = MutableStateFlow(0)
    val cra = _cra.asStateFlow()

    private var _will = MutableStateFlow(0)
    val will = _will.asStateFlow()

    private var _luck = MutableStateFlow(0)
    val luck = _luck.asStateFlow()

    private var _stun = MutableStateFlow(0)
    val stun = _stun.asStateFlow()

    private var _run = MutableStateFlow(0)
    val run = _run.asStateFlow()

    private var _leap = MutableStateFlow(0)
    val leap = _leap.asStateFlow()

    private var _maxHP = MutableStateFlow(0)
    val maxHp = _maxHP.asStateFlow()

    private var _hp = MutableStateFlow(0)
    val hp = _hp.asStateFlow()

    private var _maxSta = MutableStateFlow(0)
    val maxSta = _maxSta.asStateFlow()

    private var _sta = MutableStateFlow(0)
    val sta = _sta.asStateFlow()

    private var _enc = MutableStateFlow(0)
    val enc = _enc.asStateFlow()

    private var _rec = MutableStateFlow(0)
    val rec = _rec.asStateFlow()

    private var _punch = MutableStateFlow("1d6 +2")
    val punch = _punch.asStateFlow()

    private var _kick = MutableStateFlow("1d6 +6")
    val kick = _kick.asStateFlow()


    //Skills
    private var _awareness = MutableStateFlow(0)
    val awareness = _awareness.asStateFlow()

    private var _business = MutableStateFlow(0)
    val business = _business.asStateFlow()

    private var _deduction = MutableStateFlow(0)
    val deduction = _deduction.asStateFlow()

    private var _education = MutableStateFlow(0)
    val education = _education.asStateFlow()

    private var _commonSpeech = MutableStateFlow(0)
    val commonSpeech = _commonSpeech.asStateFlow()

    private var _elderSpeech = MutableStateFlow(0)
    val elderSpeech = _elderSpeech.asStateFlow()

    private var _dwarven = MutableStateFlow(0)
    val dwarven = _dwarven.asStateFlow()

    private var _monsterLore = MutableStateFlow(0)
    val monsterLore = _monsterLore.asStateFlow()

    private var _socialEtiquette = MutableStateFlow(0)
    val socialEtiquette = _socialEtiquette.asStateFlow()

    private var _streetwise = MutableStateFlow(0)
    val streetwise = _streetwise.asStateFlow()

    private var _tactics = MutableStateFlow(0)
    val tactics = _tactics.asStateFlow()

    private var _teaching = MutableStateFlow(0)
    val teaching = _teaching.asStateFlow()

    private var _wildernessSurvival = MutableStateFlow(0)
    val wildernessSurvival = _wildernessSurvival.asStateFlow()

    private var _brawling = MutableStateFlow(0)
    val brawling = _brawling.asStateFlow()

    private var _dodgeEscape = MutableStateFlow(0)
    val dodgeEscape = _dodgeEscape.asStateFlow()

    private var _melee = MutableStateFlow(0)
    val melee = _melee.asStateFlow()

    private var _riding = MutableStateFlow(0)
    val riding = _riding.asStateFlow()

    private var _sailing = MutableStateFlow(0)
    val sailing = _sailing.asStateFlow()

    private var _smallBlades = MutableStateFlow(0)
    val smallBlades = _smallBlades.asStateFlow()

    private var _staffSpear = MutableStateFlow(0)
    val staffSpear = _staffSpear.asStateFlow()

    private var _swordsmanship = MutableStateFlow(0)
    val swordsmanship = _swordsmanship.asStateFlow()

    private var _archery = MutableStateFlow(0)
    val archery = _archery.asStateFlow()

    private var _athletics = MutableStateFlow(0)
    val athletics = _athletics.asStateFlow()

    private var _crossbow = MutableStateFlow(0)
    val crossbow = _crossbow.asStateFlow()

    private var _sleightOfHand = MutableStateFlow(0)
    val sleightOfHand = _sleightOfHand.asStateFlow()

    private var _stealth = MutableStateFlow(0)
    val stealth = _stealth.asStateFlow()

    private var _physique = MutableStateFlow(0)
    val physique = _physique.asStateFlow()

    private var _endurance = MutableStateFlow(0)
    val endurance = _endurance.asStateFlow()

    private var _charisma = MutableStateFlow(0)
    val charisma = _charisma.asStateFlow()

    private var _deceit = MutableStateFlow(0)
    val deceit = _deceit.asStateFlow()

    private var _fineArts = MutableStateFlow(0)
    val fineArts = _fineArts.asStateFlow()

    private var _gambling = MutableStateFlow(0)
    val gambling = _gambling.asStateFlow()

    private var _groomingAndStyle = MutableStateFlow(0)
    val groomingAndStyle = _groomingAndStyle.asStateFlow()

    private var _humanPerception = MutableStateFlow(0)
    val humanPerception = _humanPerception.asStateFlow()

    private var _leadership = MutableStateFlow(0)
    val leadership = _leadership.asStateFlow()

    private var _persuasion = MutableStateFlow(0)
    val persuasion = _persuasion.asStateFlow()

    private var _performance = MutableStateFlow(0)
    val performance = _performance.asStateFlow()

    private var _seduction = MutableStateFlow(0)
    val seduction = _seduction.asStateFlow()

    private var _alchemy = MutableStateFlow(0)
    val alchemy = _alchemy.asStateFlow()

    private var _crafting = MutableStateFlow(0)
    val crafting = _crafting.asStateFlow()

    private var _disguise = MutableStateFlow(0)
    val disguise = _disguise.asStateFlow()

    private var _firstAid = MutableStateFlow(0)
    val firstAid = _firstAid.asStateFlow()

    private var _forgery = MutableStateFlow(0)
    val forgery = _forgery.asStateFlow()

    private var _pickLock = MutableStateFlow(0)
    val pickLock = _pickLock.asStateFlow()

    private var _trapCrafting = MutableStateFlow(0)
    val trapCrafting = _trapCrafting.asStateFlow()

    private var _courage = MutableStateFlow(0)
    val courage = _courage.asStateFlow()

    private var _hexWeaving = MutableStateFlow(0)
    val hexWeaving = _hexWeaving.asStateFlow()

    private var _intimidation = MutableStateFlow(0)
    val intimidation = _intimidation.asStateFlow()

    private var _spellCasting = MutableStateFlow(0)
    val spellCasting = _spellCasting.asStateFlow()

    private var _resistMagic = MutableStateFlow(0)
    val resistMagic = _resistMagic.asStateFlow()

    private var _resistCoercion = MutableStateFlow(0)
    val resistCoercion = _resistCoercion.asStateFlow()

    private var _ritualCrafting = MutableStateFlow(0)
    val ritualCrafting = _ritualCrafting.asStateFlow()


    fun setInCharCreation(inCharacterCreation: Boolean){
        _inCharacterCreation.value = inCharacterCreation
    }

    fun addCharacter(character: Character){
        addCharacterUseCase.invoke(character)
    }

    fun setAge(age: String){
        _age.value = age
    }

    fun setRace(race: String){
        _race.value = race

        getRacePerkUseCase(race).onEach {
            _perks.value = it
        }.launchIn(viewModelScope)

    }

    fun setGender(gender: String){
        _gender.value = gender
    }

    fun setProfession(profession: String){

        _profession.value = when (profession){

            "Bard" -> Constants.Professions.BARD
            "Criminal" -> Constants.Professions.CRIMINAL
            "Craftsman" -> Constants.Professions.CRAFTSMAN
            "Doctor" -> Constants.Professions.DOCTOR
            "Mage" -> Constants.Professions.MAGE
            "Man At Arms" -> Constants.Professions.MAN_AT_ARMS
            "Priest" -> Constants.Professions.PRIEST
            "Witcher" -> Constants.Professions.WITCHER
            "Merchant" -> Constants.Professions.MERCHANT
            "Noble" -> Constants.Professions.NOBLE
            "Peasant" -> Constants.Professions.PEASANT

            else -> Constants.Professions.PEASANT
        }

        getDefiningSkillUseCase(profession).onEach { defSkill ->
            _definingSkill.value = defSkill
        }.launchIn(viewModelScope)

        getDefiningSkillInfoUseCase(definingSkill.value).onEach { info ->
            Log.d("Test", info)
            _definingSkillInfo.value = info
        }.launchIn(viewModelScope)
    }

    fun onSkillChange(skill: String, increase: Boolean){

        when (skill){
            "Awareness" -> {
                val pair = onSkillChangeUseCase(_awareness.value, _ip.value, increase, _inCharacterCreation.value).data
                if (pair != null) {
                    _awareness.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Business" -> {
                val pair = onSkillChangeUseCase(_business.value, _ip.value, increase, _inCharacterCreation.value).data
                if (pair != null) {
                    _business.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Deduction" -> {
                val pair = onSkillChangeUseCase(_deduction.value, _ip.value, increase, _inCharacterCreation.value).data
                if (pair != null) {
                    _deduction.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Education" -> {
                val pair = onSkillChangeUseCase(_education.value, _ip.value, increase, _inCharacterCreation.value).data
                if (pair != null) {
                    _education.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Common Speech" -> {
                val pair = onSkillChangeUseCase(_commonSpeech.value, _ip.value, increase, _inCharacterCreation.value).data
                if (pair != null) {
                    _commonSpeech.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Elder Speech" -> {
                val pair = onSkillChangeUseCase(_elderSpeech.value, _ip.value, increase, _inCharacterCreation.value).data
                if (pair != null) {
                    _elderSpeech.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Dwarven" -> {
                val pair = onSkillChangeUseCase(_dwarven.value, _ip.value, increase, _inCharacterCreation.value).data
                if (pair != null) {
                    _dwarven.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Monster Lore" -> {
                val pair = onSkillChangeUseCase(_monsterLore.value, _ip.value, increase, _inCharacterCreation.value).data
                if (pair != null) {
                    _monsterLore.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Social Etiquette" -> {
                val pair = onSkillChangeUseCase(_socialEtiquette.value, _ip.value, increase, _inCharacterCreation.value).data
                if (pair != null) {
                    _socialEtiquette.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Streetwise" -> {
                val pair = onSkillChangeUseCase(_streetwise.value, _ip.value, increase, _inCharacterCreation.value).data
                if (pair != null) {
                    _streetwise.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Tactics" -> {
                val pair = onSkillChangeUseCase(_tactics.value, _ip.value, increase, _inCharacterCreation.value).data
                if (pair != null) {
                    _tactics.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Teaching" -> {
                val pair = onSkillChangeUseCase(_teaching.value, _ip.value, increase, _inCharacterCreation.value).data
                if (pair != null) {
                    _teaching.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Wilderness Survival" -> {
                val pair = onSkillChangeUseCase(_wildernessSurvival.value, _ip.value, increase, _inCharacterCreation.value).data
                if (pair != null) {
                    _wildernessSurvival.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Brawling" -> {
                val pair = onSkillChangeUseCase(_brawling.value, _ip.value, increase, _inCharacterCreation.value).data
                if (pair != null) {
                    _brawling.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Dodge/Escape" -> {
                val pair = onSkillChangeUseCase(_dodgeEscape.value, _ip.value, increase, _inCharacterCreation.value).data
                if (pair != null) {
                    _dodgeEscape.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Melee" -> {
                val pair = onSkillChangeUseCase(_melee.value, _ip.value, increase, _inCharacterCreation.value).data
                if (pair != null) {
                    _melee.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Riding" -> {
                val pair = onSkillChangeUseCase(_riding.value, _ip.value, increase, _inCharacterCreation.value).data
                if (pair != null) {
                    _riding.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Sailing" -> {
                val pair = onSkillChangeUseCase(_sailing.value, _ip.value, increase, _inCharacterCreation.value).data
                if (pair != null) {
                    _sailing.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Small Blades" -> {
                val pair = onSkillChangeUseCase(_smallBlades.value, _ip.value, increase, _inCharacterCreation.value).data
                if (pair != null) {
                    _smallBlades.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Staff/Spear" -> {
                val pair = onSkillChangeUseCase(_staffSpear.value, _ip.value, increase, _inCharacterCreation.value).data
                if (pair != null) {
                    _staffSpear.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Swordsmanship" -> {
                val pair = onSkillChangeUseCase(_swordsmanship.value, _ip.value, increase, _inCharacterCreation.value).data
                if (pair != null) {
                    _swordsmanship.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Archery" -> {
                val pair = onSkillChangeUseCase(_archery.value, _ip.value, increase, _inCharacterCreation.value).data
                if (pair != null) {
                    _archery.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Athletics" -> {
                val pair = onSkillChangeUseCase(_athletics.value, _ip.value, increase, _inCharacterCreation.value).data
                if (pair != null) {
                    _athletics.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Crossbow" -> {
                val pair = onSkillChangeUseCase(_crossbow.value, _ip.value, increase, _inCharacterCreation.value).data
                if (pair != null) {
                    _crossbow.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Sleight of Hand" -> {
                val pair = onSkillChangeUseCase(_sleightOfHand.value, _ip.value, increase, _inCharacterCreation.value).data
                if (pair != null) {
                    _sleightOfHand.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Stealth" -> {
                val pair = onSkillChangeUseCase(_stealth.value, _ip.value, increase, _inCharacterCreation.value).data
                if (pair != null) {
                    _stealth.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Physique" -> {
                val pair = onSkillChangeUseCase(_physique.value, _ip.value, increase, _inCharacterCreation.value).data
                if (pair != null) {
                    _physique.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Endurance" -> {
                val pair = onSkillChangeUseCase(_endurance.value, _ip.value, increase, _inCharacterCreation.value).data
                if (pair != null) {
                    _endurance.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Charisma" -> {
                val pair = onSkillChangeUseCase(_charisma.value, _ip.value, increase, _inCharacterCreation.value).data
                if (pair != null) {
                    _charisma.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Deceit" -> {
                val pair = onSkillChangeUseCase(_deceit.value, _ip.value, increase, _inCharacterCreation.value).data
                if (pair != null) {
                    _deceit.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Fine Arts" -> {
                val pair = onSkillChangeUseCase(_fineArts.value, _ip.value, increase, _inCharacterCreation.value).data
                if (pair != null) {
                    _fineArts.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Gambling" -> {
                val pair = onSkillChangeUseCase(_gambling.value, _ip.value, increase, _inCharacterCreation.value).data
                if (pair != null) {
                    _gambling.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Grooming and Style" -> {
                val pair = onSkillChangeUseCase(_groomingAndStyle.value, _ip.value, increase, _inCharacterCreation.value).data
                if (pair != null) {
                    _groomingAndStyle.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Human Perception" -> {
                val pair = onSkillChangeUseCase(_humanPerception.value, _ip.value, increase, _inCharacterCreation.value).data
                if (pair != null) {
                    _humanPerception.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Leadership" -> {
                val pair = onSkillChangeUseCase(_leadership.value, _ip.value, increase, _inCharacterCreation.value).data
                if (pair != null) {
                    _leadership.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Persuasion" -> {
                val pair = onSkillChangeUseCase(_persuasion.value, _ip.value, increase, _inCharacterCreation.value).data
                if (pair != null) {
                    _persuasion.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Performance" -> {
                val pair = onSkillChangeUseCase(_performance.value, _ip.value, increase, _inCharacterCreation.value).data
                if (pair != null) {
                    _performance.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Seduction" -> {
                val pair = onSkillChangeUseCase(_seduction.value, _ip.value, increase, _inCharacterCreation.value).data
                if (pair != null) {
                    _seduction.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Alchemy" -> {
                val pair = onSkillChangeUseCase(_alchemy.value, _ip.value, increase, _inCharacterCreation.value).data
                if (pair != null) {
                    _alchemy.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Crafting" -> {
                val pair = onSkillChangeUseCase(_crafting.value, _ip.value, increase, _inCharacterCreation.value).data
                if (pair != null) {
                    _crafting.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Disguise" -> {
                val pair = onSkillChangeUseCase(_disguise.value, _ip.value, increase, _inCharacterCreation.value).data
                if (pair != null) {
                    _disguise.value = pair.second
                    _ip.value = pair.first
                }
            }
            "First Aid" -> {
                val pair = onSkillChangeUseCase(_firstAid.value, _ip.value, increase, _inCharacterCreation.value).data
                if (pair != null) {
                    _firstAid.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Forgery" -> {
                val pair = onSkillChangeUseCase(_forgery.value, _ip.value, increase, _inCharacterCreation.value).data
                if (pair != null) {
                    _forgery.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Pick Lock" -> {
                val pair = onSkillChangeUseCase(_pickLock.value, _ip.value, increase, _inCharacterCreation.value).data
                if (pair != null) {
                    _pickLock.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Trap Crafting" -> {
                val pair = onSkillChangeUseCase(_trapCrafting.value, _ip.value, increase, _inCharacterCreation.value).data
                if (pair != null) {
                    _trapCrafting.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Courage" -> {
                val pair = onSkillChangeUseCase(_courage.value, _ip.value, increase, _inCharacterCreation.value).data
                if (pair != null) {
                    _courage.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Hex Weaving" -> {
                val pair = onSkillChangeUseCase(_hexWeaving.value, _ip.value, increase, _inCharacterCreation.value).data
                if (pair != null) {
                    _hexWeaving.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Intimidation" -> {
                val pair = onSkillChangeUseCase(_intimidation.value, _ip.value, increase, _inCharacterCreation.value).data
                if (pair != null) {
                    _intimidation.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Spell Casting" -> {
                val pair = onSkillChangeUseCase(_spellCasting.value, _ip.value, increase, _inCharacterCreation.value).data
                if (pair != null) {
                    _spellCasting.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Resist Magic" -> {
                val pair = onSkillChangeUseCase(_resistMagic.value, _ip.value, increase, _inCharacterCreation.value).data
                if (pair != null) {
                    _resistMagic.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Resist Coercion" -> {
                val pair = onSkillChangeUseCase(_resistCoercion.value, _ip.value, increase, _inCharacterCreation.value).data
                if (pair != null) {
                    _resistCoercion.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Ritual Crafting" -> {
                val pair = onSkillChangeUseCase(_ritualCrafting.value, _ip.value, increase, _inCharacterCreation.value).data
                if (pair != null) {
                    _ritualCrafting.value = pair.second
                    _ip.value = pair.first
                }
            }
        }
    }
}