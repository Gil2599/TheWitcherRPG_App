package com.witcher.thewitcherrpg.feature_custom_attributes.presentation

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.witcher.thewitcherrpg.R

class CustomMagicComposableFragment {

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Preview
    @Composable
    fun SetCustomMagicContent() {
        val activity = (LocalContext.current as? Activity)
        var typeSelection by remember {
            mutableStateOf(MagicType.SPELL)
        }
        var levelSelection by remember {
            mutableStateOf(MagicLevel.NOVICE)
        }

        Scaffold(topBar = {
            TopAppBar(
                title = {
                    Text(text = "Custom Magic")
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
            Column(
                modifier = Modifier
                    .padding(15.dp)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceEvenly
            )
            {
                CustomTitle(title = "Custom Magic")
                CustomTextFieldName()
                DropDownMenuType(
                    updateSelection = { newSelection ->
                        typeSelection = newSelection
                    }
                )
                DropDownMenuLevel(
                    selection = typeSelection,
                    updateSelection = { newSelection ->
                        levelSelection = newSelection
                    })
                STACostSliderCard()
            }
        }
    }
}

@Composable
fun CustomTitle(title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(20.dp)
            .padding(horizontal = 15.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Image(
            painterResource(id = R.drawable.ic_title_sword),
            contentDescription = "left sword",
            modifier = Modifier
                .rotate(180F)
                .weight(1F),
            contentScale = ContentScale.FillBounds,
            colorFilter = ColorFilter.tint(color = MaterialTheme.colors.primary)
        )
        Text(
            text = title,
            modifier = Modifier.padding(horizontal = 15.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.onPrimary
        )
        Image(
            painterResource(id = R.drawable.ic_title_sword),
            contentDescription = "right sword",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.weight(1F),
            colorFilter = ColorFilter.tint(color = MaterialTheme.colors.primary)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CustomTextFieldName() {

    val focusManager: FocusManager = LocalFocusManager.current
    var text by remember {
        mutableStateOf("")
    }
    val focusRequester = remember {
        FocusRequester()
    }
    OutlinedTextField(
        textStyle = LocalTextStyle.current.copy(baselineShift = BaselineShift(-0.3F)),
        value = text,
        onValueChange = { newText ->
            text = newText
        },
        label = { Text(text = "Magic Name", Modifier.padding(0.dp, 4.dp, 0.dp, 0.dp)) },
        leadingIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Person Icon"
                )
            }
        },
        trailingIcon = {
            IconButton(onClick = {
                focusManager.clearFocus()
            }) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Person Icon"
                )
            }
        },
        modifier = Modifier
            .focusRequester(focusRequester)
            .fillMaxWidth(),
        maxLines = 2
    )

}

//@Preview(showBackground = true)
@Composable
fun DropDownMenuType(
    updateSelection: (MagicType) -> Unit,
    modifier: Modifier = Modifier
) {

    var expanded by remember { mutableStateOf(false) }
    val suggestions = listOf("Spell", "Invocation", "Ritual", "Hex", "Sign")
    var selectedText by remember { mutableStateOf("") }
    val focusManager: FocusManager = LocalFocusManager.current

    val focusRequester = remember {
        FocusRequester()
    }
    var textfieldSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else {
        focusManager.clearFocus()
        Icons.Filled.KeyboardArrowDown
    }

    Column(
        modifier = modifier.then(Modifier
            .clickable { focusManager.clearFocus(true) })
    ) {
        OutlinedTextField(
            textStyle = LocalTextStyle.current.copy(baselineShift = BaselineShift(-0.3F)),
            value = selectedText,
            onValueChange = { selectedText = it },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    //This value is used to assign to the DropDown the same width
                    textfieldSize = coordinates.size.toSize()
                }
                .focusRequester(focusRequester)
                .onFocusChanged { focusState ->
                    when {
                        focusState.isFocused -> expanded = true
                        focusState.hasFocus -> expanded = true
                    }
                },
            label = { Text("Magic Type", Modifier.padding(0.dp, 4.dp, 0.dp, 0.dp)) },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_magic_icon),
                    contentDescription = null
                )
            },
            trailingIcon = {
                Icon(icon, "contentDescription",
                    Modifier.clickable { expanded = !expanded })
            },
            readOnly = true
        )
        DropdownMenu(
            expanded = (expanded),
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { textfieldSize.width.toDp() })
        ) {
            suggestions.forEach { label ->
                DropdownMenuItem(onClick = {
                    selectedText = label
                    expanded = false
                    focusManager.clearFocus()
                    when (selectedText) {
                        "Spell" -> updateSelection(MagicType.SPELL)
                        "Invocation" -> updateSelection(MagicType.INVOCATION)
                        "Ritual" -> updateSelection(MagicType.RITUAL)
                        "Hex" -> updateSelection(MagicType.HEX)
                        "Sign" -> updateSelection(MagicType.SIGN)
                    }
                }) {
                    Text(text = label)
                }
            }
        }
    }
}

@Composable
fun DropDownMenuLevel(
    selection: MagicType, updateSelection: (MagicLevel) -> Unit,
    modifier: Modifier = Modifier
) {

    var expanded by remember { mutableStateOf(false) }
    val suggestionsSpells = listOf("Novice", "Journeyman", "Master")
    val suggestionsInvocations = listOf(
        "Novice Druid",
        "Journeyman Druid",
        "Master Druid",
        "Novice Preacher",
        "Journeyman Preacher",
        "Master Preacher",
        "Arch Priest"
    )
    val suggestionsSigns = listOf("Basic", "Alternate")
    var selectedText by remember { mutableStateOf("") }
    val focusManager: FocusManager = LocalFocusManager.current

    val focusRequester = remember {
        FocusRequester()
    }
    var textfieldSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else {
        focusManager.clearFocus()
        Icons.Filled.KeyboardArrowDown
    }

    Column(
        modifier = modifier.then(Modifier
            .clickable { focusManager.clearFocus(true) })
    ) {
        OutlinedTextField(
            textStyle = LocalTextStyle.current.copy(baselineShift = BaselineShift(-0.4F)),
            value =
            when (selection) {
                MagicType.SPELL, MagicType.RITUAL -> {
                    if (!suggestionsSpells.contains(selectedText)) suggestionsSpells[0] else selectedText
                }
                MagicType.INVOCATION -> {
                    if (!suggestionsInvocations.contains(selectedText)) suggestionsInvocations[0] else selectedText
                }
                MagicType.SIGN -> {
                    if (!suggestionsSigns.contains(selectedText)) suggestionsSigns[0] else selectedText
                }
                else -> {
                    " "
                }
            },
            onValueChange = { selectedText = it },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    //This value is used to assign to the DropDown the same width
                    textfieldSize = coordinates.size.toSize()
                }
                .focusRequester(focusRequester)
                .onFocusChanged { focusState ->
                    when {
                        focusState.isFocused -> expanded = true
                        focusState.hasFocus -> expanded = true
                    }
                },
            label = { Text("Magic Level", Modifier.padding(0.dp, 4.dp, 0.dp, 0.dp)) },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_magic_icon),
                    contentDescription = null
                )
            },
            trailingIcon = {
                Icon(icon, "contentDescription",
                    Modifier.clickable { expanded = !expanded })
            },
            readOnly = true
        )
        DropdownMenu(
            expanded = (expanded),
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { textfieldSize.width.toDp() })
        ) {
            when (selection) {
                MagicType.SPELL, MagicType.RITUAL -> {
                    suggestionsSpells.forEach { label ->
                        DropdownMenuItem(onClick = {
                            selectedText = label
                            expanded = false
                            focusManager.clearFocus()
                            when (selectedText) {
                                "Novice" -> updateSelection(MagicLevel.NOVICE)
                                "Journeyman" -> updateSelection(MagicLevel.JOURNEYMAN)
                                "Master" -> updateSelection(MagicLevel.MASTER)
                            }
                        }) {
                            Text(text = label)
                        }
                    }
                }
                MagicType.INVOCATION -> {
                    suggestionsInvocations.forEach { label ->
                        DropdownMenuItem(onClick = {
                            selectedText = label
                            expanded = false
                            focusManager.clearFocus()
                            when (selectedText) {

                            }
                        }) {
                            Text(text = label)
                        }
                    }
                }
                MagicType.SIGN -> {
                    suggestionsSigns.forEach { label ->
                        DropdownMenuItem(onClick = {
                            selectedText = label
                            expanded = false
                            focusManager.clearFocus()
                            when (selectedText) {

                            }
                        }) {
                            Text(text = label)
                        }
                    }
                }
                MagicType.HEX -> {

                }
            }
        }
    }

}

@Composable
fun STACostSliderCard() {
    var mySelection by rememberSaveable { mutableStateOf(0F) }

    Column {
        Text(text = "Stamina Cost: " + mySelection.toInt().toString())
        Slider(
            modifier = Modifier.fillMaxWidth(),
            value = mySelection,
            onValueChange = { mySelection = it },
            steps = 20,
            valueRange = 0F..30F
        )
    }
}

enum class MagicType {
    SPELL,
    INVOCATION,
    RITUAL,
    HEX,
    SIGN
}

enum class MagicLevel {
    NOVICE,
    JOURNEYMAN,
    MASTER,
    NOVICE_DRUID,
    JOURNEYMAN_DRUID,
    MASTER_DRUID,
    NOVICE_PREACHER,
    JOURNEYMAN_PREACHER,
    MASTER_PREACHER,
    ARCH_PRIEST
}