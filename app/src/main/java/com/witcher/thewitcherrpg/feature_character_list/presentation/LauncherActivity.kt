package com.witcher.thewitcherrpg.feature_character_list.presentation

import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.FloatRange
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.net.toFile
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.model.KeyPath
import com.witcher.thewitcherrpg.R
import com.witcher.thewitcherrpg.about_section.AboutActivity
import com.witcher.thewitcherrpg.databinding.ActivityLauncherBinding
import com.witcher.thewitcherrpg.databinding.CustomDialogHelpInfoBinding
import com.witcher.thewitcherrpg.feature_character_creation.presentation.CharCreationActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.witcher.thewitcherrpg.core.domain.model.Character
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.File

@AndroidEntryPoint
class LauncherActivity : AppCompatActivity() {
    lateinit var binding: ActivityLauncherBinding

    private lateinit var mCharListViewModel: CharacterListViewModel
    private var darkMode = false

    lateinit var recyclerView: RecyclerView
    private lateinit var addButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLauncherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.recyclerView
        addButton = binding.addButton

        addButton.setOnClickListener {
            mCharListViewModel.setInAddMode(!mCharListViewModel.inAddMode.value)
        }

        binding.overlayView.setOnClickListener {
            mCharListViewModel.setInAddMode(false)
        }

        binding.lottieAnimationCharacterBorder.setOnClickListener {
            goToCharCreation()
        }
        binding.textViewNewCharacter.setOnClickListener {
            goToCharCreation()
        }

        val adapter = ListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        mCharListViewModel = ViewModelProvider(this)[CharacterListViewModel::class.java]

        val uri = intent.data
        if (uri != null) {
            val inputStream = contentResolver.openInputStream(uri)
            if (inputStream != null) {
                val character: Character? = mCharListViewModel.getCharacterFromFile(inputStream)

                if (character != null){
                    addSharedCharacter(character)
                } else {
                    Toast.makeText(this, "Unexpected Error Occurred", Toast.LENGTH_SHORT).show()
                }
            }
            intent.data = null
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Repeat when the lifecycle is STARTED, cancel when PAUSED
                launch {
                    mCharListViewModel.characterList.collectLatest {
                        if (it.isEmpty()) {
                            binding.textViewNoCharacters.visibility = View.VISIBLE
                            binding.textViewTapAdd.visibility = View.VISIBLE
                        } else {
                            binding.textViewNoCharacters.visibility = View.GONE
                            binding.textViewTapAdd.visibility = View.GONE
                        }
                        adapter.setData(it)
                    }
                }
            }
        }

        lifecycleScope.launch {
            launch {
                mCharListViewModel.disclaimerMode.collect { disclaimerIsEnabled ->
                    if (disclaimerIsEnabled) {
                        showDialogDisclaimer()
                    }
                }
            }
            launch {
                mCharListViewModel.darkMode.collect { darkModeIsEnabled ->
                    darkMode = darkModeIsEnabled
                    if (darkModeIsEnabled) {
                        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_night_mode)
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

                        binding.lottieAnimationMagicBorder.setAnimation("border_animation.json")
                        binding.lottieAnimationCharacterBorder.setAnimation("border_animation.json")
                        binding.lottieAnimationEquipmentBorder.setAnimation("border_animation.json")
                    } else {
                        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_day_mode)
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

                        binding.lottieAnimationMagicBorder.setAnimation("border_animation_blue.json")
                        binding.lottieAnimationCharacterBorder.setAnimation("border_animation_blue.json")
                        binding.lottieAnimationEquipmentBorder.setAnimation("border_animation_blue.json")
                    }
                }
            }
            mCharListViewModel.inAddMode.collect { inAddMode ->
                inAddMode(inAddMode)
            }
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            android.R.id.home -> {
                mCharListViewModel.saveDarkMode(!darkMode)
                true
            }
            R.id.about -> {
                val intent = Intent(this, AboutActivity::class.java)
                this.startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.launcher_activity_app_bar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun showDialogDisclaimer() {
        val dialog = Dialog(this)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val bind: CustomDialogHelpInfoBinding = CustomDialogHelpInfoBinding.inflate(layoutInflater)
        dialog.setContentView(bind.root)

        bind.textViewInfo.text = resources.getString(R.string.disclaimer)
        bind.textViewInfo.typeface = Typeface.DEFAULT

        bind.customTitle.setTitle("Disclaimer")
        bind.customTitle.setTitleSize(18F)

        bind.okButton.setOnClickListener {
            if (bind.checkBox.visibility == View.VISIBLE && bind.checkBox.isChecked) {
                mCharListViewModel.saveDisclaimerMode(!bind.checkBox.isChecked)
            }
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun addSharedCharacter(character: Character) {
        val builder = AlertDialog.Builder(this)
        builder.setPositiveButton("Yes"){_, _ ->
            mCharListViewModel.addSharedCharacter(character)

            Toast.makeText(this, "Character Added!", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No"){_, _ -> }
        builder.setTitle("Add Character?")
        builder.setMessage("All character data except image will be added.")
        builder.create().show()
    }

    private fun inAddMode(mode: Boolean){
        if (mode){

            binding.lottieAnimationMagicBorder.alpha = 0F
            binding.lottieAnimationMagicBorder.animate()
                .withStartAction {
                    binding.lottieAnimationMagicBorder.visibility = View.VISIBLE
                }
                .alpha(1F).duration = 300
            binding.lottieAnimationMagic.alpha = 0F
            binding.lottieAnimationMagic.animate()
                .withStartAction {
                    binding.lottieAnimationMagic.visibility = View.VISIBLE
                }
                .alpha(1F).duration = 300

            binding.lottieAnimationMagic.playAnimation()
            binding.lottieAnimationMagicBorder.playAnimation()

            binding.lottieAnimationEquipmentBorder.alpha = 0F
            binding.lottieAnimationEquipmentBorder.animate()
                .withStartAction {
                    binding.lottieAnimationEquipmentBorder.visibility = View.VISIBLE
                }
                .alpha(1F).duration = 300
            binding.lottieAnimationEquipment.alpha = 0F
            binding.lottieAnimationEquipment.animate()
                .withStartAction {
                    binding.lottieAnimationEquipment.visibility = View.VISIBLE
                }
                .alpha(1F).duration = 300
            binding.lottieAnimationEquipment.playAnimation()
            binding.lottieAnimationEquipmentBorder.playAnimation()

            binding.lottieAnimationCharacterBorder.alpha = 0F
            binding.lottieAnimationCharacterBorder.animate()
                .withStartAction {
                    binding.lottieAnimationCharacterBorder.visibility = View.VISIBLE
                }
                .alpha(1F).duration = 300
            binding.lottieAnimationCharacter.alpha = 0F
            binding.lottieAnimationCharacter.animate()
                .withStartAction {
                    binding.lottieAnimationCharacter.visibility = View.VISIBLE
                }
                .alpha(1F).duration = 300
            binding.lottieAnimationCharacter.playAnimation()
            binding.lottieAnimationCharacterBorder.playAnimation()

            binding.overlayView.visibility = View.VISIBLE
            binding.overlayView.alpha = 0.8F
            binding.overlayView.bringToFront()

            binding.lottieAnimationMagic.bringToFront()
            binding.lottieAnimationMagicBorder.bringToFront()
            binding.lottieAnimationEquipment.bringToFront()
            binding.lottieAnimationEquipmentBorder.bringToFront()
            binding.lottieAnimationCharacter.bringToFront()
            binding.lottieAnimationCharacterBorder.bringToFront()

            binding.textViewNewCharacter.alpha = 0F
            binding.textViewNewCharacter.animate()
                .withStartAction {
                    binding.textViewNewCharacter.visibility = View.VISIBLE
                }
                .alpha(1F).duration = 300
            binding.textViewNewCharacter.bringToFront()
            binding.textViewNewMagic.alpha = 0F
            binding.textViewNewMagic.animate()
                .withStartAction {
                    binding.textViewNewMagic.visibility = View.VISIBLE
                }
                .alpha(1F).duration = 300
            binding.textViewNewMagic.bringToFront()
            binding.textViewNewEquipment.alpha = 0F
            binding.textViewNewEquipment.animate()
                .withStartAction {
                    binding.textViewNewEquipment.visibility = View.VISIBLE
                }
                .alpha(1F).duration = 300
            binding.textViewNewEquipment.bringToFront()
            binding.recyclerView.isEnabled = false

            binding.addButton.animate().rotation(135F)


        } else {

            binding.lottieAnimationMagicBorder.visibility = View.GONE
            binding.lottieAnimationMagic.visibility = View.GONE
            binding.lottieAnimationMagic.pauseAnimation()
            binding.lottieAnimationMagicBorder.pauseAnimation()

            binding.lottieAnimationEquipmentBorder.visibility = View.GONE
            binding.lottieAnimationEquipment.visibility = View.GONE
            binding.lottieAnimationEquipment.pauseAnimation()
            binding.lottieAnimationEquipmentBorder.pauseAnimation()

            binding.lottieAnimationCharacterBorder.visibility = View.GONE
            binding.lottieAnimationCharacter.visibility = View.GONE
            binding.lottieAnimationCharacter.pauseAnimation()
            binding.lottieAnimationCharacterBorder.pauseAnimation()

            binding.overlayView.visibility = View.GONE
            binding.recyclerView.isEnabled = true

            binding.textViewNewCharacter.visibility = View.GONE
            binding.textViewNewMagic.visibility = View.GONE
            binding.textViewNewEquipment.visibility = View.GONE

            binding.addButton.animate().rotation(0F).duration = 300
        }
    }

    private fun goToCharCreation(){
        val intent = Intent(this, CharCreationActivity::class.java)
        this.startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        mCharListViewModel.setInAddMode(false)
    }
}
