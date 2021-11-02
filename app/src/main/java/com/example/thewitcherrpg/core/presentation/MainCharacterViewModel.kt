package com.example.thewitcherrpg.core.presentation

import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thewitcherrpg.core.Constants
import com.example.thewitcherrpg.core.Resource
import com.example.thewitcherrpg.feature_character_creation.domain.use_cases.*
import com.example.thewitcherrpg.core.domain.model.Character
import com.example.thewitcherrpg.feature_character_creation.presentation.SaveCharacterState
import com.example.thewitcherrpg.feature_character_list.domain.use_cases.GetCharacterUseCase
import com.example.thewitcherrpg.feature_character_sheet.domain.use_cases.skills.OnSkillChangeUseCase
import com.example.thewitcherrpg.feature_character_sheet.domain.use_cases.stats.OnStatChangeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MainCharacterViewModel @Inject constructor(
    private val application: Application,
    private val characterCreationUseCases: CharacterCreationUseCases,
    private val onSkillChangeUseCase: OnSkillChangeUseCase,
    private val onStatChangeUseCase: OnStatChangeUseCase,
    private val getCharacterUseCase: GetCharacterUseCase
) : ViewModel() {

    private var _id = MutableStateFlow(70)
    val id = _id.asStateFlow()

    //ViewModel States
    private val _inCharacterCreation = mutableStateOf(false)

    private val _addState = MutableStateFlow(SaveCharacterState())
    val addState = _addState.asStateFlow()

    private val _image = mutableStateOf("")
    val image: State<String> = _image

    val name = MutableLiveData<String>("")

    val age = MutableLiveData("")

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

    private var _crowns = MutableStateFlow(0)
    val crowns = _crowns.asStateFlow()

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


    fun setInCharCreation(inCharacterCreation: Boolean) {
        _inCharacterCreation.value = inCharacterCreation
    }

    fun addCharacter() {

        characterCreationUseCases.addCharacterUseCase(
            Character(
                id = 0,
                imagePath = _image.value,
                name = name.value!!,
                iP = _ip.value,
                race = _race.value,
                gender = _gender.value,
                age = age.value.toString().toInt(),
                profession = _profession.value,
                definingSkill = _definingSkill.value,
                intelligence = _intelligence.value,
                reflex = _ref.value,
                dexterity = _dex.value,
                body = _body.value,
                speed = _spd.value,
                empathy = _emp.value,
                craftsmanship = _cra.value,
                will = _will.value,
                luck = _luck.value,
                stun = _stun.value,
                run = _run.value,
                leap = _leap.value,
                MaxHP = _maxHP.value,
                hp = _hp.value,
                MaxStamina = _maxSta.value,
                stamina = _sta.value,
                encumbrance = _enc.value,
                recovery = _rec.value,
                punch = _punch.value,
                kick = _kick.value,
                awareness = _awareness.value,
                business = _business.value,
                deduction = _deduction.value,
                education = _education.value,
                commonSpeech = _commonSpeech.value,
                elderSpeech = _elderSpeech.value,
                dwarven = _dwarven.value,
                monsterLore = _monsterLore.value,
                socialEtiquette = _socialEtiquette.value,
                streetwise = _streetwise.value,
                tactics = _tactics.value,
                teaching = _teaching.value,
                wildernessSurvival = _wildernessSurvival.value,
                brawling = _brawling.value,
                dodgeEscape = _dodgeEscape.value,
                melee = _melee.value,
                riding = _riding.value,
                sailing = _sailing.value,
                smallBlades = _smallBlades.value,
                staffSpear = staffSpear.value,
                swordsmanship = _swordsmanship.value,
                archery = _archery.value,
                athletics = _athletics.value,
                crossbow = _crossbow.value,
                sleightOfHand = _sleightOfHand.value,
                stealth = _stealth.value,
                physique = _physique.value,
                endurance = _endurance.value,
                charisma = _charisma.value,
                deceit = _deceit.value,
                fineArts = _fineArts.value,
                gambling = _gambling.value,
                groomingAndStyle = _groomingAndStyle.value,
                humanPerception = _humanPerception.value,
                leadership = _leadership.value,
                persuasion = _persuasion.value,
                performance = _performance.value,
                seduction = _seduction.value,
                alchemy = _alchemy.value,
                crafting = _crafting.value,
                disguise = _disguise.value,
                firstAid = _firstAid.value,
                forgery = _forgery.value,
                pickLock = _pickLock.value,
                trapCrafting = _trapCrafting.value,
                courage = _courage.value,
                hexWeaving = _hexWeaving.value,
                intimidation = _intimidation.value,
                spellCasting = _spellCasting.value,
                resistMagic = _resistMagic.value,
                resistCoercion = _resistCoercion.value,
                ritualCrafting = _ritualCrafting.value
            )

        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _addState.value = SaveCharacterState(success = true)
                }
                is Resource.Error -> {
                    _addState.value = SaveCharacterState(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    _addState.value = SaveCharacterState(isLoading = true)
                }
            }

        }.launchIn(viewModelScope)
    }

    fun getCharacter(id: Int) {
        getCharacterUseCase(id).onEach { characterData ->
            _id.value = characterData.id
            _image.value = characterData.imagePath
            name.value = characterData.name
            _ip.value = characterData.iP
            _race.value = characterData.race
            _gender.value = characterData.gender
            age.value = characterData.age.toString()
            _profession.value = characterData.profession
            _definingSkill.value = characterData.definingSkill
            _crowns.value = characterData.crowns

            //Profession Skills
            _professionSkillA1.value = characterData.professionSkillA1
            _professionSkillA2.value = characterData.professionSkillA2
            _professionSkillA3.value = characterData.professionSkillA3
            _professionSkillB1.value = characterData.professionSkillB1
            _professionSkillB2.value = characterData.professionSkillB2
            _professionSkillB3.value = characterData.professionSkillB3
            _professionSkillC1.value = characterData.professionSkillC1
            _professionSkillC2.value = characterData.professionSkillC2
            _professionSkillC3.value = characterData.professionSkillC3

            //Stats
            _intelligence.value = characterData.intelligence
            _ref.value = characterData.reflex
            _dex.value = characterData.dexterity
            _body.value = characterData.body
            _spd.value = characterData.speed
            _emp.value = characterData.empathy
            _cra.value = characterData.craftsmanship
            _will.value = characterData.will
            _luck.value = characterData.luck
            _stun.value = characterData.stun
            _run.value = characterData.run
            _leap.value = characterData.leap
            _maxHP.value = characterData.MaxHP
            _hp.value = characterData.hp
            _maxSta.value = characterData.MaxStamina
            _sta.value = characterData.stamina
            _enc.value = characterData.encumbrance
            _rec.value = characterData.recovery
            _punch.value = characterData.punch
            _kick.value = characterData.kick

            //Skills
            _awareness.value = characterData.awareness
            _business.value = characterData.business
            _deduction.value = characterData.deduction
            _education.value = characterData.education
            _commonSpeech.value = characterData.commonSpeech
            _elderSpeech.value = characterData.elderSpeech
            _dwarven.value = characterData.dwarven
            _monsterLore.value = characterData.monsterLore
            _socialEtiquette.value = characterData.socialEtiquette
            _streetwise.value = characterData.streetwise
            _tactics.value = characterData.tactics
            _teaching.value = characterData.teaching
            _wildernessSurvival.value = characterData.wildernessSurvival
            _brawling.value = characterData.brawling
            _dodgeEscape.value = characterData.dodgeEscape
            _melee.value = characterData.melee
            _riding.value = characterData.riding
            _sailing.value = characterData.sailing
            _smallBlades.value = characterData.smallBlades
            _staffSpear.value = characterData.staffSpear
            _swordsmanship.value = characterData.swordsmanship
            _archery.value = characterData.archery
            _athletics.value = characterData.athletics
            _crossbow.value = characterData.crossbow
            _sleightOfHand.value = characterData.sleightOfHand
            _stealth.value = characterData.stealth
            _physique.value = characterData.physique
            _endurance.value = characterData.endurance
            _charisma.value = characterData.charisma
            _deceit.value = characterData.deceit
            _fineArts.value = characterData.fineArts
            _gambling.value = characterData.gambling
            _groomingAndStyle.value = characterData.groomingAndStyle
            _humanPerception.value = characterData.humanPerception
            _leadership.value = characterData.leadership
            _persuasion.value = characterData.persuasion
            _performance.value = characterData.performance
            _seduction.value = characterData.seduction
            _alchemy.value = characterData.alchemy
            _crafting.value = characterData.crafting
            _disguise.value = characterData.disguise
            _firstAid.value = characterData.firstAid
            _forgery.value = characterData.forgery
            _pickLock.value = characterData.pickLock
            _trapCrafting.value = characterData.trapCrafting
            _courage.value = characterData.courage
            _hexWeaving.value = characterData.hexWeaving
            _intimidation.value = characterData.intimidation
            _spellCasting.value = characterData.spellCasting
            _resistMagic.value = characterData.resistMagic
            _resistCoercion.value = characterData.resistCoercion
            _ritualCrafting.value = characterData.ritualCrafting

            /*//Magic
            _vigor.value = characterData.vigor

            //Mages
            _noviceSpellList.value = characterData.noviceSpells
            _journeymanSpellList.value = characterData.journeymanSpells
            _masterSpellList.value = characterData.masterSpells

            //Priests
            _noviceDruidInvocations.value = characterData.noviceDruidInvocations
            _journeymanDruidInvocations.value = characterData.journeymanDruidInvocations
            _masterDruidInvocations.value = characterData.masterDruidInvocations

            _novicePreacherInvocations.value = characterData.novicePreacherInvocations
            _journeymanPreacherInvocations.value = characterData.journeymanPreacherInvocations
            _masterPreacherInvocations.value = characterData.masterPreacherInvocations

            _archPriestInvocations.value = characterData.archPriestInvocations

            //Signs
            _basicSigns.value = characterData.basicSigns
            _alternateSigns.value = characterData.alternateSigns

            //Rituals
            _noviceRitualList.value = characterData.noviceRituals
            _journeymanRitualList.value = characterData.journeymanRituals
            _masterRitualList.value = characterData.masterRituals

            //Hexes
            _hexesList.value = characterData.hexes

            //Equipment
            _headEquipment.value = characterData.headEquipment
            _equippedHead.value = characterData.equippedHead

            _chestEquipment.value = characterData.chestEquipment
            _equippedChest.value = characterData.equippedChest

            _legEquipment.value = characterData.legEquipment
            _equippedLegs.value = characterData.equippedLegs*/
        }.launchIn(viewModelScope)
    }

    fun setImage(path: String){
        _image.value = path
    }

    fun saveImageToInternalStorage(bitmapImage: Bitmap): String? {
        val cw = ContextWrapper(application.applicationContext)
        // path to /data/data/thewitherrpg/app_data/imageDir
        val directory: File = cw.getDir("imageDir", Context.MODE_PRIVATE)
        // Create imageDir
        val myPath = File(directory, _id.value.toString() + ".jpeg")
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(myPath)
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fos)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                fos?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return directory.absolutePath
    }

    fun setRace(race: String) {
        _race.value = race

        characterCreationUseCases.getRacePerkUseCase(race).onEach {
            _perks.value = it
        }.launchIn(viewModelScope)
    }

    fun setGender(gender: String) {
        _gender.value = gender
    }

    fun setProfession(profession: String) {

        _profession.value = when (profession) {

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

        characterCreationUseCases.getDefiningSkillUseCase(profession).onEach { defSkill ->
            _definingSkill.value = defSkill
        }.launchIn(viewModelScope)

        characterCreationUseCases.getDefiningSkillInfoUseCase(definingSkill.value).onEach { info ->
            Log.d("Test", info)
            _definingSkillInfo.value = info
        }.launchIn(viewModelScope)
    }

    fun onSkillChange(skill: String, increase: Boolean) {

        when (skill) {
            "Awareness" -> {
                val pair = onSkillChangeUseCase(
                    _awareness.value,
                    _ip.value,
                    increase,
                    _inCharacterCreation.value
                ).data
                if (pair != null) {
                    _awareness.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Business" -> {
                val pair = onSkillChangeUseCase(
                    _business.value,
                    _ip.value,
                    increase,
                    _inCharacterCreation.value
                ).data
                if (pair != null) {
                    _business.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Deduction" -> {
                val pair = onSkillChangeUseCase(
                    _deduction.value,
                    _ip.value,
                    increase,
                    _inCharacterCreation.value
                ).data
                if (pair != null) {
                    _deduction.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Education" -> {
                val pair = onSkillChangeUseCase(
                    _education.value,
                    _ip.value,
                    increase,
                    _inCharacterCreation.value
                ).data
                if (pair != null) {
                    _education.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Common Speech" -> {
                val pair = onSkillChangeUseCase(
                    _commonSpeech.value,
                    _ip.value,
                    increase,
                    _inCharacterCreation.value
                ).data
                if (pair != null) {
                    _commonSpeech.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Elder Speech" -> {
                val pair = onSkillChangeUseCase(
                    _elderSpeech.value,
                    _ip.value,
                    increase,
                    _inCharacterCreation.value
                ).data
                if (pair != null) {
                    _elderSpeech.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Dwarven" -> {
                val pair = onSkillChangeUseCase(
                    _dwarven.value,
                    _ip.value,
                    increase,
                    _inCharacterCreation.value
                ).data
                if (pair != null) {
                    _dwarven.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Monster Lore" -> {
                val pair = onSkillChangeUseCase(
                    _monsterLore.value,
                    _ip.value,
                    increase,
                    _inCharacterCreation.value
                ).data
                if (pair != null) {
                    _monsterLore.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Social Etiquette" -> {
                val pair = onSkillChangeUseCase(
                    _socialEtiquette.value,
                    _ip.value,
                    increase,
                    _inCharacterCreation.value
                ).data
                if (pair != null) {
                    _socialEtiquette.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Streetwise" -> {
                val pair = onSkillChangeUseCase(
                    _streetwise.value,
                    _ip.value,
                    increase,
                    _inCharacterCreation.value
                ).data
                if (pair != null) {
                    _streetwise.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Tactics" -> {
                val pair = onSkillChangeUseCase(
                    _tactics.value,
                    _ip.value,
                    increase,
                    _inCharacterCreation.value
                ).data
                if (pair != null) {
                    _tactics.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Teaching" -> {
                val pair = onSkillChangeUseCase(
                    _teaching.value,
                    _ip.value,
                    increase,
                    _inCharacterCreation.value
                ).data
                if (pair != null) {
                    _teaching.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Wilderness Survival" -> {
                val pair = onSkillChangeUseCase(
                    _wildernessSurvival.value,
                    _ip.value,
                    increase,
                    _inCharacterCreation.value
                ).data
                if (pair != null) {
                    _wildernessSurvival.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Brawling" -> {
                val pair = onSkillChangeUseCase(
                    _brawling.value,
                    _ip.value,
                    increase,
                    _inCharacterCreation.value
                ).data
                if (pair != null) {
                    _brawling.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Dodge/Escape" -> {
                val pair = onSkillChangeUseCase(
                    _dodgeEscape.value,
                    _ip.value,
                    increase,
                    _inCharacterCreation.value
                ).data
                if (pair != null) {
                    _dodgeEscape.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Melee" -> {
                val pair = onSkillChangeUseCase(
                    _melee.value,
                    _ip.value,
                    increase,
                    _inCharacterCreation.value
                ).data
                if (pair != null) {
                    _melee.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Riding" -> {
                val pair = onSkillChangeUseCase(
                    _riding.value,
                    _ip.value,
                    increase,
                    _inCharacterCreation.value
                ).data
                if (pair != null) {
                    _riding.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Sailing" -> {
                val pair = onSkillChangeUseCase(
                    _sailing.value,
                    _ip.value,
                    increase,
                    _inCharacterCreation.value
                ).data
                if (pair != null) {
                    _sailing.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Small Blades" -> {
                val pair = onSkillChangeUseCase(
                    _smallBlades.value,
                    _ip.value,
                    increase,
                    _inCharacterCreation.value
                ).data
                if (pair != null) {
                    _smallBlades.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Staff/Spear" -> {
                val pair = onSkillChangeUseCase(
                    _staffSpear.value,
                    _ip.value,
                    increase,
                    _inCharacterCreation.value
                ).data
                if (pair != null) {
                    _staffSpear.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Swordsmanship" -> {
                val pair = onSkillChangeUseCase(
                    _swordsmanship.value,
                    _ip.value,
                    increase,
                    _inCharacterCreation.value
                ).data
                if (pair != null) {
                    _swordsmanship.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Archery" -> {
                val pair = onSkillChangeUseCase(
                    _archery.value,
                    _ip.value,
                    increase,
                    _inCharacterCreation.value
                ).data
                if (pair != null) {
                    _archery.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Athletics" -> {
                val pair = onSkillChangeUseCase(
                    _athletics.value,
                    _ip.value,
                    increase,
                    _inCharacterCreation.value
                ).data
                if (pair != null) {
                    _athletics.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Crossbow" -> {
                val pair = onSkillChangeUseCase(
                    _crossbow.value,
                    _ip.value,
                    increase,
                    _inCharacterCreation.value
                ).data
                if (pair != null) {
                    _crossbow.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Sleight of Hand" -> {
                val pair = onSkillChangeUseCase(
                    _sleightOfHand.value,
                    _ip.value,
                    increase,
                    _inCharacterCreation.value
                ).data
                if (pair != null) {
                    _sleightOfHand.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Stealth" -> {
                val pair = onSkillChangeUseCase(
                    _stealth.value,
                    _ip.value,
                    increase,
                    _inCharacterCreation.value
                ).data
                if (pair != null) {
                    _stealth.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Physique" -> {
                val pair = onSkillChangeUseCase(
                    _physique.value,
                    _ip.value,
                    increase,
                    _inCharacterCreation.value
                ).data
                if (pair != null) {
                    _physique.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Endurance" -> {
                val pair = onSkillChangeUseCase(
                    _endurance.value,
                    _ip.value,
                    increase,
                    _inCharacterCreation.value
                ).data
                if (pair != null) {
                    _endurance.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Charisma" -> {
                val pair = onSkillChangeUseCase(
                    _charisma.value,
                    _ip.value,
                    increase,
                    _inCharacterCreation.value
                ).data
                if (pair != null) {
                    _charisma.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Deceit" -> {
                val pair = onSkillChangeUseCase(
                    _deceit.value,
                    _ip.value,
                    increase,
                    _inCharacterCreation.value
                ).data
                if (pair != null) {
                    _deceit.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Fine Arts" -> {
                val pair = onSkillChangeUseCase(
                    _fineArts.value,
                    _ip.value,
                    increase,
                    _inCharacterCreation.value
                ).data
                if (pair != null) {
                    _fineArts.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Gambling" -> {
                val pair = onSkillChangeUseCase(
                    _gambling.value,
                    _ip.value,
                    increase,
                    _inCharacterCreation.value
                ).data
                if (pair != null) {
                    _gambling.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Grooming and Style" -> {
                val pair = onSkillChangeUseCase(
                    _groomingAndStyle.value,
                    _ip.value,
                    increase,
                    _inCharacterCreation.value
                ).data
                if (pair != null) {
                    _groomingAndStyle.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Human Perception" -> {
                val pair = onSkillChangeUseCase(
                    _humanPerception.value,
                    _ip.value,
                    increase,
                    _inCharacterCreation.value
                ).data
                if (pair != null) {
                    _humanPerception.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Leadership" -> {
                val pair = onSkillChangeUseCase(
                    _leadership.value,
                    _ip.value,
                    increase,
                    _inCharacterCreation.value
                ).data
                if (pair != null) {
                    _leadership.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Persuasion" -> {
                val pair = onSkillChangeUseCase(
                    _persuasion.value,
                    _ip.value,
                    increase,
                    _inCharacterCreation.value
                ).data
                if (pair != null) {
                    _persuasion.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Performance" -> {
                val pair = onSkillChangeUseCase(
                    _performance.value,
                    _ip.value,
                    increase,
                    _inCharacterCreation.value
                ).data
                if (pair != null) {
                    _performance.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Seduction" -> {
                val pair = onSkillChangeUseCase(
                    _seduction.value,
                    _ip.value,
                    increase,
                    _inCharacterCreation.value
                ).data
                if (pair != null) {
                    _seduction.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Alchemy" -> {
                val pair = onSkillChangeUseCase(
                    _alchemy.value,
                    _ip.value,
                    increase,
                    _inCharacterCreation.value
                ).data
                if (pair != null) {
                    _alchemy.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Crafting" -> {
                val pair = onSkillChangeUseCase(
                    _crafting.value,
                    _ip.value,
                    increase,
                    _inCharacterCreation.value
                ).data
                if (pair != null) {
                    _crafting.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Disguise" -> {
                val pair = onSkillChangeUseCase(
                    _disguise.value,
                    _ip.value,
                    increase,
                    _inCharacterCreation.value
                ).data
                if (pair != null) {
                    _disguise.value = pair.second
                    _ip.value = pair.first
                }
            }
            "First Aid" -> {
                val pair = onSkillChangeUseCase(
                    _firstAid.value,
                    _ip.value,
                    increase,
                    _inCharacterCreation.value
                ).data
                if (pair != null) {
                    _firstAid.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Forgery" -> {
                val pair = onSkillChangeUseCase(
                    _forgery.value,
                    _ip.value,
                    increase,
                    _inCharacterCreation.value
                ).data
                if (pair != null) {
                    _forgery.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Pick Lock" -> {
                val pair = onSkillChangeUseCase(
                    _pickLock.value,
                    _ip.value,
                    increase,
                    _inCharacterCreation.value
                ).data
                if (pair != null) {
                    _pickLock.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Trap Crafting" -> {
                val pair = onSkillChangeUseCase(
                    _trapCrafting.value,
                    _ip.value,
                    increase,
                    _inCharacterCreation.value
                ).data
                if (pair != null) {
                    _trapCrafting.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Courage" -> {
                val pair = onSkillChangeUseCase(
                    _courage.value,
                    _ip.value,
                    increase,
                    _inCharacterCreation.value
                ).data
                if (pair != null) {
                    _courage.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Hex Weaving" -> {
                val pair = onSkillChangeUseCase(
                    _hexWeaving.value,
                    _ip.value,
                    increase,
                    _inCharacterCreation.value
                ).data
                if (pair != null) {
                    _hexWeaving.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Intimidation" -> {
                val pair = onSkillChangeUseCase(
                    _intimidation.value,
                    _ip.value,
                    increase,
                    _inCharacterCreation.value
                ).data
                if (pair != null) {
                    _intimidation.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Spell Casting" -> {
                val pair = onSkillChangeUseCase(
                    _spellCasting.value,
                    _ip.value,
                    increase,
                    _inCharacterCreation.value
                ).data
                if (pair != null) {
                    _spellCasting.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Resist Magic" -> {
                val pair = onSkillChangeUseCase(
                    _resistMagic.value,
                    _ip.value,
                    increase,
                    _inCharacterCreation.value
                ).data
                if (pair != null) {
                    _resistMagic.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Resist Coercion" -> {
                val pair = onSkillChangeUseCase(
                    _resistCoercion.value,
                    _ip.value,
                    increase,
                    _inCharacterCreation.value
                ).data
                if (pair != null) {
                    _resistCoercion.value = pair.second
                    _ip.value = pair.first
                }
            }
            "Ritual Crafting" -> {
                val pair = onSkillChangeUseCase(
                    _ritualCrafting.value,
                    _ip.value,
                    increase,
                    _inCharacterCreation.value
                ).data
                if (pair != null) {
                    _ritualCrafting.value = pair.second
                    _ip.value = pair.first
                }
            }
        }
    }

    fun onStatChange(stat: String, increase: Boolean) {

        when (stat) {

            "INT" -> {
                val pair = onStatChangeUseCase(
                    _intelligence.value,
                    _ip.value,
                    increase,
                    _inCharacterCreation.value
                ).data
                if (pair != null) {
                    _intelligence.value = pair.second
                    _ip.value = pair.first
                }
            }
            "REF" -> {
                val pair = onStatChangeUseCase(
                    _ref.value,
                    _ip.value,
                    increase,
                    _inCharacterCreation.value
                ).data
                if (pair != null) {
                    _ref.value = pair.second
                    _ip.value = pair.first
                }
            }
            "DEX" -> {
                val pair = onStatChangeUseCase(
                    _dex.value,
                    _ip.value,
                    increase,
                    _inCharacterCreation.value
                ).data
                if (pair != null) {
                    _dex.value = pair.second
                    _ip.value = pair.first
                }
            }
            "BODY" -> {
                val pair = onStatChangeUseCase(
                    _body.value,
                    _ip.value,
                    increase,
                    _inCharacterCreation.value
                ).data
                if (pair != null) {
                    _body.value = pair.second
                    _ip.value = pair.first
                }
            }
            "SPD" -> {
                val pair = onStatChangeUseCase(
                    _spd.value,
                    _ip.value,
                    increase,
                    _inCharacterCreation.value
                ).data
                if (pair != null) {
                    _spd.value = pair.second
                    _ip.value = pair.first
                }
            }
            "EMP" -> {
                val pair = onStatChangeUseCase(
                    _emp.value,
                    _ip.value,
                    increase,
                    _inCharacterCreation.value
                ).data
                if (pair != null) {
                    _emp.value = pair.second
                    _ip.value = pair.first
                }
            }
            "CRA" -> {
                val pair = onStatChangeUseCase(
                    _cra.value,
                    _ip.value,
                    increase,
                    _inCharacterCreation.value
                ).data
                if (pair != null) {
                    _cra.value = pair.second
                    _ip.value = pair.first
                }
            }
            "WILL" -> {
                val pair = onStatChangeUseCase(
                    _will.value,
                    _ip.value,
                    increase,
                    _inCharacterCreation.value
                ).data
                if (pair != null) {
                    _will.value = pair.second
                    _ip.value = pair.first
                }
            }
            "LUCK" -> {
                val pair = onStatChangeUseCase(
                    _luck.value,
                    _ip.value,
                    increase,
                    _inCharacterCreation.value
                ).data
                if (pair != null) {
                    _luck.value = pair.second
                    _ip.value = pair.first
                }
            }
            "STUN" -> {
                val pair = onStatChangeUseCase(
                    _stun.value,
                    _ip.value,
                    increase,
                    _inCharacterCreation.value
                ).data
                if (pair != null) {
                    _stun.value = pair.second
                    _ip.value = pair.first
                }
            }
            "RUN" -> {
                val pair = onStatChangeUseCase(
                    _run.value,
                    _ip.value,
                    increase,
                    _inCharacterCreation.value
                ).data
                if (pair != null) {
                    _run.value = pair.second
                    _ip.value = pair.first
                }
            }
            "LEAP" -> {
                val pair = onStatChangeUseCase(
                    _leap.value,
                    _ip.value,
                    increase,
                    _inCharacterCreation.value
                ).data
                if (pair != null) {
                    _leap.value = pair.second
                    _ip.value = pair.first
                }
            }
            "MaxHP" -> {
                val pair = onStatChangeUseCase(
                    _maxHP.value,
                    _ip.value,
                    increase,
                    _inCharacterCreation.value
                ).data
                if (pair != null) {
                    _maxHP.value = pair.second
                    _ip.value = pair.first
                }
            }
            "MaxSTA" -> {
                val pair = onStatChangeUseCase(
                    _maxSta.value,
                    _ip.value,
                    increase,
                    _inCharacterCreation.value
                ).data
                if (pair != null) {
                    _maxSta.value = pair.second
                    _ip.value = pair.first
                }
            }
            "ENC" -> {
                val pair = onStatChangeUseCase(
                    _enc.value,
                    _ip.value,
                    increase,
                    _inCharacterCreation.value
                ).data
                if (pair != null) {
                    _enc.value = pair.second
                    _ip.value = pair.first
                }
            }
            "REC" -> {
                val pair = onStatChangeUseCase(
                    _rec.value,
                    _ip.value,
                    increase,
                    _inCharacterCreation.value
                ).data
                if (pair != null) {
                    _rec.value = pair.second
                    _ip.value = pair.first
                }
            }


        }
    }
}
