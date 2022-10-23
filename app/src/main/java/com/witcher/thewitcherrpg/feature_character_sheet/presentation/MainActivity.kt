package com.witcher.thewitcherrpg.feature_character_sheet.presentation

import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.model.KeyPath
import com.google.android.material.color.MaterialColors
import com.google.android.material.snackbar.Snackbar
import com.witcher.thewitcherrpg.R
import com.witcher.thewitcherrpg.core.presentation.CustomEditStatDialog
import com.witcher.thewitcherrpg.core.presentation.MainCharacterViewModel
import com.witcher.thewitcherrpg.databinding.ActivityMainBinding
import com.witcher.thewitcherrpg.databinding.NavHeaderBinding
import com.witcher.thewitcherrpg.feature_character_creation.presentation.CharCreationFirstFrag
import com.witcher.thewitcherrpg.feature_character_sheet.presentation.campaign_notes.CampaignNotesFragment
import com.witcher.thewitcherrpg.feature_character_sheet.presentation.character_information.CharFragment
import com.witcher.thewitcherrpg.feature_character_sheet.presentation.equipment.EquipmentParentFragment
import com.witcher.thewitcherrpg.feature_character_sheet.presentation.magic.MagicParentFragment
import com.witcher.thewitcherrpg.feature_character_sheet.presentation.profession_skill_tree.ProfessionSkillTree
import com.witcher.thewitcherrpg.feature_character_sheet.presentation.skills.SkillsExpandableFragment
import com.witcher.thewitcherrpg.feature_character_sheet.presentation.stats.StatsFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var toggle: ActionBarDrawerToggle
    lateinit var binding: ActivityMainBinding
    private var characterId: Int = -1
    private var saveAvailableMenuItem: MenuItem? = null
    private val mainCharacterViewModel: MainCharacterViewModel by viewModels()
    private var isDarkMode: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        characterId = intent.getIntExtra("CHARACTER_ID", -1) //Get character id

        try {
            mainCharacterViewModel.getCharacter(characterId) //Initialize viewModel with character data
        } catch (ex: KotlinNullPointerException) {
            Toast.makeText(this, "Unexpected error has occurred", Toast.LENGTH_SHORT).show()
        }

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
            when (it.itemId) {
                R.id.Character -> {
                    fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView3, CharFragment(), "CharacterInfo")
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit()
                }
                R.id.Skills -> {
                    fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView3, SkillsExpandableFragment(), "Skills")
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit()
                }
                R.id.Stats -> {
                    fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView3, StatsFragment(), "Stats")
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit()
                }
                R.id.ProfessionSkillTree -> {
                    fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView3, ProfessionSkillTree(), "SkillTree")
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit()
                }
                R.id.Magic -> {
                    fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView3, MagicParentFragment(), "Magic")
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit()
                }
                R.id.Equipment -> {
                    fragmentManager.beginTransaction().replace(
                        R.id.fragmentContainerView3,
                        EquipmentParentFragment(),
                        "Equipment"
                    )
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit()
                }
                R.id.Campaign_Notes -> {
                    fragmentManager.beginTransaction().replace(
                        R.id.fragmentContainerView3,
                        CampaignNotesFragment(),
                        "Campaign_Notes"
                    )
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit()
                }
            }
            drawerLayout.closeDrawer(binding.navView)
            true
        }

        val navHeaderBinding: NavHeaderBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.nav_header, binding.navView, true)
        navHeaderBinding.mainViewModel = mainCharacterViewModel
        navHeaderBinding.lifecycleOwner = this



        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Repeat when the lifecycle is STARTED, cancel when PAUSED
                launch {
                    mainCharacterViewModel.addState.collectLatest {
                        if (it.success) {
                            Toast.makeText(
                                this@MainActivity,
                                "Saved Successfully!",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }
                }
                launch {
                    mainCharacterViewModel.image.collect { newImage ->
                        if (loadImageFromStorage(newImage, navHeaderBinding.imageView))
                            (navHeaderBinding.imageView.layoutParams as ViewGroup.MarginLayoutParams).setMargins(
                                0,
                                0,
                                0,
                                0
                            )
                    }
                }
                launch {
                    mainCharacterViewModel.isDarkModeEnabled.collect { darkModeIsEnabled ->
                        if (darkModeIsEnabled) {
                            navHeaderBinding.lottieAnimationView.setAnimation("border_animation.json")
                        } else {
                            navHeaderBinding.lottieAnimationView.setAnimation("border_animation_blue.json")
                        }
                        isDarkMode = darkModeIsEnabled
                    }
                }
            }
        }
        lifecycleScope.launch {
            launch {
                mainCharacterViewModel._saveAvailable.observeForever {
                    saveAvailableMenuItem?.isVisible = it
                }
            }
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.menu_delete) {
            deleteCharacter()
        }
        if (item.itemId == R.id.menu_save) {
            saveCharacter()
        }
        if (item.itemId == R.id.menu_share) {
            val file = mainCharacterViewModel.getCharacterFile()
            if (file != null && file.exists()) {
                val intentShare = Intent(Intent.ACTION_SEND)
                intentShare.type = "application/cha"
                intentShare.putExtra(
                    Intent.EXTRA_STREAM,
                    FileProvider.getUriForFile(this, "com.witcher.thewitcherrpg", file)
                )

                startActivity(Intent.createChooser(intentShare, "Share character..."))
            }
        }
        if (item.itemId == R.id.menu_long_rest) {
            mainCharacterViewModel.onRest(longRest = true)
            Snackbar.make(
                binding.root, "${mainCharacterViewModel.name.value} has rested.",
                Snackbar.LENGTH_SHORT
            ).show()
        }
        if (item.itemId == R.id.menu_short_rest) {
            mainCharacterViewModel.onRest(longRest = false)
            Snackbar.make(
                binding.root, "${mainCharacterViewModel.name.value} has recovered.",
                Snackbar.LENGTH_SHORT
            ).show()
        }
        if (item.itemId == R.id.menu_edit_character) {
            val editFragment = CharCreationFirstFrag.newInstance(inEditMode = true)
            val fragmentManager = supportFragmentManager
            fragmentManager.beginTransaction().replace(R.id.fragmentContainerView3, editFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit()
        }
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteCharacter() {
        val builder = AlertDialog.Builder(this)
        builder.setPositiveButton("Yes") { _, _ ->
            mainCharacterViewModel.deleteCharacter(characterId)

            //Deletes the character image associated with this character
            val path = mainCharacterViewModel.image.value.toString()
            val f = File(path, mainCharacterViewModel.id.value.toString() + ".jpeg")
            f.delete()

            Toast.makeText(this, "Character Deleted", Toast.LENGTH_SHORT).show()
            finish()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete character?")
        builder.create().show()
    }

    private fun saveCharacter() {
        mainCharacterViewModel.saveCharacter()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.delete_save_menu, menu)

        saveAvailableMenuItem = menu.findItem(R.id.save_available)
        saveAvailableMenuItem!!.isVisible = mainCharacterViewModel._saveAvailable.value!!
        val saveAvailableView = (saveAvailableMenuItem as MenuItem).actionView

        saveAvailableView?.findViewById<LottieAnimationView?>(R.id.pulse_animation)
            ?.addValueCallback(
                KeyPath("**"),
                LottieProperty.COLOR_FILTER
            ) {
                PorterDuffColorFilter(
                    MaterialColors.getColor(this, R.attr.colorPrimary, Color.BLACK),
                    PorterDuff.Mode.SRC_ATOP
                )
            }


        saveAvailableView?.setOnClickListener {
            mainCharacterViewModel.saveCharacter()
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onBackPressed() {
        //Check if there is a callback set by a fragment then don't close activity
        if (this.onBackPressedDispatcher.hasEnabledCallbacks()) {
            super.onBackPressed()
            return
        }

        //If no callbacks are set by fragments, ask user if they would like to save character or cancel
        if (mainCharacterViewModel.checkIfDataChanged() || mainCharacterViewModel._saveAvailable.value == true) {
            val builder = AlertDialog.Builder(this)
            builder.setPositiveButton("Yes") { _, _ ->
                saveCharacter()
                finish()
            }
            builder.setNegativeButton("No") { _, _ -> finish() }

            builder.setNeutralButton("Cancel") { _, _ -> }

            builder.setTitle("Save changes to character?")
            builder.setMessage("All unsaved changes will be lost.")
            builder.create().show()
        } else finish()
    }

    private fun loadImageFromStorage(path: String, img: ImageView): Boolean {
        return try {
            val f = File(path)
            val b = BitmapFactory.decodeStream(FileInputStream(f))
            img.setImageBitmap(b)
            true
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mainCharacterViewModel._saveAvailable.removeObservers(this)
    }

    fun showEditStatDialog(label: String, currentValue: String, onPlus: (Int) -> Unit, onMinus: (Int) -> Unit) {
        binding.dialogCompose.setContent {
            var value by remember {
                mutableStateOf("")
            }

            CustomEditStatDialog(
                isDarkMode = isDarkMode,
                dialogState = true,
                label = label,
                currentValue = currentValue,
                editValue = value,
                updateValue = {
                    value = it
                },
                onPlus = {
                    onPlus(it)
                    binding.dialogCompose.setContent {}
                },
                onMinus = {
                    onMinus(it)
                    binding.dialogCompose.setContent {}
                },
                onDismissRequest = {
                    mainCharacterViewModel._editStatMode.value = false
                    binding.dialogCompose.setContent {}
                }
            )
        }
    }

}
