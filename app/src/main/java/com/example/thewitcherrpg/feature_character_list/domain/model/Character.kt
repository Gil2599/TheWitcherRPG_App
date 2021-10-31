package com.example.thewitcherrpg.feature_character_list.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.example.thewitcherrpg.feature_character_sheet.presentation.equipment.EquipmentItem
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
    var profession: String,
    var definingSkill: String,
    var crowns: Int = 0,

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
    var reflex: Int = 0,
    var dexterity: Int = 0,
    var body: Int = 0,
    var speed: Int = 0,
    var empathy: Int = 0,
    var craftsmanship: Int = 0,
    var will: Int = 0,
    var luck: Int = 0,
    var stun: Int = 0,
    var run: Int = 0,
    var leap: Int = 0,
    var MaxHP: Int = 0,
    var hp: Int = 0,
    var MaxStamina: Int = 0,
    var stamina: Int = 0,
    var encumbrance: Int = 0,
    var recovery: Int = 0,
    var punch: String = "",
    var kick: String = "",

    //Skills
    var awareness: Int = 0,
    var business: Int = 0,
    var deduction: Int = 0,
    var education: Int = 0,
    var commonSpeech: Int = 0,
    var elderSpeech: Int = 0,
    var dwarven: Int = 0,
    var monsterLore: Int = 0,
    var socialEtiquette: Int = 0,
    var streetwise: Int = 0,
    var tactics: Int = 0,
    var teaching: Int = 0,
    var wildernessSurvival: Int = 0,
    var brawling: Int = 0,
    var dodgeEscape: Int = 0,
    var melee: Int = 0,
    var riding: Int = 0,
    var sailing: Int = 0,
    var smallBlades: Int = 0,
    var staffSpear: Int = 0,
    var swordsmanship: Int = 0,
    var archery: Int = 0,
    var athletics: Int = 0,
    var crossbow: Int = 0,
    var sleightOfHand: Int = 0,
    var stealth: Int = 0,
    var physique: Int = 0,
    var endurance: Int = 0,
    var charisma: Int = 0,
    var deceit: Int = 0,
    var fineArts: Int = 0,
    var gambling: Int = 0,
    var groomingAndStyle: Int = 0,
    var humanPerception: Int = 0,
    var leadership: Int = 0,
    var persuasion: Int = 0,
    var performance: Int = 0,
    var seduction: Int = 0,
    var alchemy: Int = 0,
    var crafting: Int = 0,
    var disguise: Int = 0,
    var firstAid: Int = 0,
    var forgery: Int = 0,
    var pickLock: Int = 0,
    var trapCrafting: Int = 0,
    var courage: Int = 0,
    var hexWeaving: Int = 0,
    var intimidation: Int = 0,
    var spellCasting: Int = 0,
    var resistMagic: Int = 0,
    var resistCoercion: Int = 0,
    var ritualCrafting: Int = 0,

    //##### Magic #################################################
    var vigor: Int = 0,

    var basicSigns: ArrayList<String> = arrayListOf(),
    var alternateSigns: ArrayList<String> = arrayListOf(),

    var noviceRituals: ArrayList<String> = arrayListOf(),
    var journeymanRituals: ArrayList<String> = arrayListOf(),
    var masterRituals: ArrayList<String> = arrayListOf(),

    var hexes: ArrayList<String> = arrayListOf(),

    //Mages
    var noviceSpells: ArrayList<String> = arrayListOf(),
    var journeymanSpells: ArrayList<String> = arrayListOf(),
    var masterSpells: ArrayList<String> = arrayListOf(),

    //Priests
    var noviceDruidInvocations: ArrayList<String> = arrayListOf(),
    var journeymanDruidInvocations: ArrayList<String> = arrayListOf(),
    var masterDruidInvocations: ArrayList<String> = arrayListOf(),

    var novicePreacherInvocations: ArrayList<String> = arrayListOf(),
    var journeymanPreacherInvocations: ArrayList<String> = arrayListOf(),
    var masterPreacherInvocations: ArrayList<String> = arrayListOf(),

    var archPriestInvocations: ArrayList<String> = arrayListOf(),

    //Equipment
    var headEquipment: ArrayList<EquipmentItem> = arrayListOf(),
    var equippedHead: EquipmentItem?,

    var chestEquipment: ArrayList<EquipmentItem> = arrayListOf(),
    var equippedChest: EquipmentItem?,

    var legEquipment: ArrayList<EquipmentItem> = arrayListOf(),
    var equippedLegs: EquipmentItem?

): Parcelable

class MyTypeConverters {

    @TypeConverter
    fun fromString(value: String?): ArrayList<String>{
        val listType =object :TypeToken<ArrayList<String>>(){}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: ArrayList<String?>): String{
        return Gson().toJson(list)
    }

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


}

