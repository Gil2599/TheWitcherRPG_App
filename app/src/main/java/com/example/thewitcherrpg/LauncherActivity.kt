package com.example.thewitcherrpg

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thewitcherrpg.characterCreation.CharCreationActivity
import com.example.thewitcherrpg.data.CharacterViewModel
import com.example.thewitcherrpg.databinding.ActivityLauncherBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.jar.Manifest

class LauncherActivity : AppCompatActivity() {

    private lateinit var mCharViewModel: CharacterViewModel

    lateinit var recyclerView: RecyclerView
    lateinit var addButton: FloatingActionButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityLauncherBinding = ActivityLauncherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
        != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 111)
        }

        recyclerView = binding.recyclerView
        addButton = binding.addButton


        addButton.setOnClickListener(){

            val intent = Intent(this, CharCreationActivity::class.java)
            this.startActivity(intent)
        }

        val adapter = ListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        mCharViewModel = ViewModelProvider(this).get(CharacterViewModel::class.java)

        mCharViewModel.readAllData.observe(this, {character ->
            adapter.setData(character)
        })

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResult: IntArray
    ){
        super.onRequestPermissionsResult(requestCode, permissions, grantResult)
        if(requestCode == 111 && grantResult[0] == PackageManager.PERMISSION_GRANTED){

        }
    }
}