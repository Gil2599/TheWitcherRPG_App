package com.example.thewitcherrpg.characterSheet

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.thewitcherrpg.characterSheet.equipment.EquipmentItem
import com.example.thewitcherrpg.characterSheet.equipment.EquipmentTypes
import com.example.thewitcherrpg.data.Character
import kotlin.math.absoluteValue

class SharedViewModel: ViewModel() {

    var inCharacterCreation: Boolean = false

    private var _uniqueID = MutableLiveData(0)
    val uniqueID: LiveData<Int> = _uniqueID

    private var _image = MutableLiveData("")
    val image: LiveData<String> = _image

    private var _name = MutableLiveData("")
    val name: LiveData<String> = _name

    private var _iP = MutableLiveData(44)
    val iP: LiveData<Int> = _iP

    private var _race = MutableLiveData("")
    val race: LiveData<String> = _race

    private var _gender = MutableLiveData("")
    val gender: LiveData<String> = _gender

    private var _profession = MutableLiveData("")
    val profession: LiveData<String> = _profession

    private var _age = MutableLiveData(0)
    val age: LiveData<Int> = _age

    private var _definingSkill = MutableLiveData("")
    val definingSkill: LiveData<String> = _definingSkill

    private var _crowns = MutableLiveData(0)
    val crowns: LiveData<Int> = _crowns


    //Profession Skills
    private var _professionSkillA1 = MutableLiveData(0)
    val professionSkillA1: LiveData<Int> = _professionSkillA1

    private var _professionSkillA2 = MutableLiveData(0)
    val professionSkillA2: LiveData<Int> = _professionSkillA2

    private var _professionSkillA3 = MutableLiveData(0)
    val professionSkillA3: LiveData<Int> = _professionSkillA3

    private var _professionSkillB1 = MutableLiveData(0)
    val professionSkillB1: LiveData<Int> = _professionSkillB1

    private var _professionSkillB2 = MutableLiveData(0)
    val professionSkillB2: LiveData<Int> = _professionSkillB2

    private var _professionSkillB3 = MutableLiveData(0)
    val professionSkillB3: LiveData<Int> = _professionSkillB3

    private var _professionSkillC1 = MutableLiveData(0)
    val professionSkillC1: LiveData<Int> = _professionSkillC1

    private var _professionSkillC2 = MutableLiveData(0)
    val professionSkillC2: LiveData<Int> = _professionSkillC2

    private var _professionSkillC3 = MutableLiveData(0)
    val professionSkillC3: LiveData<Int> = _professionSkillC3

    //Stats
    private var _intelligence = MutableLiveData(0)
    val intelligence: LiveData<Int> = _intelligence

    private var _ref = MutableLiveData(0)
    val ref: LiveData<Int> = _ref

    private var _dex = MutableLiveData(0)
    val dex: LiveData<Int> = _dex

    private var _body = MutableLiveData(0)
    val body: LiveData<Int> = _body

    private var _spd = MutableLiveData(0)
    val spd: LiveData<Int> = _spd

    private var _emp = MutableLiveData(0)
    val emp: LiveData<Int> = _emp

    private var _cra = MutableLiveData(0)
    val cra: LiveData<Int> = _cra

    private var _will = MutableLiveData(0)
    val will: LiveData<Int> = _will

    private var _luck = MutableLiveData(0)
    val luck: LiveData<Int> = _luck

    private var _stun = MutableLiveData(0)
    val stun: LiveData<Int> = _stun

    private var _run = MutableLiveData(0)
    val run: LiveData<Int> = _run

    private var _leap = MutableLiveData(0)
    val leap: LiveData<Int> = _leap

    private var _maxHP = MutableLiveData(0)
    val maxHp: LiveData<Int> = _maxHP

    private var _hp = MutableLiveData(0)
    val hp: LiveData<Int> = _hp

    private var _maxSta = MutableLiveData(0)
    val maxSta: LiveData<Int> = _maxSta

    private var _sta = MutableLiveData(0)
    val sta: LiveData<Int> = _sta

    private var _enc = MutableLiveData(0)
    val enc: LiveData<Int> = _enc

    private var _rec = MutableLiveData(0)
    val rec: LiveData<Int> = _rec

    private var _punch = MutableLiveData("1d6 +2")
    val punch: LiveData<String> = _punch

    private var _kick = MutableLiveData("1d6 +6")
    val kick: LiveData<String> = _kick


    //Skills
    private var _awareness = MutableLiveData(0)
    val awareness: LiveData<Int> = _awareness

    private var _business = MutableLiveData(0)
    val business: LiveData<Int> = _business

    private var _deduction = MutableLiveData(0)
    val deduction: LiveData<Int> = _deduction

    private var _education = MutableLiveData(0)
    val education: LiveData<Int> = _education

    private var _commonSpeech = MutableLiveData(0)
    val commonSpeech: LiveData<Int> = _commonSpeech

    private var _elderSpeech = MutableLiveData(0)
    val elderSpeech: LiveData<Int> = _elderSpeech

    private var _dwarven = MutableLiveData(0)
    val dwarven: LiveData<Int> = _dwarven

    private var _monsterLore = MutableLiveData(0)
    val monsterLore: LiveData<Int> = _monsterLore

    private var _socialEtiquette = MutableLiveData(0)
    val socialEtiquette: LiveData<Int> = _socialEtiquette

    private var _streetwise = MutableLiveData(0)
    val streetwise: LiveData<Int> = _streetwise

    private var _tactics = MutableLiveData(0)
    val tactics: LiveData<Int> = _tactics

    private var _teaching = MutableLiveData(0)
    val teaching: LiveData<Int> = _teaching

    private var _wildernessSurvival = MutableLiveData(0)
    val wildernessSurvival: LiveData<Int> = _wildernessSurvival

    private var _brawling = MutableLiveData(0)
    val brawling: LiveData<Int> = _brawling

    private var _dodgeEscape = MutableLiveData(0)
    val dodgeEscape: LiveData<Int> = _dodgeEscape

    private var _melee = MutableLiveData(0)
    val melee: LiveData<Int> = _melee

    private var _riding = MutableLiveData(0)
    val riding: LiveData<Int> = _riding

    private var _sailing = MutableLiveData(0)
    val sailing: LiveData<Int> = _sailing

    private var _smallBlades = MutableLiveData(0)
    val smallBlades: LiveData<Int> = _smallBlades

    private var _staffSpear = MutableLiveData(0)
    val staffSpear: LiveData<Int> = _staffSpear

    private var _swordsmanship = MutableLiveData(0)
    val swordsmanship: LiveData<Int> = _swordsmanship

    private var _archery = MutableLiveData(0)
    val archery: LiveData<Int> = _archery

    private var _athletics = MutableLiveData(0)
    val athletics: LiveData<Int> = _athletics

    private var _crossbow = MutableLiveData(0)
    val crossbow: LiveData<Int> = _crossbow

    private var _sleightOfHand = MutableLiveData(0)
    val sleightOfHand: LiveData<Int> = _sleightOfHand

    private var _stealth = MutableLiveData(0)
    val stealth: LiveData<Int> = _stealth

    private var _physique = MutableLiveData(0)
    val physique: LiveData<Int> = _physique

    private var _endurance = MutableLiveData(0)
    val endurance: LiveData<Int> = _endurance

    private var _charisma = MutableLiveData(0)
    val charisma: LiveData<Int> = _charisma

    private var _deceit = MutableLiveData(0)
    val deceit: LiveData<Int> = _deceit

    private var _fineArts = MutableLiveData(0)
    val fineArts: LiveData<Int> = _fineArts

    private var _gambling = MutableLiveData(0)
    val gambling: LiveData<Int> = _gambling

    private var _groomingAndStyle = MutableLiveData(0)
    val groomingAndStyle: LiveData<Int> = _groomingAndStyle

    private var _humanPerception = MutableLiveData(0)
    val humanPerception: LiveData<Int> = _humanPerception

    private var _leadership = MutableLiveData(0)
    val leadership: LiveData<Int> = _leadership

    private var _persuasion = MutableLiveData(0)
    val persuasion: LiveData<Int> = _persuasion

    private var _performance = MutableLiveData(0)
    val performance: LiveData<Int> = _performance

    private var _seduction = MutableLiveData(0)
    val seduction: LiveData<Int> = _seduction

    private var _alchemy = MutableLiveData(0)
    val alchemy: LiveData<Int> = _alchemy

    private var _crafting = MutableLiveData(0)
    val crafting: LiveData<Int> = _crafting

    private var _disguise = MutableLiveData(0)
    val disguise: LiveData<Int> = _disguise

    private var _firstAid = MutableLiveData(0)
    val firstAid: LiveData<Int> = _firstAid

    private var _forgery = MutableLiveData(0)
    val forgery: LiveData<Int> = _forgery

    private var _pickLock = MutableLiveData(0)
    val pickLock: LiveData<Int> = _pickLock

    private var _trapCrafting = MutableLiveData(0)
    val trapCrafting: LiveData<Int> = _trapCrafting

    private var _courage = MutableLiveData(0)
    val courage: LiveData<Int> = _courage

    private var _hexWeaving = MutableLiveData(0)
    val hexWeaving: LiveData<Int> = _hexWeaving

    private var _intimidation = MutableLiveData(0)
    val intimidation: LiveData<Int> = _intimidation

    private var _spellCasting = MutableLiveData(0)
    val spellCasting: LiveData<Int> = _spellCasting

    private var _resistMagic = MutableLiveData(0)
    val resistMagic: LiveData<Int> = _resistMagic

    private var _resistCoercion = MutableLiveData(0)
    val resistCoercion: LiveData<Int> = _resistCoercion

    private var _ritualCrafting = MutableLiveData(0)
    val ritualCrafting: LiveData<Int> = _ritualCrafting

    //##### Magic #################################################
    private var _vigor = MutableLiveData(10)
    val vigor: LiveData<Int> = _vigor

    //Mages
    private var _noviceSpellList = MutableLiveData(arrayListOf<String>())
    val noviceSpellList: LiveData<ArrayList<String>> = _noviceSpellList

    private var _journeymanSpellList = MutableLiveData(arrayListOf<String>())
    val journeymanSpellList: LiveData<ArrayList<String>> = _journeymanSpellList

    private var _masterSpellList = MutableLiveData(arrayListOf<String>())
    val masterSpellList: LiveData<ArrayList<String>> = _masterSpellList

    //Priests
    private var _noviceDruidInvocations = MutableLiveData(arrayListOf<String>())
    val noviceDruidInvocations: LiveData<ArrayList<String>> = _noviceDruidInvocations

    private var _journeymanDruidInvocations = MutableLiveData(arrayListOf<String>())
    val journeymanDruidInvocations: LiveData<ArrayList<String>> = _journeymanDruidInvocations

    private var _masterDruidInvocations = MutableLiveData(arrayListOf<String>())
    val masterDruidInvocations: LiveData<ArrayList<String>> = _masterDruidInvocations

    private var _novicePreacherInvocations = MutableLiveData(arrayListOf<String>())
    val novicePreacherInvocations: LiveData<ArrayList<String>> = _novicePreacherInvocations

    private var _journeymanPreacherInvocations = MutableLiveData(arrayListOf<String>())
    val journeymanPreacherInvocations: LiveData<ArrayList<String>> = _journeymanPreacherInvocations

    private var _masterPreacherInvocations = MutableLiveData(arrayListOf<String>())
    val masterPreacherInvocations: LiveData<ArrayList<String>> = _masterPreacherInvocations

    private var _archPriestInvocations = MutableLiveData(arrayListOf<String>())
    val archPriestInvocations: LiveData<ArrayList<String>> = _archPriestInvocations

    //Signs
    private var _basicSigns = MutableLiveData(arrayListOf<String>())
    val basicSigns: LiveData<ArrayList<String>> = _basicSigns

    private var _alternateSigns = MutableLiveData(arrayListOf<String>())
    val alternateSigns: LiveData<ArrayList<String>> = _alternateSigns

    //Rituals
    private var _noviceRitualList = MutableLiveData(arrayListOf<String>())
    val noviceRitualList: LiveData<ArrayList<String>> = _noviceRitualList

    private var _journeymanRitualList = MutableLiveData(arrayListOf<String>())
    val journeymanRitualList: LiveData<ArrayList<String>> = _journeymanRitualList

    private var _masterRitualList = MutableLiveData(arrayListOf<String>())
    val masterRitualList: LiveData<ArrayList<String>> = _masterRitualList

    //Hexes
    private var _hexesList = MutableLiveData(arrayListOf<String>())
    val hexesList: LiveData<ArrayList<String>> = _hexesList

    //Equipment
    private var _headEquipment = MutableLiveData(arrayListOf<String>())
    val headEquipment: LiveData<ArrayList<String>> = _headEquipment

    private var _equippedHead = MutableLiveData<EquipmentItem>()
    val equippedHead: LiveData<EquipmentItem> = _equippedHead



    //Setter Functions
    fun setImagePath(path: String){
        _image.value = path
    }

    fun setName(name: String){
        _name.value = name
    }

    fun setIP(ip: Int){
        _iP.value = ip
    }

    fun setRace(race: String){
        _race.value = race
    }
    fun setGender(gender: String){
        _gender.value = gender
    }

    fun setAge(age: Int){
        _age.value = age
    }

    fun setProfession(profession: String){
        _profession.value = profession
    }

    fun setDefiningSkill(defSkill: String){
        _definingSkill.value = defSkill
    }

    //Mages
    fun addNoviceSpell(spell: String): Boolean{
        //Check whether character already has the spell
        return if (spell !in _noviceSpellList.value!!){
            val newArray = _noviceSpellList.value!!.toMutableList()
            newArray.add(spell)
            _noviceSpellList.value = ArrayList(newArray)
            true
        } else false

    }
    fun removeNoviceSpell(spell: String){
        val newArray = _noviceSpellList.value!!.toMutableList()
        newArray.remove(spell)
        _noviceSpellList.value = ArrayList(newArray)
    }

    fun addJourneymanSpell(spell: String): Boolean{
        //Check whether character already has the spell
        return if (spell !in _journeymanSpellList.value!!){
            val newArray = _journeymanSpellList.value!!.toMutableList()
            newArray.add(spell)
            _journeymanSpellList.value = ArrayList(newArray)
            true
        } else false

    }
    fun removeJourneymanSpell(spell: String){
        val newArray = _journeymanSpellList.value!!.toMutableList()
        newArray.remove(spell)
        _journeymanSpellList.value = ArrayList(newArray)
    }

    fun addMasterSpell(spell: String): Boolean{
        //Check whether character already has the spell
        return if (spell !in _masterSpellList.value!!){
            val newArray = _masterSpellList.value!!.toMutableList()
            newArray.add(spell)
            _masterSpellList.value = ArrayList(newArray)
            true
        } else false

    }
    fun removeMasterSpell(spell: String){
        val newArray = _masterSpellList.value!!.toMutableList()
        newArray.remove(spell)
        _masterSpellList.value = ArrayList(newArray)
    }

    //Priests
    fun addNoviceDruidInvo(invo: String): Boolean{
        //Check whether character already has the spell
        return if (invo !in _noviceDruidInvocations.value!!){
            val newArray = _noviceDruidInvocations.value!!.toMutableList()
            newArray.add(invo)
            _noviceDruidInvocations.value = ArrayList(newArray)
            true
        } else false

    }
    fun removeNoviceDruidInvo(invo: String){
        val newArray = _noviceDruidInvocations.value!!.toMutableList()
        newArray.remove(invo)
        _noviceDruidInvocations.value = ArrayList(newArray)
    }

    fun addJourneymanDruidInvo(invo: String): Boolean{
        //Check whether character already has the spell
        return if (invo !in _journeymanDruidInvocations.value!!){
            val newArray = _journeymanDruidInvocations.value!!.toMutableList()
            newArray.add(invo)
            _journeymanDruidInvocations.value = ArrayList(newArray)
            true
        } else false

    }
    fun removeJourneymanDruidInvo(invo: String){
        val newArray = _journeymanDruidInvocations.value!!.toMutableList()
        newArray.remove(invo)
        _journeymanDruidInvocations.value = ArrayList(newArray)
    }

    fun addMasterDruidInvo(invo: String): Boolean{
        //Check whether character already has the spell
        return if (invo !in _masterDruidInvocations.value!!){
            val newArray = _masterDruidInvocations.value!!.toMutableList()
            newArray.add(invo)
            _masterDruidInvocations.value = ArrayList(newArray)
            true
        } else false

    }
    fun removeMasterDruidInvo(invo: String){
        val newArray = _masterDruidInvocations.value!!.toMutableList()
        newArray.remove(invo)
        _masterDruidInvocations.value = ArrayList(newArray)
    }

    fun addNovicePreacherInvo(invo: String): Boolean{
        //Check whether character already has the spell
        return if (invo !in _novicePreacherInvocations.value!!){
            val newArray = _novicePreacherInvocations.value!!.toMutableList()
            newArray.add(invo)
            _novicePreacherInvocations.value = ArrayList(newArray)
            true
        } else false

    }
    fun removeNovicePreacherInvo(invo: String){
        val newArray = _novicePreacherInvocations.value!!.toMutableList()
        newArray.remove(invo)
        _novicePreacherInvocations.value = ArrayList(newArray)
    }

    fun addJourneymanPreacherInvo(invo: String): Boolean{
        //Check whether character already has the spell
        return if (invo !in _journeymanPreacherInvocations.value!!){
            val newArray = _journeymanPreacherInvocations.value!!.toMutableList()
            newArray.add(invo)
            _journeymanPreacherInvocations.value = ArrayList(newArray)
            true
        } else false

    }
    fun removeJourneymanPreacherInvo(invo: String){
        val newArray = _journeymanPreacherInvocations.value!!.toMutableList()
        newArray.remove(invo)
        _journeymanPreacherInvocations.value = ArrayList(newArray)
    }

    fun addMasterPreacherInvo(invo: String): Boolean{
        //Check whether character already has the spell
        return if (invo !in _masterPreacherInvocations.value!!){
            val newArray = _masterPreacherInvocations.value!!.toMutableList()
            newArray.add(invo)
            _masterPreacherInvocations.value = ArrayList(newArray)
            true
        } else false

    }
    fun removeMasterPreacherInvo(invo: String){
        val newArray = _masterPreacherInvocations.value!!.toMutableList()
        newArray.remove(invo)
        _masterPreacherInvocations.value = ArrayList(newArray)
    }

    fun addArchPriestInvo(invo: String): Boolean{
        //Check whether character already has the spell
        return if (invo !in _archPriestInvocations.value!!){
            val newArray = _archPriestInvocations.value!!.toMutableList()
            newArray.add(invo)
            _archPriestInvocations.value = ArrayList(newArray)
            true
        } else false

    }
    fun removeArchPriestInvo(invo: String){
        val newArray = _archPriestInvocations.value!!.toMutableList()
        newArray.remove(invo)
        _archPriestInvocations.value = ArrayList(newArray)
    }

    //Signs
    fun addBasicSign(sign: String): Boolean{
        //Check whether character already has the spell
        return if (sign !in _basicSigns.value!!){
            val newArray = _basicSigns.value!!.toMutableList()
            newArray.add(sign)
            _basicSigns.value = ArrayList(newArray)
            true
        } else false

    }
    fun removeBasicSign(sign: String){
        val newArray = _basicSigns.value!!.toMutableList()
        newArray.remove(sign)
        _basicSigns.value = ArrayList(newArray)
    }

    fun addAlternateSign(sign: String): Boolean{
        //Check whether character already has the spell
        return if (sign !in _alternateSigns.value!!){
            val newArray = _alternateSigns.value!!.toMutableList()
            newArray.add(sign)
            _alternateSigns.value = ArrayList(newArray)
            true
        } else false

    }
    fun removeAlternateSign(sign: String){
        val newArray = _alternateSigns.value!!.toMutableList()
        newArray.remove(sign)
        _alternateSigns.value = ArrayList(newArray)
    }

    //Rituals
    fun addNoviceRitual(ritual: String): Boolean{
        //Check whether character already has the spell
        return if (ritual !in _noviceRitualList.value!!){
            val newArray = _noviceRitualList.value!!.toMutableList()
            newArray.add(ritual)
            _noviceRitualList.value = ArrayList(newArray)
            true
        } else false

    }
    fun removeNoviceRitual(ritual: String){
        val newArray = _noviceRitualList.value!!.toMutableList()
        newArray.remove(ritual)
        _noviceRitualList.value = ArrayList(newArray)
    }

    fun addJourneymanRitual(ritual: String): Boolean{
        //Check whether character already has the spell
        return if (ritual !in _journeymanRitualList.value!!){
            val newArray = _journeymanRitualList.value!!.toMutableList()
            newArray.add(ritual)
            _journeymanRitualList.value = ArrayList(newArray)
            true
        } else false

    }
    fun removeJourneymanRitual(ritual: String){
        val newArray = _journeymanRitualList.value!!.toMutableList()
        newArray.remove(ritual)
        _journeymanRitualList.value = ArrayList(newArray)
    }

    fun addMasterRitual(ritual: String): Boolean{
        //Check whether character already has the spell
        return if (ritual !in _masterRitualList.value!!){
            val newArray = _masterRitualList.value!!.toMutableList()
            newArray.add(ritual)
            _masterRitualList.value = ArrayList(newArray)
            true
        } else false

    }
    fun removeMasterRitual(ritual: String){
        val newArray = _masterRitualList.value!!.toMutableList()
        newArray.remove(ritual)
        _masterRitualList.value = ArrayList(newArray)
    }

    //Hexes
    fun addHex(hex: String): Boolean{
        //Check whether character already has the spell
        return if (hex !in _hexesList.value!!){
            val newArray = _hexesList.value!!.toMutableList()
            newArray.add(hex)
            _hexesList.value = ArrayList(newArray)
            true
        } else false

    }
    fun removeHex(hex: String){
        val newArray = _hexesList.value!!.toMutableList()
        newArray.remove(hex)
        _hexesList.value = ArrayList(newArray)
    }

    fun castSpell(staCost: Int): Boolean{
        return _vigor.value!! >= staCost
    }

    //Equipment
    fun addArmor(itemString: String): Boolean{

        val pair = itemString.split(":").toTypedArray()

        //Check the armor type and add it to the correct list
        when(pair[8]) {
            "head" -> {
                //Check whether character already has the spell
                return if (itemString !in _headEquipment.value!!) {
                    val newArray = _headEquipment.value!!.toMutableList()
                    newArray.add(itemString)
                    _headEquipment.value = ArrayList(newArray)
                    true
                } else false
            }
        }
        return false

    }

    //Logic Functions
    private fun onStatChange(value: Int, increase: Boolean): Boolean{

        var newIP = iP.value!!
        var newVal = value

        if(!inCharacterCreation) {
            if (increase) {
                if (newVal == 0) newVal = 1

                if (iP.value!! >= newVal*10) {
                    newIP -= newVal * 10
                } else return false
            }

            if (!increase) {
                if (newVal > 0) {
                    if (newVal == 1) newVal = 2

                    newIP += (newVal - 1) * 10
                } else return false
            }
            _iP.value = newIP
        }
        else {
            if (increase) {
                if (iP.value!! > 0 && newVal < 10) {
                    newIP -= 1
                } else return false
            }

            if (!increase) {
                if (newVal > 0) {

                    newIP += 1
                } else return false
            }
            _iP.value = newIP
        }
        return true

    }

    private fun onSkillChange(value: Int, increase: Boolean): Boolean{

        var newIP = iP.value!!
        var newVal = value

        if(!inCharacterCreation) {
            if (increase) {
                if (newVal == 0) newVal = 1

                if (iP.value!! >= newVal) {
                    newIP -= newVal
                } else return false
            }

            if (!increase) {
                if (newVal > 0) {
                    if (newVal == 1) newVal = 2

                    newIP += (newVal - 1)
                } else return false
            }
            _iP.value = newIP
        }
        else {
            if (increase) {
                if (iP.value!! > 0 && newVal < 6) {
                    newIP -= 1
                } else return false
            }

            if (!increase) {
                if (newVal > 0) {

                    newIP += 1
                } else return false
            }
            _iP.value = newIP
        }
        return true
    }

    fun onSaveFinal(): Character{

        val imagePath = _image.value!!
        val name = _name.value!!
        val ip = _iP.value!!
        val race = _race.value!!
        val gender = _gender.value!!
        val age = _age.value!!
        val profession = _profession.value!!
        val definingSkill = _definingSkill.value!!
        val crowns = _crowns.value!!


        //Stats
        val inte = _intelligence.value!!
        val ref = _ref.value!!
        val dex = _dex.value!!
        val body = _body.value!!
        val spd = _spd.value!!
        val emp = _emp.value!!
        val cra = _cra.value!!
        val will = _will.value!!
        val luck = _luck.value!!
        val stun = _stun.value!!
        val run = _run.value!!
        val leap = _leap.value!!
        val maxHp = _maxHP.value!!
        val hp = if (!inCharacterCreation) _hp.value!! else _maxHP.value!!
        val maxSta = _maxSta.value!!
        val sta = if (!inCharacterCreation) _sta.value!! else _maxSta.value!!
        val enc = _enc.value!!
        val rec = _rec.value!!
        val punch = _punch.value!!
        val kick = _kick.value!!

        //Profession Skills
        val professionSkillA1 = _professionSkillA1.value!!
        val professionSkillA2 = _professionSkillA2.value!!
        val professionSkillA3 = _professionSkillA3.value!!
        val professionSkillB1 = _professionSkillB1.value!!
        val professionSkillB2 = _professionSkillB2.value!!
        val professionSkillB3 = _professionSkillB3.value!!
        val professionSkillC1 = _professionSkillC1.value!!
        val professionSkillC2 = _professionSkillC2.value!!
        val professionSkillC3 = _professionSkillC3.value!!

        //Skills
        val awareness = _awareness.value!!
        val business = _business.value!!
        val deduction = _deduction.value!!
        val education = _education.value!!
        val commonSpeech = _commonSpeech.value!!
        val elderSpeech = _elderSpeech.value!!
        val dwarven = _dwarven.value!!
        val monsterLore = _monsterLore.value!!
        val socialEtiquette = _socialEtiquette.value!!
        val streetwise = _streetwise.value!!
        val tactics = _tactics.value!!
        val teaching = _teaching.value!!
        val wildernessSurvival = _wildernessSurvival.value!!
        val brawling = _brawling.value!!
        val dodgeEscape = _dodgeEscape.value!!
        val melee = _melee.value!!
        val riding = _riding.value!!
        val sailing = _sailing.value!!
        val smallBlades = _smallBlades.value!!
        val staffSpear = _staffSpear.value!!
        val swordsmanship = _swordsmanship.value!!
        val archery = _archery.value!!
        val athletics = _athletics.value!!
        val crossbow = _crossbow.value!!
        val sleightOfHand = _sleightOfHand.value!!
        val stealth = _stealth.value!!
        val physique = _physique.value!!
        val endurance = _endurance.value!!
        val charisma = _charisma.value!!
        val deceit = _deceit.value!!
        val fineArts = _fineArts.value!!
        val gambling = _gambling.value!!
        val groomingAndStyle = _groomingAndStyle.value!!
        val humanPerception = _humanPerception.value!!
        val leadership = _leadership.value!!
        val persuasion = _persuasion.value!!
        val performance = _performance.value!!
        val seduction = _seduction.value!!
        val alchemy = _alchemy.value!!
        val crafting = _crafting.value!!
        val disguise = _disguise.value!!
        val firstAid = _firstAid.value!!
        val forgery = _forgery.value!!
        val pickLock = _pickLock.value!!
        val trapCrafting = _trapCrafting.value!!
        val courage = _courage.value!!
        val hexWeaving = _hexWeaving.value!!
        val intimidation = _intimidation.value!!
        val spellCasting = _spellCasting.value!!
        val resistMagic = _resistMagic.value!!
        val resistCoercion = _resistCoercion.value!!
        val ritualCrafting = _ritualCrafting.value!!

        //##### Magic #################################################
        val vigor = _vigor.value!!

        val basicSigns = _basicSigns.value!!
        val alternateSigns = _alternateSigns.value!!

        val noviceRituals = _noviceRitualList.value!!
        val journeymanRituals = _journeymanRitualList.value!!
        val masterRituals = _masterRitualList.value!!

        val hexes = _hexesList.value!!

        //Mages
        val noviceSpells = _noviceSpellList.value!!
        val journeymanSpells = _journeymanSpellList.value!!
        val masterSpells = _masterSpellList.value!!

        //Priests
        val noviceDruidInvocations = _noviceDruidInvocations.value!!
        val journeymanDruidInvocations = _journeymanDruidInvocations.value!!
        val masterDruidInvocations = _masterDruidInvocations.value!!

        val novicePreacherInvocations = _novicePreacherInvocations.value!!
        val journeymanPreacherInvocations = _journeymanPreacherInvocations.value!!
        val masterPreacherInvocations = _masterPreacherInvocations.value!!

        val archPriestInvocations = _archPriestInvocations.value!!

        //Equipment
        val headEquipment = _headEquipment.value!!
        val equippedHead = EquipmentItem("", 0, "", "", "",
                                            0, 0F, 0, EquipmentTypes.HEAD)

        return Character(0, imagePath, name, ip, race, gender, age, profession, definingSkill, crowns,
            professionSkillA1, professionSkillA2, professionSkillA3, professionSkillB1, professionSkillB2, professionSkillB3,
            professionSkillC1, professionSkillC2, professionSkillC3, inte, ref, dex, body, spd, emp, cra, will, luck,
            stun, run, leap, maxHp, hp, maxSta, sta, enc, rec, punch, kick,
            awareness, business, deduction, education, commonSpeech, elderSpeech, dwarven, monsterLore, socialEtiquette,
            streetwise, tactics, teaching, wildernessSurvival, brawling, dodgeEscape, melee, riding, sailing, smallBlades,
            staffSpear, swordsmanship, archery, athletics, crossbow, sleightOfHand, stealth, physique, endurance, charisma,
            deceit, fineArts, gambling, groomingAndStyle, humanPerception, leadership, persuasion, performance, seduction,
            alchemy, crafting, disguise, firstAid, forgery, pickLock, trapCrafting, courage, hexWeaving, intimidation,
            spellCasting, resistMagic, resistCoercion, ritualCrafting, vigor, basicSigns, alternateSigns, noviceRituals,
            journeymanRituals, masterRituals, hexes, noviceSpells, journeymanSpells, masterSpells, noviceDruidInvocations,
            journeymanDruidInvocations, masterDruidInvocations, novicePreacherInvocations, journeymanPreacherInvocations,
            masterPreacherInvocations, archPriestInvocations, headEquipment, equippedHead)

    }

    fun increaseStat(stat: Int){
        when (stat){
            1 -> _intelligence.value = if(onStatChange(_intelligence.value!!, true)) _intelligence.value!!.plus(1) else _intelligence.value
            2 -> _ref.value = if(onStatChange(_ref.value!!, true)) _ref.value!!.plus(1) else _ref.value
            3 -> _dex.value = if(onStatChange(_dex.value!!, true)) _dex.value!!.plus(1) else _dex.value
            4 -> {_body.value = if(onStatChange(_body.value!!, true)) _body.value!!.plus(1) else _body.value; updateDerivedStats()}
            5 -> {_spd.value = if(onStatChange(_spd.value!!, true)) _spd.value!!.plus(1) else _spd.value; updateDerivedStats()}
            6 -> _emp.value = if(onStatChange(_emp.value!!, true)) _emp.value!!.plus(1) else _emp.value
            7 -> _cra.value = if(onStatChange(_cra.value!!, true)) _cra.value!!.plus(1) else _cra.value
            8 -> {_will.value = if(onStatChange(_will.value!!, true)) _will.value!!.plus(1) else _will.value; updateDerivedStats()}
            9 -> _luck.value = if(onStatChange(_luck.value!!, true)) _luck.value!!.plus(1) else _luck.value
            10 -> _stun.value = _stun.value!!.plus(1)
            11 -> {_run.value = _run.value!!.plus(1); updateDerivedStats()}
            12 -> _leap.value = _leap.value!!.plus(1)
            13 -> _maxHP.value = _maxHP.value!!.plus(1)
            14 -> _maxSta.value = _maxSta.value!!.plus(1)
            15 -> _enc.value = _enc.value!!.plus(1)
            16 -> _rec.value = _rec.value!!.plus(1)

        }
    }

    fun decreaseStat(stat: Int){
        when (stat){
            1 -> _intelligence.value = if(onStatChange(_intelligence.value!!, false)) _intelligence.value!!.minus(1) else _intelligence.value
            2 -> _ref.value = if(onStatChange(_ref.value!!, false)) _ref.value!!.minus(1) else _ref.value
            3 -> _dex.value = if(onStatChange(_dex.value!!, false)) _dex.value!!.minus(1) else _dex.value
            4 -> {_body.value = if(onStatChange(_body.value!!, false)) _body.value!!.minus(1) else _body.value; ; updateDerivedStats()}
            5 -> {_spd.value = if(onStatChange(_spd.value!!, false)) _spd.value!!.minus(1) else _spd.value; updateDerivedStats()}
            6 -> _emp.value = if(onStatChange(_emp.value!!, false)) _emp.value!!.minus(1) else _emp.value
            7 -> _cra.value = if(onStatChange(_cra.value!!, false)) _cra.value!!.minus(1) else _cra.value
            8 -> {_will.value = if(onStatChange(_will.value!!, false)) _will.value!!.minus(1) else _will.value; ; updateDerivedStats()}
            9 -> _luck.value = if(onStatChange(_luck.value!!, false)) _luck.value!!.minus(1) else _luck.value
            10 -> _stun.value = _stun.value!!.minus(1)
            11 -> {_run.value = _run.value!!.minus(1); updateDerivedStats()}
            12 -> _leap.value = _leap.value!!.minus(1)
            13 -> _maxHP.value = _maxHP.value!!.minus(1)
            14 -> _maxSta.value = _maxSta.value!!.minus(1)
            15 -> _enc.value = _enc.value!!.minus(1)
            16 -> _rec.value = _rec.value!!.minus(1)

        }
    }

    fun increaseSkill(stat: Int){
        when (stat){
            1 -> _awareness.value = if(onSkillChange(_awareness.value!!, true)) _awareness.value!!.plus(1) else _awareness.value
            2 -> _business.value = if(onSkillChange(_business.value!!, true)) _business.value!!.plus(1) else _business.value
            3 -> _deduction.value = if(onSkillChange(_deduction.value!!, true)) _deduction.value!!.plus(1) else _deduction.value
            4 -> _education.value = if(onSkillChange(_education.value!!, true)) _education.value!!.plus(1) else _education.value
            5 -> _commonSpeech.value = if(onSkillChange(_commonSpeech.value!!, true)) _commonSpeech.value!!.plus(1) else _commonSpeech.value
            6 -> _elderSpeech.value = if(onSkillChange(_elderSpeech.value!!, true)) _elderSpeech.value!!.plus(1) else _elderSpeech.value
            7 -> _dwarven.value = if(onSkillChange(_dwarven.value!!, true)) _dwarven.value!!.plus(1) else _dwarven.value
            8 -> _monsterLore.value = if(onSkillChange(_monsterLore.value!!, true)) _monsterLore.value!!.plus(1) else _monsterLore.value
            9 -> _socialEtiquette.value = if(onSkillChange(_socialEtiquette.value!!, true)) _socialEtiquette.value!!.plus(1) else _socialEtiquette.value
            10 -> _streetwise.value = if(onSkillChange(_streetwise.value!!, true)) _streetwise.value!!.plus(1) else _streetwise.value
            11 -> _tactics.value = if(onSkillChange(_tactics.value!!, true)) _tactics.value!!.plus(1) else _tactics.value
            12 -> _teaching.value = if(onSkillChange(_teaching.value!!, true)) _teaching.value!!.plus(1) else _teaching.value
            13 -> _wildernessSurvival.value = if(onSkillChange(_wildernessSurvival.value!!, true)) _wildernessSurvival.value!!.plus(1) else _wildernessSurvival.value
            14 -> _brawling.value = if(onSkillChange(_brawling.value!!, true)) _brawling.value!!.plus(1) else _brawling.value
            15 -> _dodgeEscape.value = if(onSkillChange(_dodgeEscape.value!!, true)) _dodgeEscape.value!!.plus(1) else _dodgeEscape.value
            16 -> _melee.value = if(onSkillChange(_melee.value!!, true)) _melee.value!!.plus(1) else _melee.value
            17 -> _riding.value = if(onSkillChange(_riding.value!!, true)) _riding.value!!.plus(1) else _riding.value
            18 -> _sailing.value = if(onSkillChange(_sailing.value!!, true)) _sailing.value!!.plus(1) else _sailing.value
            19 -> _smallBlades.value = if(onSkillChange(_smallBlades.value!!, true)) _smallBlades.value!!.plus(1) else _smallBlades.value
            20 -> _staffSpear.value = if(onSkillChange(_staffSpear.value!!, true)) _staffSpear.value!!.plus(1) else _staffSpear.value
            21 -> _swordsmanship.value = if(onSkillChange(_swordsmanship.value!!, true)) _swordsmanship.value!!.plus(1) else _swordsmanship.value
            22 -> _archery.value = if(onSkillChange(_archery.value!!, true)) _archery.value!!.plus(1) else _archery.value
            23 -> _athletics.value = if(onSkillChange(_athletics.value!!, true)) _athletics.value!!.plus(1) else _athletics.value
            24 -> _crossbow.value = if(onSkillChange(_crossbow.value!!, true)) _crossbow.value!!.plus(1) else _crossbow.value
            25 -> _sleightOfHand.value = if(onSkillChange(_sleightOfHand.value!!, true)) _sleightOfHand.value!!.plus(1) else _sleightOfHand.value
            26 -> _stealth.value = if(onSkillChange(_stealth.value!!, true)) _stealth.value!!.plus(1) else _stealth.value
            27 -> _physique.value = if(onSkillChange(_physique.value!!, true)) _physique.value!!.plus(1) else _physique.value
            28 -> _endurance.value = if(onSkillChange(_endurance.value!!, true)) _endurance.value!!.plus(1) else _endurance.value
            29 -> _charisma.value = if(onSkillChange(_charisma.value!!, true)) _charisma.value!!.plus(1) else _charisma.value
            30 -> _deceit.value = if(onSkillChange(_deceit.value!!, true)) _deceit.value!!.plus(1) else _deceit.value
            31 -> _fineArts.value = if(onSkillChange(_fineArts.value!!, true)) _fineArts.value!!.plus(1) else _fineArts.value
            32 -> _gambling.value = if(onSkillChange(_gambling.value!!, true)) _gambling.value!!.plus(1) else _gambling.value
            33 -> _groomingAndStyle.value = if(onSkillChange(_groomingAndStyle.value!!, true)) _groomingAndStyle.value!!.plus(1) else _groomingAndStyle.value
            34 -> _humanPerception.value = if(onSkillChange(_humanPerception.value!!, true)) _humanPerception.value!!.plus(1) else _humanPerception.value
            35 -> _leadership.value = if(onSkillChange(_leadership.value!!, true)) _leadership.value!!.plus(1) else _leadership.value
            36 -> _persuasion.value = if(onSkillChange(_persuasion.value!!, true)) _persuasion.value!!.plus(1) else _persuasion.value
            37 -> _performance.value = if(onSkillChange(_performance.value!!, true)) _performance.value!!.plus(1) else _performance.value
            38 -> _seduction.value = if(onSkillChange(_seduction.value!!, true)) _seduction.value!!.plus(1) else _seduction.value
            39 -> _alchemy.value = if(onSkillChange(_alchemy.value!!, true)) _alchemy.value!!.plus(1) else _alchemy.value
            40 -> _crafting.value = if(onSkillChange(_crafting.value!!, true)) _crafting.value!!.plus(1) else _crafting.value
            41 -> _disguise.value = if(onSkillChange(_disguise.value!!, true)) _disguise.value!!.plus(1) else _disguise.value
            42 -> _firstAid.value = if(onSkillChange(_firstAid.value!!, true)) _firstAid.value!!.plus(1) else _firstAid.value
            43 -> _forgery.value = if(onSkillChange(_alchemy.value!!, true)) _alchemy.value!!.plus(1) else _alchemy.value
            44 -> _pickLock.value = if(onSkillChange(_pickLock.value!!, true)) _pickLock.value!!.plus(1) else _pickLock.value
            45 -> _trapCrafting.value = if(onSkillChange(_trapCrafting.value!!, true)) _trapCrafting.value!!.plus(1) else _trapCrafting.value
            46 -> _courage.value = if(onSkillChange(_courage.value!!, true)) _courage.value!!.plus(1) else _courage.value
            47 -> _hexWeaving.value = if(onSkillChange(_hexWeaving.value!!, true)) _hexWeaving.value!!.plus(1) else _hexWeaving.value
            48 -> _intimidation.value = if(onSkillChange(_intimidation.value!!, true)) _intimidation.value!!.plus(1) else _intimidation.value
            49 -> _spellCasting.value = if(onSkillChange(_spellCasting.value!!, true)) _spellCasting.value!!.plus(1) else _spellCasting.value
            50 -> _resistMagic.value = if(onSkillChange(_resistMagic.value!!, true)) _resistMagic.value!!.plus(1) else _resistMagic.value
            51 -> _resistCoercion.value = if(onSkillChange(_resistCoercion.value!!, true)) _resistCoercion.value!!.plus(1) else _resistCoercion.value
            52 -> _ritualCrafting.value = if(onSkillChange(_ritualCrafting.value!!, true)) _ritualCrafting.value!!.plus(1) else _ritualCrafting.value

        }
    }

    fun decreaseSkill(stat: Int){
        when (stat){
            1 -> _awareness.value = if(onSkillChange(_awareness.value!!, false)) _awareness.value!!.minus(1) else _awareness.value
            2 -> _business.value = if(onSkillChange(_business.value!!, false)) _business.value!!.minus(1) else _business.value
            3 -> _deduction.value = if(onSkillChange(_deduction.value!!, false)) _deduction.value!!.minus(1) else _deduction.value
            4 -> _education.value = if(onSkillChange(_education.value!!, false)) _education.value!!.minus(1) else _education.value
            5 -> _commonSpeech.value = if(onSkillChange(_commonSpeech.value!!, false)) _commonSpeech.value!!.minus(1) else _commonSpeech.value
            6 -> _elderSpeech.value = if(onSkillChange(_elderSpeech.value!!, false)) _elderSpeech.value!!.minus(1) else _elderSpeech.value
            7 -> _dwarven.value = if(onSkillChange(_dwarven.value!!, false)) _dwarven.value!!.minus(1) else _dwarven.value
            8 -> _monsterLore.value = if(onSkillChange(_monsterLore.value!!, false)) _monsterLore.value!!.minus(1) else _monsterLore.value
            9 -> _socialEtiquette.value = if(onSkillChange(_socialEtiquette.value!!, false)) _socialEtiquette.value!!.minus(1) else _socialEtiquette.value
            10 -> _streetwise.value = if(onSkillChange(_streetwise.value!!, false)) _streetwise.value!!.minus(1) else _streetwise.value
            11 -> _tactics.value = if(onSkillChange(_tactics.value!!, false)) _tactics.value!!.minus(1) else _tactics.value
            12 -> _teaching.value = if(onSkillChange(_teaching.value!!, false)) _teaching.value!!.minus(1) else _teaching.value
            13 -> _wildernessSurvival.value = if(onSkillChange(_wildernessSurvival.value!!, false)) _wildernessSurvival.value!!.minus(1) else _wildernessSurvival.value
            14 -> _brawling.value = if(onSkillChange(_brawling.value!!, false)) _brawling.value!!.minus(1) else _brawling.value
            15 -> _dodgeEscape.value = if(onSkillChange(_dodgeEscape.value!!, false)) _dodgeEscape.value!!.minus(1) else _dodgeEscape.value
            16 -> _melee.value = if(onSkillChange(_melee.value!!, false)) _melee.value!!.minus(1) else _melee.value
            17 -> _riding.value = if(onSkillChange(_riding.value!!, false)) _riding.value!!.minus(1) else _riding.value
            18 -> _sailing.value = if(onSkillChange(_sailing.value!!, false)) _sailing.value!!.minus(1) else _sailing.value
            19 -> _smallBlades.value = if(onSkillChange(_smallBlades.value!!, false)) _smallBlades.value!!.minus(1) else _smallBlades.value
            20 -> _staffSpear.value = if(onSkillChange(_staffSpear.value!!, false)) _staffSpear.value!!.minus(1) else _staffSpear.value
            21 -> _swordsmanship.value = if(onSkillChange(_swordsmanship.value!!, false)) _swordsmanship.value!!.minus(1) else _swordsmanship.value
            22 -> _archery.value = if(onSkillChange(_archery.value!!, false)) _archery.value!!.minus(1) else _archery.value
            23 -> _athletics.value = if(onSkillChange(_athletics.value!!, false)) _athletics.value!!.minus(1) else _athletics.value
            24 -> _crossbow.value = if(onSkillChange(_crossbow.value!!, false)) _crossbow.value!!.minus(1) else _crossbow.value
            25 -> _sleightOfHand.value = if(onSkillChange(_sleightOfHand.value!!, false)) _sleightOfHand.value!!.minus(1) else _sleightOfHand.value
            26 -> _stealth.value = if(onSkillChange(_stealth.value!!, false)) _stealth.value!!.minus(1) else _stealth.value
            27 -> _physique.value = if(onSkillChange(_physique.value!!, false)) _physique.value!!.minus(1) else _physique.value
            28 -> _endurance.value = if(onSkillChange(_endurance.value!!, false)) _endurance.value!!.minus(1) else _endurance.value
            29 -> _charisma.value = if(onSkillChange(_charisma.value!!, false)) _charisma.value!!.minus(1) else _charisma.value
            30 -> _deceit.value = if(onSkillChange(_deceit.value!!, false)) _deceit.value!!.minus(1) else _deceit.value
            31 -> _fineArts.value = if(onSkillChange(_fineArts.value!!, false)) _fineArts.value!!.minus(1) else _fineArts.value
            32 -> _gambling.value = if(onSkillChange(_gambling.value!!, false)) _gambling.value!!.minus(1) else _gambling.value
            33 -> _groomingAndStyle.value = if(onSkillChange(_groomingAndStyle.value!!, false)) _groomingAndStyle.value!!.minus(1) else _groomingAndStyle.value
            34 -> _humanPerception.value = if(onSkillChange(_humanPerception.value!!, false)) _humanPerception.value!!.minus(1) else _humanPerception.value
            35 -> _leadership.value = if(onSkillChange(_leadership.value!!, false)) _leadership.value!!.minus(1) else _leadership.value
            36 -> _persuasion.value = if(onSkillChange(_persuasion.value!!, false)) _persuasion.value!!.minus(1) else _persuasion.value
            37 -> _performance.value = if(onSkillChange(_performance.value!!, false)) _performance.value!!.minus(1) else _performance.value
            38 -> _seduction.value = if(onSkillChange(_seduction.value!!, false)) _seduction.value!!.minus(1) else _seduction.value
            39 -> _alchemy.value = if(onSkillChange(_alchemy.value!!, false)) _alchemy.value!!.minus(1) else _alchemy.value
            40 -> _crafting.value = if(onSkillChange(_crafting.value!!, false)) _crafting.value!!.minus(1) else _crafting.value
            41 -> _disguise.value = if(onSkillChange(_disguise.value!!, false)) _disguise.value!!.minus(1) else _disguise.value
            42 -> _firstAid.value = if(onSkillChange(_firstAid.value!!, false)) _firstAid.value!!.minus(1) else _firstAid.value
            43 -> _forgery.value = if(onSkillChange(_alchemy.value!!, false)) _alchemy.value!!.minus(1) else _alchemy.value
            44 -> _pickLock.value = if(onSkillChange(_pickLock.value!!, false)) _pickLock.value!!.minus(1) else _pickLock.value
            45 -> _trapCrafting.value = if(onSkillChange(_trapCrafting.value!!, false)) _trapCrafting.value!!.minus(1) else _trapCrafting.value
            46 -> _courage.value = if(onSkillChange(_courage.value!!, false)) _courage.value!!.minus(1) else _courage.value
            47 -> _hexWeaving.value = if(onSkillChange(_hexWeaving.value!!, false)) _hexWeaving.value!!.minus(1) else _hexWeaving.value
            48 -> _intimidation.value = if(onSkillChange(_intimidation.value!!, false)) _intimidation.value!!.minus(1) else _intimidation.value
            49 -> _spellCasting.value = if(onSkillChange(_spellCasting.value!!, false)) _spellCasting.value!!.minus(1) else _spellCasting.value
            50 -> _resistMagic.value = if(onSkillChange(_resistMagic.value!!, false)) _resistMagic.value!!.minus(1) else _resistMagic.value
            51 -> _resistCoercion.value = if(onSkillChange(_resistCoercion.value!!, false)) _resistCoercion.value!!.minus(1) else _resistCoercion.value
            52 -> _ritualCrafting.value = if(onSkillChange(_ritualCrafting.value!!, false)) _ritualCrafting.value!!.minus(1) else _ritualCrafting.value

        }
    }

    private fun updateDerivedStats(){
        val multiplier: Int = (_body.value!! + _will.value!!)/2

        _stun.value = multiplier*10
        _run.value = _spd.value!!*3
        _leap.value = _run.value!!/5
        _maxHP.value =  multiplier*5
        _maxSta.value = multiplier*5
        _enc.value = _body.value!!*10
        _rec.value = multiplier

        when(_body.value){
            1,2 -> {_punch.value = "1d6 -4"; _kick.value = "1d6"}
            3,4 -> {_punch.value = "1d6 -2"; _kick.value = "1d6 +2"}
            5,6 -> {_punch.value = "1d6"; _kick.value = "1d6 +4"}
            7,8 -> {_punch.value = "1d6 +2"; _kick.value = "1d6 +6"}
            9,10 -> {_punch.value = "1d6 +4"; _kick.value = "1d6 +8"}
            11,12 -> {_punch.value = "1d6 +6"; _kick.value = "1d6 +10"}
            13 -> {_punch.value = "1d6 +8"; _kick.value = "1d6 +12"}
        }

    }

    fun onHealthChange(value: Int){
        if (value < 0){
            if (value.absoluteValue < _hp.value!!) {
                _hp.value = _hp.value?.plus(value)
            }
            else _hp.value = 0
        }
        else _hp.value = _hp.value?.plus(value)
    }

    fun onStaminaChange(value: Int){
        if (value < 0){
            if (value.absoluteValue < _sta.value!!) {
                _sta.value = _sta.value?.plus(value)
            }
            else _sta.value = 0
        }
        else _sta.value = _sta.value?.plus(value)
    }

    fun onCrownsChange(value: Int){
        if (value < 0){
            if (value.absoluteValue < _crowns.value!!) {
                _crowns.value = _crowns.value?.plus(value)
            }
            else _crowns.value = 0
        }
        else _crowns.value = _crowns.value?.plus(value)
    }

    fun increaseProfSkill(skill: Int){
        when (skill){
            1 -> _professionSkillA1.value = if(onProfessionSkillChange(_professionSkillA1.value!!, true, "a2"))
                _professionSkillA1.value!!.plus(1) else _professionSkillA1.value

            2 -> _professionSkillA2.value = if(onProfessionSkillChange(_professionSkillA2.value!!, true, "a3"))
                _professionSkillA2.value!!.plus(1) else _professionSkillA2.value

            3 -> _professionSkillA3.value = if(onProfessionSkillChange(_professionSkillA3.value!!, true, "-1"))
                _professionSkillA3.value!!.plus(1) else _professionSkillA3.value

            4 -> _professionSkillB1.value = if(onProfessionSkillChange(_professionSkillB1.value!!, true, "b2"))
                _professionSkillB1.value!!.plus(1) else _professionSkillB1.value

            5 -> _professionSkillB2.value = if(onProfessionSkillChange(_professionSkillB2.value!!, true, "b3"))
                _professionSkillB2.value!!.plus(1) else _professionSkillB2.value

            6 -> _professionSkillB3.value = if(onProfessionSkillChange(_professionSkillB3.value!!, true, "-1"))
                _professionSkillB3.value!!.plus(1) else _professionSkillB3.value

            7 -> _professionSkillC1.value = if(onProfessionSkillChange(_professionSkillC1.value!!, true, "c2"))
                _professionSkillC1.value!!.plus(1) else _professionSkillC1.value

            8 -> _professionSkillC2.value = if(onProfessionSkillChange(_professionSkillC2.value!!, true, "c3"))
                _professionSkillC2.value!!.plus(1) else _professionSkillC2.value

            9 -> _professionSkillC3.value = if(onProfessionSkillChange(_professionSkillC3.value!!, true, "-1"))
                _professionSkillC3.value!!.plus(1) else _professionSkillC3.value
        }
    }

    fun decreaseProfSkill(skill: Int){
        when (skill){
            1 -> _professionSkillA1.value = if(onProfessionSkillChange(_professionSkillA1.value!!, false, "a2"))
                _professionSkillA1.value!!.minus(1) else _professionSkillA1.value

            2 -> _professionSkillA2.value = if(onProfessionSkillChange(_professionSkillA2.value!!, false, "a3"))
                _professionSkillA2.value!!.minus(1) else _professionSkillA2.value

            3 -> _professionSkillA3.value = if(onProfessionSkillChange(_professionSkillA3.value!!, false, "-1"))
                _professionSkillA3.value!!.minus(1) else _professionSkillA3.value

            4 -> _professionSkillB1.value = if(onProfessionSkillChange(_professionSkillB1.value!!, false, "b2"))
                _professionSkillB1.value!!.minus(1) else _professionSkillB1.value

            5 -> _professionSkillB2.value = if(onProfessionSkillChange(_professionSkillB2.value!!, false, "b3"))
                _professionSkillB2.value!!.minus(1) else _professionSkillB2.value

            6 -> _professionSkillB3.value = if(onProfessionSkillChange(_professionSkillB3.value!!, false, "-1"))
                _professionSkillB3.value!!.minus(1) else _professionSkillB3.value

            7 -> _professionSkillC1.value = if(onProfessionSkillChange(_professionSkillC1.value!!, false, "c2"))
                _professionSkillC1.value!!.minus(1) else _professionSkillC1.value

            8 -> _professionSkillC2.value = if(onProfessionSkillChange(_professionSkillC2.value!!, false, "c3"))
                _professionSkillC2.value!!.minus(1) else _professionSkillC2.value

            9 -> _professionSkillC3.value = if(onProfessionSkillChange(_professionSkillC3.value!!, false, "-1"))
                _professionSkillC3.value!!.minus(1) else _professionSkillC3.value
        }
    }

    private fun onProfessionSkillChange(value: Int, increase: Boolean, nextSkill: String): Boolean{

        var newIP = iP.value!!
        var newVal = value

        if (increase) {
            if (value >= 10) return false

            if (newVal == 0) newVal = 1

            if (iP.value!! >= newVal) {
                newIP -= newVal
            } else return false
        }

        if (!increase) {
            if (value > 0) {
                when (nextSkill){
                    "a2" -> if (_professionSkillA2.value!! > 0) return false
                    "a3" -> if (_professionSkillA3.value!! > 0) return false
                    "b2" -> if (_professionSkillB2.value!! > 0) return false
                    "b3" -> if (_professionSkillB3.value!! > 0) return false
                    "c2" -> if (_professionSkillC2.value!! > 0) return false
                    "c3" -> if (_professionSkillC3.value!! > 0) return false
                }
                if (newVal == 1) newVal = 2

                newIP += (newVal - 1)

            } else return false
        }
        _iP.value = newIP

        return true

    }

    fun onInit(characterData: Character){

        this._uniqueID.value = characterData.id
        this._image.value = characterData.imagePath
        this._name.value = characterData.name
        this._iP.value = characterData.iP
        this._race.value = characterData.race
        this._gender.value = characterData.gender
        this._age.value = characterData.age
        this._profession.value = characterData.profession
        this._definingSkill.value = characterData.definingSkill
        this._crowns.value = characterData.crowns

        //Profession Skills
        this._professionSkillA1.value = characterData.professionSkillA1
        this._professionSkillA2.value = characterData.professionSkillA2
        this._professionSkillA3.value = characterData.professionSkillA3
        this._professionSkillB1.value = characterData.professionSkillB1
        this._professionSkillB2.value = characterData.professionSkillB2
        this._professionSkillB3.value = characterData.professionSkillB3
        this._professionSkillC1.value = characterData.professionSkillC1
        this._professionSkillC2.value = characterData.professionSkillC2
        this._professionSkillC3.value = characterData.professionSkillC3

        //Stats
        this._intelligence.value = characterData.intelligence
        this._ref.value = characterData.reflex
        this._dex.value = characterData.dexterity
        this._body.value = characterData.body
        this._spd.value = characterData.speed
        this._emp.value = characterData.empathy
        this._cra.value = characterData.craftsmanship
        this._will.value = characterData.will
        this._luck.value = characterData.luck
        this._stun.value = characterData.stun
        this._run.value = characterData.run
        this._leap.value = characterData.leap
        this._maxHP.value = characterData.MaxHP
        this._hp.value = characterData.hp
        this._maxSta.value = characterData.MaxStamina
        this._sta.value = characterData.stamina
        this._enc.value = characterData.encumbrance
        this._rec.value = characterData.recovery
        this._punch.value = characterData.punch
        this._kick.value = characterData.kick

        //Skills
        this._awareness.value = characterData.awareness
        this._business.value = characterData.business
        this._deduction.value = characterData.deduction
        this._education.value = characterData.education
        this._commonSpeech.value = characterData.commonSpeech
        this._elderSpeech.value = characterData.elderSpeech
        this._dwarven.value = characterData.dwarven
        this._monsterLore.value = characterData.monsterLore
        this._socialEtiquette.value = characterData.socialEtiquette
        this._streetwise.value = characterData.streetwise
        this._tactics.value = characterData.tactics
        this._teaching.value = characterData.teaching
        this._wildernessSurvival.value = characterData.wildernessSurvival
        this._brawling.value = characterData.brawling
        this._dodgeEscape.value = characterData.dodgeEscape
        this._melee.value = characterData.melee
        this._riding.value = characterData.riding
        this._sailing.value = characterData.sailing
        this._smallBlades.value = characterData.smallBlades
        this._staffSpear.value = characterData.staffSpear
        this._swordsmanship.value = characterData.swordsmanship
        this._archery.value = characterData.archery
        this._athletics.value = characterData.athletics
        this._crossbow.value = characterData.crossbow
        this._sleightOfHand.value = characterData.sleightOfHand
        this._stealth.value = characterData.stealth
        this._physique.value = characterData.physique
        this._endurance.value = characterData.endurance
        this._charisma.value = characterData.charisma
        this._deceit.value = characterData.deceit
        this._fineArts.value = characterData.fineArts
        this._gambling.value = characterData.gambling
        this._groomingAndStyle.value = characterData.groomingAndStyle
        this._humanPerception.value = characterData.humanPerception
        this._leadership.value = characterData.leadership
        this._persuasion.value = characterData.persuasion
        this._performance.value = characterData.performance
        this._seduction.value = characterData.seduction
        this._alchemy.value = characterData.alchemy
        this._crafting.value = characterData.crafting
        this._disguise.value = characterData.disguise
        this._firstAid.value = characterData.firstAid
        this._forgery.value = characterData.forgery
        this._pickLock.value = characterData.pickLock
        this._trapCrafting.value = characterData.trapCrafting
        this._courage.value = characterData.courage
        this._hexWeaving.value = characterData.hexWeaving
        this._intimidation.value = characterData.intimidation
        this._spellCasting.value = characterData.spellCasting
        this._resistMagic.value = characterData.resistMagic
        this._resistCoercion.value = characterData.resistCoercion
        this._ritualCrafting.value = characterData.ritualCrafting

        //Magic
        this._vigor.value = characterData.vigor

        //Mages
        this._noviceSpellList.value = characterData.noviceSpells
        this._journeymanSpellList.value = characterData.journeymanSpells
        this._masterSpellList.value = characterData.masterSpells

        //Priests
        this._noviceDruidInvocations.value = characterData.noviceDruidInvocations
        this._journeymanDruidInvocations.value = characterData.journeymanDruidInvocations
        this._masterDruidInvocations.value = characterData.masterDruidInvocations

        this._novicePreacherInvocations.value = characterData.novicePreacherInvocations
        this._journeymanPreacherInvocations.value = characterData.journeymanPreacherInvocations
        this._masterPreacherInvocations.value = characterData.masterPreacherInvocations

        this._archPriestInvocations.value = characterData.archPriestInvocations

        //Signs
        this._basicSigns.value = characterData.basicSigns
        this._alternateSigns.value = characterData.alternateSigns

        //Rituals
        this._noviceRitualList.value = characterData.noviceRituals
        this._journeymanRitualList.value = characterData.journeymanRituals
        this._masterRitualList.value = characterData.masterRituals

        //Hexes
        this._hexesList.value = characterData.hexes

        //Equipment
        this._headEquipment.value = characterData.headEquipment


    }

}

