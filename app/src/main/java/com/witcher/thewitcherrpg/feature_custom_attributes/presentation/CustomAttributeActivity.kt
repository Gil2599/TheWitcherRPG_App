package com.witcher.thewitcherrpg.feature_custom_attributes.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.witcher.thewitcherrpg.feature_custom_attributes.presentation.theme.TheWitcherRPGTheme

private var isDarkMode = true

class CustomAttributeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isDarkMode = intent.extras?.getBoolean("isDark") ?: true
        val isMagic = intent.extras?.getBoolean("isMagic") ?: true

        setContent {
            TheWitcherRPGTheme(isDarkMode) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.primaryVariant
                ) {
                    if (isMagic) CustomMagicComposableFragment().SetCustomMagicContent()
                    else {}
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!", color = MaterialTheme.colors.primary)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TheWitcherRPGTheme(isDarkMode) {
        Greeting("Android")
    }
}

