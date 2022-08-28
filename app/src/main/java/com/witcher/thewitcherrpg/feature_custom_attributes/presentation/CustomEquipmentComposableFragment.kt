package com.witcher.thewitcherrpg.feature_custom_attributes.presentation

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class CustomEquipmentComposableFragment {

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Preview
    @Composable
    fun SetCustomEquipmentContent(){
        val activity = (LocalContext.current as? Activity)
        val scrollState = rememberScrollState()

        var typeSelection by remember {
            mutableStateOf(MagicGeneralType.SPELL)
        }

        Scaffold(topBar = {
            TopAppBar(
                title = {
                    Text(text = "Add New Attribute")
                },
                backgroundColor = MaterialTheme.colors.primaryVariant,
                navigationIcon = {
                    IconButton(onClick = { activity?.finish() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Go Back"
                        )
                    }
                }
            )
        }
        ) {
            Column {
                CustomTitle(title = "Custom Equipment")

                Column(
                    modifier = Modifier
                        .padding(15.dp)
                        .fillMaxHeight()
                        .verticalScroll(scrollState),
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    //CustomTextFieldName()
//                    DropDownMenuType(
//                        isMagic = false,
//                        updateSelection = { newSelection ->
//                            typeSelection = newSelection
//                        }
//                    )

                    //STACostSliderCard()
                    //STACostSliderCard()
                }
            }
        }
    }
}