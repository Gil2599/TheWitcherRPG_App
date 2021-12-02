package com.example.thewitcherrpg.feature_character_list.presentation

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thewitcherrpg.R
import com.example.thewitcherrpg.databinding.ActivityLauncherBinding
import com.example.thewitcherrpg.databinding.CustomDialogHelpInfoBinding
import com.example.thewitcherrpg.feature_character_creation.presentation.CharCreationActivity
import com.example.thewitcherrpg.feature_character_creation.presentation.CharCreationFirstFrag
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LauncherActivity : AppCompatActivity() {

    private lateinit var mCharListViewModel: CharacterListViewModel

    lateinit var recyclerView: RecyclerView
    private lateinit var addButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityLauncherBinding = ActivityLauncherBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        recyclerView = binding.recyclerView
        addButton = binding.addButton

        addButton.setOnClickListener() {
            val intent = Intent(this, CharCreationActivity::class.java)
            this.startActivity(intent)
        }

        val adapter = ListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        mCharListViewModel = ViewModelProvider(this)[CharacterListViewModel::class.java]

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Repeat when the lifecycle is STARTED, cancel when PAUSED
                launch {
                    mCharListViewModel.characterList.collectLatest {
                        if (it.isEmpty()){
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
            mCharListViewModel.disclaimerMode.collect { disclaimerIsEnabled ->
                if (disclaimerIsEnabled) {
                    showDialogDisclaimer(true)
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.information) {
            showDialogDisclaimer(false)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.launcher_activity_app_bar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun showDialogDisclaimer(showCheckbox: Boolean) {
        val dialog = Dialog(this)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val bind: CustomDialogHelpInfoBinding = CustomDialogHelpInfoBinding.inflate(layoutInflater)
        dialog.setContentView(bind.root)

        bind.textViewInfo.text = resources.getString(R.string.disclaimer)
        bind.textViewInfo.typeface = Typeface.DEFAULT

        bind.customTitle.setTitle("Disclaimer")
        bind.customTitle.setTitleSize(18F)

        if (!showCheckbox) {
            bind.checkBox.visibility = View.GONE
        } else {
            bind.checkBox.visibility = View.VISIBLE
        }

        bind.okButton.setOnClickListener {
            if (bind.checkBox.visibility == View.VISIBLE && bind.checkBox.isChecked) {
                mCharListViewModel.saveDisclaimerMode(!bind.checkBox.isChecked)
            }
            dialog.dismiss()
        }
        dialog.show()
    }
}
