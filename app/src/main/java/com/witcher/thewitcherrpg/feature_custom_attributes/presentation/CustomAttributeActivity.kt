package com.witcher.thewitcherrpg.feature_custom_attributes.presentation

import android.graphics.drawable.AdaptiveIconDrawable
import android.graphics.drawable.Icon
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.witcher.thewitcherrpg.R
import com.witcher.thewitcherrpg.feature_character_list.presentation.CharacterListViewModel
import com.witcher.thewitcherrpg.feature_custom_attributes.presentation.theme.TheWitcherRPGTheme
import dagger.hilt.android.AndroidEntryPoint

private var isDarkMode = true

@AndroidEntryPoint
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
                    else CustomEquipmentComposableFragment().SetCustomEquipmentContent()
                }
            }
        }
    }
}

@Composable
fun CustomTitle(title: String) {
    Column {
        Spacer(
            modifier = Modifier
                .height(16.dp)
                .fillMaxWidth()
        )
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
}

@Composable
fun CustomDropDownMenu(
    modifier: Modifier = Modifier,
    selection: String = "",
    updateSelection: (String) -> Unit,
    options: List<String>,
    label: String = "",
    leadingIcon: ImageVector? = null,
    leadingIconDrawable: Int? = null,
) {

    var expanded by remember { mutableStateOf(false) }

    var selectedText by remember { mutableStateOf("") }
    val focusManager: FocusManager = LocalFocusManager.current

    val focusRequester = remember {
        FocusRequester()
    }
    var textfieldSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else {
        Icons.Filled.KeyboardArrowDown
    }

    Column(
        modifier = modifier.then(Modifier
            .clickable { focusManager.clearFocus(true) })
    ) {
        OutlinedTextField(
            textStyle = LocalTextStyle.current.copy(baselineShift = BaselineShift(-0.4F)),
            value = selection.ifBlank { selectedText },
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
            label = { Text(label, Modifier.padding(0.dp, 4.dp, 0.dp, 0.dp)) },
            leadingIcon = {
                if (leadingIcon != null) {
                    Icon(
                        leadingIcon,
                        contentDescription = null
                    )
                }
                if (leadingIconDrawable != null){
                    Icon(
                        painter = painterResource(id = leadingIconDrawable),
                        contentDescription = null
                    )
                }
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
            options.forEach { label ->
                DropdownMenuItem(onClick = {
                    selectedText = label
                    expanded = false
                    focusManager.clearFocus()
                    updateSelection(selectedText)
                }) {
                    Text(text = label)
                }
            }
        }
    }

}

@Composable
fun CustomTextField(
    value: String,
    updateValue: (String) -> Unit,
    label: String,
    maxLines: Int = 1,
    leadingIcon: ImageVector? = null,
    leadingIconDrawable: Int? = null,
    inputType: KeyboardType = KeyboardType.Text,
    singleLine: Boolean = false
) {

    val focusManager: FocusManager = LocalFocusManager.current
    val focusRequester = remember {
        FocusRequester()
    }
    OutlinedTextField(
        textStyle = LocalTextStyle.current.copy(baselineShift = BaselineShift(-0.3F)),
        value = value,
        onValueChange = { newText ->
            updateValue(newText)
        },
        label = { Text(text = label, Modifier.padding(0.dp, 4.dp, 0.dp, 0.dp)) },
        leadingIcon = {
            if (leadingIcon != null) {
                Icon(
                    leadingIcon,
                    contentDescription = null
                )
            }
            if (leadingIconDrawable != null){
                Icon(
                    painter = painterResource(id = leadingIconDrawable),
                    contentDescription = null
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
        maxLines = maxLines,
        keyboardOptions = KeyboardOptions(keyboardType = inputType, imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
          onDone = {focusManager.clearFocus()}
        ),
        singleLine = singleLine
    )
}
