package com.example.thewitcherrpg.characterSheet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.example.thewitcherrpg.R
import com.example.thewitcherrpg.characterSheet.magic.SpellsParentFragment
import com.example.thewitcherrpg.data.Character
import com.example.thewitcherrpg.data.CharacterViewModel
import com.example.thewitcherrpg.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var mCharViewModel: CharacterViewModel
    private lateinit var toggle: ActionBarDrawerToggle
    lateinit var binding: ActivityMainBinding
    lateinit var characterData: Character

    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mCharViewModel = ViewModelProvider(this).get(CharacterViewModel::class.java)
        characterData = intent?.getParcelableExtra("EXTRA_DATA")!!

        charInit()

        val drawerLayout = binding.drawerLayout

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val fragmentManager = supportFragmentManager

        fragmentManager.beginTransaction().replace(R.id.fragmentContainerView3, CharFragment())
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit()

        binding.navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.Character -> {
                    fragmentManager.beginTransaction().replace(R.id.fragmentContainerView3, CharFragment())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit() }
                R.id.Skills -> {
                    fragmentManager.beginTransaction().replace(R.id.fragmentContainerView3, SkillsFragment())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit() }
                R.id.Stats -> {
                    fragmentManager.beginTransaction().replace(R.id.fragmentContainerView3, StatsFragment())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit()
                }
                R.id.ProfessionSkillTree -> {
                    fragmentManager.beginTransaction().replace(R.id.fragmentContainerView3, ProfessionSkillTree())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit()
                }
                R.id.Magic -> {
                    fragmentManager.beginTransaction().replace(R.id.fragmentContainerView3, SpellsParentFragment())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit()
                }
            }
            drawerLayout.closeDrawer(binding.navView)
            true
        }

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.menu_delete){
            deleteCharacter()
        }
        if(item.itemId == R.id.menu_save){
            saveCharacter()
        }
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteCharacter() {
        val builder = AlertDialog.Builder(this)
        builder.setPositiveButton("Yes"){_, _ ->
            mCharViewModel.deleteChar(characterData)

            //Deletes the character image associated with this character
            val path = sharedViewModel.image.value.toString()
            val f = File(path, sharedViewModel.uniqueID.value.toString() + ".jpeg")
            f.delete()

            Toast.makeText(this, "Character Deleted", Toast.LENGTH_SHORT).show()
            finish()
        }
        builder.setNegativeButton("No"){_, _ -> }
        builder.setTitle("Delete character?")
        builder.create().show()
    }

    private fun saveCharacter(){

        val updatedCharacter = sharedViewModel.onSaveFinal()
        val charID = characterData.id
        updatedCharacter.id = charID

        //Toast.makeText(this, updatedCharacter.professionSkillA1.toString(), Toast.LENGTH_SHORT).show()

        mCharViewModel.updateChar(updatedCharacter)

        Toast.makeText(this, "Saved Successfully!", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.delete_save_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun charInit(){

        sharedViewModel.setID(characterData.id)
        sharedViewModel.setImagePath(characterData.imagePath)
        sharedViewModel.setName(characterData.name)
        sharedViewModel.setIP(characterData.iP)
        sharedViewModel.setRace(characterData.race)
        sharedViewModel.setGender(characterData.gender)
        sharedViewModel.setAge(characterData.age)
        sharedViewModel.setProfession(characterData.profession)
        sharedViewModel.setDefiningSkill(characterData.definingSkill)
        sharedViewModel.setCrowns(characterData.crowns)

        //Profession Skills
        sharedViewModel.setProfessionSkillA1(characterData.professionSkillA1)
        sharedViewModel.setProfessionSkillA2(characterData.professionSkillA2)
        sharedViewModel.setProfessionSkillA3(characterData.professionSkillA3)
        sharedViewModel.setProfessionSkillB1(characterData.professionSkillB1)
        sharedViewModel.setProfessionSkillB2(characterData.professionSkillB2)
        sharedViewModel.setProfessionSkillB3(characterData.professionSkillB3)
        sharedViewModel.setProfessionSkillC1(characterData.professionSkillC1)
        sharedViewModel.setProfessionSkillC2(characterData.professionSkillC2)
        sharedViewModel.setProfessionSkillC3(characterData.professionSkillC3)

        //Stats
        sharedViewModel.setIntelligence(characterData.intelligence)
        sharedViewModel.setReflex(characterData.reflex)
        sharedViewModel.setDexterity(characterData.dexterity)
        sharedViewModel.setBody(characterData.body)
        sharedViewModel.setSpeed(characterData.speed)
        sharedViewModel.setEmpathy(characterData.empathy)
        sharedViewModel.setCraftsmanship(characterData.craftsmanship)
        sharedViewModel.setWill(characterData.will)
        sharedViewModel.setLuck(characterData.luck)
        sharedViewModel.setStun(characterData.stun)
        sharedViewModel.setRun(characterData.run)
        sharedViewModel.setLeap(characterData.leap)
        sharedViewModel.setMaxHP(characterData.MaxHP)
        sharedViewModel.setHP(characterData.hp)
        sharedViewModel.setMaxStamina(characterData.MaxStamina)
        sharedViewModel.setStamina(characterData.stamina)
        sharedViewModel.setEncumbrance(characterData.encumbrance)
        sharedViewModel.setRecovery(characterData.recovery)
        sharedViewModel.setPunch(characterData.punch)
        sharedViewModel.setKick(characterData.kick)

        //Skills
        sharedViewModel.setAwareness(characterData.awareness)
        sharedViewModel.setBusiness(characterData.business)
        sharedViewModel.setDeduction(characterData.deduction)
        sharedViewModel.setEducation(characterData.education)
        sharedViewModel.setCommonSpeech(characterData.commonSpeech)
        sharedViewModel.setElderSpeech(characterData.elderSpeech)
        sharedViewModel.setDwarven(characterData.dwarven)
        sharedViewModel.setMonsterLore(characterData.monsterLore)
        sharedViewModel.setSocialEtiquette(characterData.socialEtiquette)
        sharedViewModel.setStreetwise(characterData.streetwise)
        sharedViewModel.setTactics(characterData.tactics)
        sharedViewModel.setTeaching(characterData.teaching)
        sharedViewModel.setWildernessSurvival(characterData.wildernessSurvival)
        sharedViewModel.setBrawling(characterData.brawling)
        sharedViewModel.setDodgeEscape(characterData.dodgeEscape)
        sharedViewModel.setMelee(characterData.melee)
        sharedViewModel.setRiding(characterData.riding)
        sharedViewModel.setSailing(characterData.sailing)
        sharedViewModel.setSmallBlades(characterData.smallBlades)
        sharedViewModel.setStaffSpear(characterData.staffSpear)
        sharedViewModel.setSwordsmanship(characterData.swordsmanship)
        sharedViewModel.setArchery(characterData.archery)
        sharedViewModel.setAthletics(characterData.athletics)
        sharedViewModel.setCrossbow(characterData.crossbow)
        sharedViewModel.setSleightOfHand(characterData.sleightOfHand)
        sharedViewModel.setStealth(characterData.stealth)
        sharedViewModel.setPhysique(characterData.physique)
        sharedViewModel.setEndurance(characterData.endurance)
        sharedViewModel.setCharisma(characterData.charisma)
        sharedViewModel.setDeceit(characterData.deceit)
        sharedViewModel.setFineArts(characterData.fineArts)
        sharedViewModel.setGambling(characterData.gambling)
        sharedViewModel.setGroomingAndStyle(characterData.groomingAndStyle)
        sharedViewModel.setHumanPerception(characterData.humanPerception)
        sharedViewModel.setLeadership(characterData.leadership)
        sharedViewModel.setPersuasion(characterData.persuasion)
        sharedViewModel.setPerformance(characterData.performance)
        sharedViewModel.setSeduction(characterData.seduction)
        sharedViewModel.setAlchemy(characterData.alchemy)
        sharedViewModel.setCrafting(characterData.crafting)
        sharedViewModel.setDisguise(characterData.disguise)
        sharedViewModel.setFirstAid(characterData.firstAid)
        sharedViewModel.setForgery(characterData.forgery)
        sharedViewModel.setPickLock(characterData.pickLock)
        sharedViewModel.setTrapCrafting(characterData.trapCrafting)
        sharedViewModel.setCourage(characterData.courage)
        sharedViewModel.setHexWeaving(characterData.hexWeaving)
        sharedViewModel.setIntimidation(characterData.intimidation)
        sharedViewModel.setSpellCasting(characterData.spellCasting)
        sharedViewModel.setResistMagic(characterData.resistMagic)
        sharedViewModel.setResistCoercion(characterData.resistCoercion)
        sharedViewModel.setRitualCrafting(characterData.ritualCrafting)

        //Spells
        sharedViewModel.setSpellList(characterData.spells)

    }

    override fun onBackPressed() {
        //Check if there is a callback set by a fragment
        if (this.onBackPressedDispatcher.hasEnabledCallbacks()){
            super.onBackPressed()
            return;
        }

        //If no callbacks are set by fragments, ask user if they would like to save character or cancel
        val builder = AlertDialog.Builder(this)
        builder.setPositiveButton("Yes"){_, _ ->
            saveCharacter()
            finish()
            }
        builder.setNegativeButton("No"){_, _ -> finish()}

        builder.setNeutralButton("Cancel"){_, _ -> }

        builder.setTitle("Save changes to character?")
        builder.setMessage("All unsaved changes will be lost.")
        builder.create().show()

    }

}
