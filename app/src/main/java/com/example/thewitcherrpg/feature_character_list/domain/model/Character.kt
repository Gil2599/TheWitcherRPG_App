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
    var crowns: Int,

    //Profession Skills
    var professionSkillA1: Int,
    var professionSkillA2: Int,
    var professionSkillA3: Int,
    var professionSkillB1: Int,
    var professionSkillB2: Int,
    var professionSkillB3: Int,
    var professionSkillC1: Int,
    var professionSkillC2: Int,
    var professionSkillC3: Int,

    //Stats
    var intelligence: Int,
    var reflex: Int,
    var dexterity: Int,
    var body: Int,
    var speed: Int,
    var empathy: Int,
    var craftsmanship: Int,
    var will: Int,
    var luck: Int,
    var stun: Int,
    var run: Int,
    var leap: Int,
    var MaxHP: Int,
    var hp: Int,
    var MaxStamina: Int,
    var stamina: Int,
    var encumbrance: Int,
    var recovery: Int,
    var punch: String,
    var kick: String,

    //Skills
    var awareness: Int,
    var business: Int,
    var deduction: Int,
    var education: Int,
    var commonSpeech: Int,
    var elderSpeech: Int,
    var dwarven: Int,
    var monsterLore: Int,
    var socialEtiquette: Int,
    var streetwise: Int,
    var tactics: Int,
    var teaching: Int,
    var wildernessSurvival: Int,
    var brawling: Int,
    var dodgeEscape: Int,
    var melee: Int,
    var riding: Int,
    var sailing: Int,
    var smallBlades: Int,
    var staffSpear: Int,
    var swordsmanship: Int,
    var archery: Int,
    var athletics: Int,
    var crossbow: Int,
    var sleightOfHand: Int,
    var stealth: Int,
    var physique: Int,
    var endurance: Int,
    var charisma: Int,
    var deceit: Int,
    var fineArts: Int,
    var gambling: Int,
    var groomingAndStyle: Int,
    var humanPerception: Int,
    var leadership: Int,
    var persuasion: Int,
    var performance: Int,
    var seduction: Int,
    var alchemy: Int,
    var crafting: Int,
    var disguise: Int,
    var firstAid: Int,
    var forgery: Int,
    var pickLock: Int,
    var trapCrafting: Int,
    var courage: Int,
    var hexWeaving: Int,
    var intimidation: Int,
    var spellCasting: Int,
    var resistMagic: Int,
    var resistCoercion: Int,
    var ritualCrafting: Int,

    //##### Magic #################################################
    var vigor: Int,

    var basicSigns: ArrayList<String>,
    var alternateSigns: ArrayList<String>,

    var noviceRituals: ArrayList<String>,
    var journeymanRituals: ArrayList<String>,
    var masterRituals: ArrayList<String>,

    var hexes: ArrayList<String>,

    //Mages
    var noviceSpells: ArrayList<String>,
    var journeymanSpells: ArrayList<String>,
    var masterSpells: ArrayList<String>,

    //Priests
    var noviceDruidInvocations: ArrayList<String>,
    var journeymanDruidInvocations: ArrayList<String>,
    var masterDruidInvocations: ArrayList<String>,

    var novicePreacherInvocations: ArrayList<String>,
    var journeymanPreacherInvocations: ArrayList<String>,
    var masterPreacherInvocations: ArrayList<String>,

    var archPriestInvocations: ArrayList<String>,

    //Equipment
    var headEquipment: ArrayList<EquipmentItem>,
    var equippedHead: EquipmentItem?,

    var chestEquipment: ArrayList<EquipmentItem>,
    var equippedChest: EquipmentItem?,

    var legEquipment: ArrayList<EquipmentItem>,
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

