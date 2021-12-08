package com.example.thewitcherrpg.core.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.example.thewitcherrpg.core.Constants
import com.example.thewitcherrpg.feature_character_sheet.domain.models.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "character_table")
data class Character(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var imagePath: String,
    var name: String,
    var iP: Int,
    var race: String,
    var gender: String,
    var age: Int,
    var profession: Constants.Professions,
    var definingSkill: String,
    var racePerks: String,
    var definingSkillInfo: String,
    var crowns: Int = 0,
    var clothing: String = "",
    var hairStyle: String = "",
    var personality: String = "",
    var affectations: String = "",
    var valuedPerson: String = "",
    var values: String = "",
    var feelingsOnPeople: String = "",
    var socialStanding: String = "",
    var reputation: String = "",
    var lifeEvents: ArrayList<LifeEvent> = arrayListOf(),

    //Profession Skills
    var professionSkillA1: Int = 0,
    var professionSkillA2: Int = 0,
    var professionSkillA3: Int = 0,
    var professionSkillB1: Int = 0,
    var professionSkillB2: Int = 0,
    var professionSkillB3: Int = 0,
    var professionSkillC1: Int = 0,
    var professionSkillC2: Int = 0,
    var professionSkillC3: Int = 0,

    //Stats
    var intelligence: Int = 0,
    var intelligenceModifier: Int = 0,
    var reflex: Int = 0,
    var reflexModifier: Int = 0,
    var dexterity: Int = 0,
    var dexterityModifier: Int = 0,
    var body: Int = 0,
    var bodyModifier: Int = 0,
    var speed: Int = 0,
    var speedModifier: Int = 0,
    var empathy: Int = 0,
    var empathyModifier: Int = 0,
    var craftsmanship: Int = 0,
    var craftsmanshipModifier: Int = 0,
    var will: Int = 0,
    var willModifier: Int = 0,
    var luck: Int = 0,
    var luckModifier: Int = 0,
    var stun: Int = 0,
    var stunModifier: Int = 0,
    var run: Int = 0,
    var runModifier: Int = 0,
    var leap: Int = 0,
    var leapModifier: Int = 0,
    var MaxHP: Int = 0,
    var hpModifier: Int = 0,
    var hp: Int = 0,
    var MaxStamina: Int = 0,
    var staModifier: Int = 0,
    var stamina: Int = 0,
    var encumbrance: Int = 0,
    var encumbranceModifier: Int = 0,
    var currentEncumbrance: Float = 0F,
    var recovery: Int = 0,
    var recoveryModifier: Int = 0,
    var punch: String = "",
    var kick: String = "",

    //Skills
    var definingSkillValue: Int = 0,
    var awareness: Int = 0,
    var awarenessModifier: Int = 0,
    var business: Int = 0,
    var businessModifier: Int = 0,
    var deduction: Int = 0,
    var deductionModifier: Int = 0,
    var education: Int = 0,
    var educationModifier: Int = 0,
    var commonSpeech: Int = 0,
    var commonSpeechModifier: Int = 0,
    var elderSpeech: Int = 0,
    var elderSpeechModifier: Int = 0,
    var dwarven: Int = 0,
    var dwarvenModifier: Int = 0,
    var monsterLore: Int = 0,
    var monsterLoreModifier: Int = 0,
    var socialEtiquette: Int = 0,
    var socialEtiquetteModifier: Int = 0,
    var streetwise: Int = 0,
    var streetwiseModifier: Int = 0,
    var tactics: Int = 0,
    var tacticsModifier: Int = 0,
    var teaching: Int = 0,
    var teachingModifier: Int = 0,
    var wildernessSurvival: Int = 0,
    var wildernessSurvivalModifier: Int = 0,
    var brawling: Int = 0,
    var brawlingModifier: Int = 0,
    var dodgeEscape: Int = 0,
    var dodgeEscapeModifier: Int = 0,
    var melee: Int = 0,
    var meleeModifier: Int = 0,
    var riding: Int = 0,
    var ridingModifier: Int = 0,
    var sailing: Int = 0,
    var sailingModifier: Int = 0,
    var smallBlades: Int = 0,
    var smallBladesModifier: Int = 0,
    var staffSpear: Int = 0,
    var staffSpearModifier: Int = 0,
    var swordsmanship: Int = 0,
    var swordsmanshipModifier: Int = 0,
    var archery: Int = 0,
    var archeryModifier: Int = 0,
    var athletics: Int = 0,
    var athleticsModifier: Int = 0,
    var crossbow: Int = 0,
    var crossbowModifier: Int = 0,
    var sleightOfHand: Int = 0,
    var sleightOfHandModifier: Int = 0,
    var stealth: Int = 0,
    var stealthModifier: Int = 0,
    var physique: Int = 0,
    var physiqueModifier: Int = 0,
    var endurance: Int = 0,
    var enduranceModifier: Int = 0,
    var charisma: Int = 0,
    var charismaModifier: Int = 0,
    var deceit: Int = 0,
    var deceitModifier: Int = 0,
    var fineArts: Int = 0,
    var fineArtsModifier: Int = 0,
    var gambling: Int = 0,
    var gamblingModifier: Int = 0,
    var groomingAndStyle: Int = 0,
    var groomingAndStyleModifier: Int = 0,
    var humanPerception: Int = 0,
    var humanPerceptionModifier: Int = 0,
    var leadership: Int = 0,
    var leadershipModifier: Int = 0,
    var persuasion: Int = 0,
    var persuasionModifier: Int = 0,
    var performance: Int = 0,
    var performanceModifier: Int = 0,
    var seduction: Int = 0,
    var seductionModifier: Int = 0,
    var alchemy: Int = 0,
    var alchemyModifier: Int = 0,
    var crafting: Int = 0,
    var craftingModifier: Int = 0,
    var disguise: Int = 0,
    var disguiseModifier: Int = 0,
    var firstAid: Int = 0,
    var firstAidModifier: Int = 0,
    var forgery: Int = 0,
    var forgeryModifier: Int = 0,
    var pickLock: Int = 0,
    var pickLockModifier: Int = 0,
    var trapCrafting: Int = 0,
    var trapCraftingModifier: Int = 0,
    var courage: Int = 0,
    var courageModifier: Int = 0,
    var hexWeaving: Int = 0,
    var hexWeavingModifier: Int = 0,
    var intimidation: Int = 0,
    var intimidationModifier: Int = 0,
    var spellCasting: Int = 0,
    var spellCastingModifier: Int = 0,
    var resistMagic: Int = 0,
    var resistMagicModifier: Int = 0,
    var resistCoercion: Int = 0,
    var resistCoercionModifier: Int = 0,
    var ritualCrafting: Int = 0,
    var ritualCraftingModifier: Int = 0,

    //##### Magic #################################################
    var vigor: Int = 0,
    var focus: Int = 0,

    var basicSigns: ArrayList<MagicItem> = arrayListOf(),
    var alternateSigns: ArrayList<MagicItem> = arrayListOf(),

    var noviceRituals: ArrayList<MagicItem> = arrayListOf(),
    var journeymanRituals: ArrayList<MagicItem> = arrayListOf(),
    var masterRituals: ArrayList<MagicItem> = arrayListOf(),

    var hexes: ArrayList<MagicItem> = arrayListOf(),

    //Mages
    var noviceSpells: ArrayList<MagicItem> = arrayListOf(),
    var journeymanSpells: ArrayList<MagicItem> = arrayListOf(),
    var masterSpells: ArrayList<MagicItem> = arrayListOf(),

    //Priests
    var noviceDruidInvocations: ArrayList<MagicItem> = arrayListOf(),
    var journeymanDruidInvocations: ArrayList<MagicItem> = arrayListOf(),
    var masterDruidInvocations: ArrayList<MagicItem> = arrayListOf(),

    var novicePreacherInvocations: ArrayList<MagicItem> = arrayListOf(),
    var journeymanPreacherInvocations: ArrayList<MagicItem> = arrayListOf(),
    var masterPreacherInvocations: ArrayList<MagicItem> = arrayListOf(),

    var archPriestInvocations: ArrayList<MagicItem> = arrayListOf(),

    //Equipment
    var headEquipment: ArrayList<EquipmentItem> = arrayListOf(),
    var equippedHead: EquipmentItem? = null,

    var chestEquipment: ArrayList<EquipmentItem> = arrayListOf(),
    var equippedChest: EquipmentItem? = null,

    var legEquipment: ArrayList<EquipmentItem> = arrayListOf(),
    var equippedLegs: EquipmentItem? = null,

    var accessoryEquipment: ArrayList<EquipmentItem> = arrayListOf(),
    var equippedSecondHandShield: EquipmentItem? = null,
    var equippedSecondHandWeapon: WeaponItem? = null,

    var miscEquipment: ArrayList<EquipmentItem> = arrayListOf(),

    //Weapons
    var weaponEquipment: ArrayList<WeaponItem> = arrayListOf(),
    var equippedWeapon: WeaponItem? = null,

    //Campaign Notes
    var campaignNotes: ArrayList<CampaignNote> = arrayListOf()


): Parcelable

class MyTypeConverters {

    @TypeConverter
    fun fromStringToItem(item: String?): EquipmentItem?{
        val itemType =object :TypeToken<EquipmentItem>(){}.type
        return Gson().fromJson(item, itemType)
    }

    @TypeConverter
    fun fromItem(item: EquipmentItem?): String{
        return Gson().toJson(item)
    }

    @TypeConverter
    fun fromItemArrayListToJson(list: ArrayList<EquipmentItem?>): String{
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromJsonToItemArrayList(list: String): ArrayList<EquipmentItem>{
        val itemType =object :TypeToken<ArrayList<EquipmentItem>>(){}.type
        return Gson().fromJson(list, itemType)
    }

    @TypeConverter
    fun fromProfession(profession: Constants.Professions): String{
        return Gson().toJson(profession)
    }

    @TypeConverter
    fun fromStringToProfession(profession: String): Constants.Professions?{
        val prof = object :TypeToken<Constants.Professions>(){}.type
        return Gson().fromJson(profession, prof)
    }

    @TypeConverter
    fun fromMagicItemArrayListToJson(list: ArrayList<MagicItem?>): String{
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromJsonToMagicItemArrayList(list: String): ArrayList<MagicItem>{
        val itemType =object :TypeToken<ArrayList<MagicItem>>(){}.type
        return Gson().fromJson(list, itemType)
    }

    @TypeConverter
    fun fromStringToWeaponItem(item: String?): WeaponItem?{
        val itemType =object :TypeToken<WeaponItem>(){}.type
        return Gson().fromJson(item, itemType)
    }

    @TypeConverter
    fun fromWeaponItem(item: WeaponItem?): String{
        return Gson().toJson(item)
    }

    @TypeConverter
    fun fromWeaponItemArrayListToJson(list: ArrayList<WeaponItem?>): String{
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromJsonToWeaponItemArrayList(list: String): ArrayList<WeaponItem>{
        val itemType =object :TypeToken<ArrayList<WeaponItem>>(){}.type
        return Gson().fromJson(list, itemType)
    }

    @TypeConverter
    fun fromLifeEventArrayListToJson(list: ArrayList<LifeEvent?>): String{
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromJsonToLifeEventArrayList(list: String): ArrayList<LifeEvent>{
        val itemType =object :TypeToken<ArrayList<LifeEvent>>(){}.type
        return Gson().fromJson(list, itemType)
    }

    @TypeConverter
    fun fromCampaignNoteArrayListToJson(list: ArrayList<CampaignNote?>): String{
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromJsonToCampaignNoteArrayList(list: String): ArrayList<CampaignNote>{
        val itemType =object :TypeToken<ArrayList<CampaignNote>>(){}.type
        return Gson().fromJson(list, itemType)
    }

}