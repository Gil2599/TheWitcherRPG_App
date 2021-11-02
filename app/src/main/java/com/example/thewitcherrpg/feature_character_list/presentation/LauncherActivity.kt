package com.example.thewitcherrpg.feature_character_list.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thewitcherrpg.feature_character_creation.presentation.CharCreationActivity
import com.example.thewitcherrpg.databinding.ActivityLauncherBinding
import com.example.thewitcherrpg.core.domain.model.Character
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LauncherActivity : AppCompatActivity() {

    private lateinit var mCharListViewModel: CharacterListViewModel

    lateinit var recyclerView: RecyclerView
    private lateinit var addButton: FloatingActionButton

    private var characterList = ArrayList<Character>()

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
            mCharListViewModel.characterList.collectLatest {
                adapter.setData(characterList)
            }
        }

    }
}
