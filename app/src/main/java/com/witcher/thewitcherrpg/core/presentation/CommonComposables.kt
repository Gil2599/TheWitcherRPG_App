package com.witcher.thewitcherrpg.core.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.witcher.thewitcherrpg.feature_custom_attributes.presentation.theme.TheWitcherRPGTheme
import kotlin.math.roundToInt

class CommonComposables {

    @Preview
    @Composable
    fun testComposable() {
        var test by remember {
            mutableStateOf("")
        }

        CustomEditStatDialog(
            isDarkMode = true,
            dialogState = true,
            label = "Current HP",
            currentValue = "45",
            onDismissRequest = {},
            updateValue = {
                test = it
            },
            editValue = test,
            onMinus = {

            },
            onPlus = {

            }
        )
    }
}


@Composable
fun CustomEditStatDialog(
    isDarkMode: Boolean,
    dialogState: Boolean,
    label: String,
    currentValue: String,
    editValue: String,
    updateValue: (String) -> Unit,
    onPlus: (Int) -> Unit,
    onMinus: (Int) -> Unit,
    onDismissRequest: (dialogState: Boolean) -> Unit
) {
    TheWitcherRPGTheme(isDarkMode) {
        if (dialogState) {
            AlertDialog(
                modifier = Modifier.border(1.dp, MaterialTheme.colors.primary, shape = RoundedCornerShape(12.dp)),
                onDismissRequest = {
                    onDismissRequest(dialogState)
                },
                title = null,
                text = null,
                buttons = {
                    Column(
                        Modifier.padding(all = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = label,
                            fontSize = 20.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Text(
                            text = currentValue,
                            fontSize = 30.sp
                        )

                        Spacer(modifier = Modifier.height(36.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Button(
                                Modifier
                                    .weight(1f)
                                    .height(50.dp),
                                label = "-",
                                onClick = {
                                    if (editValue.isNotBlank()) {
                                        onMinus(editValue.toFloat().roundToInt())
                                    }
                                }
                            )

                            CustomTextFieldStat(
                                modifier = Modifier.weight(0.8f),
                                value = editValue,
                                updateValue = updateValue,
                            )

                            Button(
                                Modifier
                                    .weight(1f)
                                    .height(50.dp),
                                label = "+",
                                onClick = {
                                    if (editValue.isNotBlank()) {
                                        onPlus(editValue.toFloat().roundToInt())
                                    }
                                }
                            )
                        }
                    }
                },
                properties = DialogProperties(
                    dismissOnBackPress = true,
                    dismissOnClickOutside = true
                ),
                shape = RoundedCornerShape(12.dp)
            )
        }
    }
}

@Composable
fun Button(
    modifier: Modifier = Modifier,
    label: String = "",
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        border = BorderStroke(1.dp, MaterialTheme.colors.primary),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Transparent),
        shape = RoundedCornerShape(15.dp)
    ) {
        Text(text = label, color = MaterialTheme.colors.onPrimary, fontSize = 16.sp)
    }
}

@Composable
fun CustomTextFieldStat(
    modifier: Modifier = Modifier,
    value: String,
    updateValue: (String) -> Unit,
    inputType: KeyboardType = KeyboardType.Number,
    maxCharacters: Int = 4
) {

    val focusManager: FocusManager = LocalFocusManager.current
    val focusRequester = remember {
        FocusRequester()
    }
    OutlinedTextField(
        textStyle = LocalTextStyle.current.copy(baselineShift = BaselineShift(-0.3F), textAlign = TextAlign.Center),
        value = value,
        onValueChange = { newText ->
            if (newText.length <= maxCharacters) {
                updateValue(newText)
            }
        },
        modifier = modifier,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(keyboardType = inputType, imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = { focusManager.clearFocus() }
        ),
        singleLine = true
    )
}
