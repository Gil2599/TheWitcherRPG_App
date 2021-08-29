package com.example.thewitcherrpg.characterSheet

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.thewitcherrpg.R
import com.example.thewitcherrpg.data.Character
import com.example.thewitcherrpg.data.CharacterViewModel
import com.example.thewitcherrpg.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var mCharViewModel: CharacterViewModel
    private lateinit var toggle: ActionBarDrawerToggle
    lateinit var binding: ActivityMainBinding
    lateinit var visibleFrag: Fragment
    lateinit var characterData: Character

    private val charFragment = CharFragment()
    private val statsFragment = StatsFragment()
    private val skillsFragment = SkillsFragment()

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


        fragmentManager.beginTransaction().add(R.id.fragmentContainerView3, charFragment).setReorderingAllowed(true).commit()
        visibleFrag = charFragment
        fragmentManager.beginTransaction().add(R.id.fragmentContainerView3, skillsFragment).hide(skillsFragment).commit()
        fragmentManager.beginTransaction().add(R.id.fragmentContainerView3, statsFragment).hide(statsFragment).commit()

        binding.navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.Character -> {
                    fragmentManager.beginTransaction().hide(visibleFrag).show(charFragment).commit()
                    visibleFrag = charFragment }
                R.id.Skills -> {
                    fragmentManager.beginTransaction().hide(visibleFrag).show(skillsFragment).commit()
                    visibleFrag = skillsFragment }
                R.id.Stats -> {
                    fragmentManager.beginTransaction().hide(visibleFrag).show(statsFragment).commit()
                    visibleFrag = statsFragment
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

        //Toast.makeText(this, updatedCharacter.crowns.toString(), Toast.LENGTH_SHORT).show()

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
        //sharedViewModel.setSocialStanding((characterData.socialStanding))

    }

}
