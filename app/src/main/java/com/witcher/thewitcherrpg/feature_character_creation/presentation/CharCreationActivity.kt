package com.witcher.thewitcherrpg.feature_character_creation.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.witcher.thewitcherrpg.core.presentation.CustomEditStatDialog
import com.witcher.thewitcherrpg.core.presentation.MainCharacterViewModel
import com.witcher.thewitcherrpg.databinding.ActivityCharCreationBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CharCreationActivity : AppCompatActivity() {

    private val mainCharacterViewModel: MainCharacterViewModel by viewModels()
    lateinit var binding: ActivityCharCreationBinding
    private var isDarkMode: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharCreationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainCharacterViewModel.setInCharCreation(true)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Repeat when the lifecycle is STARTED, cancel when PAUSED
                launch {
                    mainCharacterViewModel.addState.collectLatest {
                        if (it.success) {
                            Toast.makeText(this@CharCreationActivity,
                                "Character created successfully",
                                Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
                launch {
                    mainCharacterViewModel.isDarkModeEnabled.collect { darkModeIsEnabled ->
                        isDarkMode = darkModeIsEnabled
                    }
                }
            }
        }
    }

    fun onSaveFinal(){
        mainCharacterViewModel.addCharacter()
        finish()
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