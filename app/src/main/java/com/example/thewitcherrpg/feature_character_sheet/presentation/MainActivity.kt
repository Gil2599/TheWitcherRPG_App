package com.example.thewitcherrpg.feature_character_sheet.presentation

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
import com.example.thewitcherrpg.feature_character_sheet.presentation.equipment.EquipmentParentFragment
import com.example.thewitcherrpg.feature_character_sheet.presentation.magic.MagicParentFragment
import com.example.thewitcherrpg.feature_character_list.domain.model.Character
import com.example.thewitcherrpg.feature_character_list.presentation.CharacterListViewModel
import com.example.thewitcherrpg.databinding.ActivityMainBinding
import com.example.thewitcherrpg.feature_character_sheet.SharedViewModel
import com.example.thewitcherrpg.feature_character_sheet.presentation.character_information.CharFragment
import com.example.thewitcherrpg.feature_character_sheet.presentation.profession_skill_tree.ProfessionSkillTree
import com.example.thewitcherrpg.feature_character_sheet.presentation.skills.SkillsFragment
import com.example.thewitcherrpg.feature_character_sheet.presentation.stats.StatsFragment
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var mCharListViewModel: CharacterListViewModel
    private lateinit var toggle: ActionBarDrawerToggle
    lateinit var binding: ActivityMainBinding
    private lateinit var characterData: Character

    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mCharListViewModel = ViewModelProvider(this).get(CharacterListViewModel::class.java)
        characterData = intent?.getParcelableExtra("EXTRA_DATA")!! //Get character data


        sharedViewModel.onInit(characterData) //Initialize viewModel with character data

        //Set up navigation drawer
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
                    fragmentManager.beginTransaction().replace(R.id.fragmentContainerView3, MagicParentFragment())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit()
                }
                R.id.Equipment -> {
                    fragmentManager.beginTransaction().replace(R.id.fragmentContainerView3, EquipmentParentFragment())
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
            mCharListViewModel.deleteChar(characterData)

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

        mCharListViewModel.updateChar(updatedCharacter)

        Toast.makeText(this, "Saved Successfully!", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.delete_save_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onBackPressed() {
        //Check if there is a callback set by a fragment then don't close activity
        if (this.onBackPressedDispatcher.hasEnabledCallbacks()){
            super.onBackPressed()
            return
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
