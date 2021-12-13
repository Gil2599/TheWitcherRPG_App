package com.example.thewitcherrpg.about_section

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import com.example.thewitcherrpg.databinding.ActivityAboutBinding
import com.example.thewitcherrpg.databinding.ActivityMainBinding

class AboutActivity : AppCompatActivity() {

    lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textViewAboutInfo.typeface = Typeface.DEFAULT
        binding.textViewDisclaimerInfo.typeface = Typeface.DEFAULT

        binding.textViewFreepik.movementMethod = LinkMovementMethod.getInstance()
        binding.textViewSmashIcons.movementMethod = LinkMovementMethod.getInstance()
        binding.textViewBookLink.movementMethod = LinkMovementMethod.getInstance()
    }
}