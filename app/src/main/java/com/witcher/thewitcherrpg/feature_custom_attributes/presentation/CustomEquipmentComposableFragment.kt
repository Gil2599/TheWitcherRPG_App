package com.witcher.thewitcherrpg.feature_custom_attributes.presentation

import android.annotation.SuppressLint
import android.app.Activity
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.witcher.thewitcherrpg.R
import com.witcher.thewitcherrpg.core.Resource
import com.witcher.thewitcherrpg.core.presentation.Button
import com.witcher.thewitcherrpg.feature_character_sheet.domain.item_types.WeaponTypes

class CustomEquipmentComposableFragment {

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Preview
    @Composable
    fun SetCustomEquipmentContent() {
        val activity = (LocalContext.current as? Activity)
        val customAttributeViewModel: CustomAttributeViewModel = viewModel()

        var equipmentName by remember {
            mutableStateOf("")
        }
        var equipmentType by remember {
            mutableStateOf(EquipmentType.HEAD)
        }
        var levelSelection by remember {
            mutableStateOf("")
        }
        var availability by remember {
            mutableStateOf(Availability.EVERYWHERE)
        }
        var stoppingPower by remember {
            mutableStateOf(0F)
        }
        var armorEnhancement by remember {
            mutableStateOf(0F)
        }
        var encumbranceValue by remember {
            mutableStateOf(0F)
        }
        var weight by remember {
            mutableStateOf("")
        }
        var range by remember {
            mutableStateOf("")
        }
        var damage by remember {
            mutableStateOf("")
        }
        var cost by remember {
            mutableStateOf("")
        }
        var effect by remember {
            mutableStateOf("")
        }
        var twoHanded by remember {
            mutableStateOf(false)
        }
        var isRelic by remember {
            mutableStateOf(false)
        }
        var bludgeoning by remember {
            mutableStateOf(false)
        }
        var piercing by remember {
            mutableStateOf(false)
        }
        var slashing by remember {
            mutableStateOf(false)
        }
        var elemental by remember {
            mutableStateOf(false)
        }
        var weaponAccuracy by remember {
            mutableStateOf(0F)
        }
        var weaponReliability by remember {
            mutableStateOf(0F)
        }
        var enhancements by remember {
            mutableStateOf(0F)
        }
        var focus by remember {
            mutableStateOf(0F)
        }
        var concealment by remember {
            mutableStateOf("")
        }
        val scrollState = rememberScrollState()

        customAttributeViewModel.addEquipmentState.observe(LocalLifecycleOwner.current) {
            if (customAttributeViewModel.addEquipmentState.value is Resource.Success) {
                Toast.makeText(activity, "Equipment Added Successfully!", Toast.LENGTH_SHORT).show()
                activity?.finish()
            }
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
                        .padding(top = 15.dp, end = 15.dp, start = 15.dp)
                        .verticalScroll(scrollState)
                        .weight(1f, fill = true),
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    CustomTextField(
                        value = equipmentName,
                        updateValue = {
                            equipmentName = it
                        },
                        label = "Equipment Name",
                        maxLines = 2,
                        leadingIconDrawable = R.drawable.ic_skills_icon
                    )
                    CustomDropDownMenu(
                        updateSelection = { newSelection ->
                            equipmentType =
                                customAttributeViewModel.getEquipmentTypeFromSelection(newSelection)!!
                        },
                        options = customAttributeViewModel.optionsEquipmentTypeMap.keys.toList(),
                        label = "Equipment Type",
                        leadingIconDrawable = R.drawable.ic_skills_icon
                    )
                    AnimatedVisibility(visible = equipmentType != EquipmentType.AMULET) {
                        CustomDropDownMenu(
                            selection = levelSelection,
                            updateSelection = { newSelection ->
                                levelSelection = newSelection
                            },
                            options = customAttributeViewModel.getEquipmentLevelOptions(
                                equipmentType
                            ),
                            label = if (equipmentType == EquipmentType.WEAPON) "Weapon Type" else "Equipment Level",
                            leadingIconDrawable = if (equipmentType == EquipmentType.WEAPON) R.drawable.ic_staff else R.drawable.ic_bolt
                        )
                    }
                    CustomDropDownMenu(
                        selection = availability.toString,
                        updateSelection = { newSelection ->
                            availability =
                                customAttributeViewModel.getAvailabilityFromSelection(newSelection)
                        },
                        options = Availability.values().map { it.toString },
                        label = "Availability",
                        leadingIconDrawable = R.drawable.ic_backpack_icon
                    )
                    AnimatedVisibility(visible = equipmentType == EquipmentType.WEAPON) {
                        CustomTextField(
                            value = damage,
                            updateValue = {
                                damage = it
                            },
                            label = "Damage",
                            leadingIconDrawable = R.drawable.ic_axe,
                            inputType = KeyboardType.Text,
                            singleLine = true
                        )
                    }
                    AnimatedVisibility(visible = equipmentType == EquipmentType.WEAPON) {
                        DamageTypeRow(
                            bludgeoning = bludgeoning,
                            piercing = piercing,
                            slashing = slashing,
                            elemental = elemental,
                            updateBludgeoning = {
                                bludgeoning = it
                            },
                            updatePiercing = {
                                piercing = it
                            },
                            updateSlashing = {
                                slashing = it
                            },
                            updateElemental = {
                                elemental = it
                            }
                        )
                    }
                    AnimatedVisibility(visible = equipmentType == EquipmentType.WEAPON) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = "Two Handed", modifier = Modifier.padding(top = 16.dp))
                            Checkbox(
                                checked = twoHanded,
                                onCheckedChange = {
                                    twoHanded = it
                                },
                                colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colors.primary)
                            )
                        }
                    }
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Relic", modifier = Modifier.padding(top = 16.dp), color = colorResource(
                            id = R.color.relic
                        ))
                        Checkbox(
                            checked = isRelic,
                            onCheckedChange = {
                                isRelic = it
                            },
                            colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colors.primary)
                        )
                    }
                    AnimatedVisibility(visible = equipmentType != EquipmentType.WEAPON && equipmentType != EquipmentType.AMULET) {
                        StandardSliderCard(
                            mainValue = stoppingPower,
                            updateMainValue = { newCost -> stoppingPower = newCost },
                            initialText = "Stopping Power: ",
                            range = 0F..50F
                        )
                    }
                    AnimatedVisibility(visible = equipmentType != EquipmentType.WEAPON && equipmentType != EquipmentType.AMULET) {
                        StandardSliderCard(
                            mainValue = armorEnhancement,
                            updateMainValue = { newCost -> armorEnhancement = newCost },
                            initialText = "Armor Enhancement: ",
                            range = 0F..5F
                        )
                    }
                    AnimatedVisibility(visible = equipmentType != EquipmentType.WEAPON && equipmentType != EquipmentType.AMULET) {
                        StandardSliderCard(
                            mainValue = encumbranceValue,
                            updateMainValue = { newCost -> encumbranceValue = newCost },
                            initialText = "Encumbrance Value: ",
                            range = 0F..5F
                        )
                    }
                    AnimatedVisibility(visible = equipmentType == EquipmentType.WEAPON) {
                        StandardSliderCard(
                            mainValue = weaponAccuracy,
                            updateMainValue = { newCost -> weaponAccuracy = newCost },
                            initialText = "Weapon Accuracy: ",
                            range = 0F..5F
                        )
                    }
                    AnimatedVisibility(visible = equipmentType == EquipmentType.WEAPON) {
                        StandardSliderCard(
                            mainValue = weaponReliability,
                            updateMainValue = { newCost -> weaponReliability = newCost },
                            initialText = "Reliability: ",
                            range = 0F..30F
                        )
                    }
                    AnimatedVisibility(visible = equipmentType == EquipmentType.WEAPON) {
                        StandardSliderCard(
                            mainValue = enhancements,
                            updateMainValue = { newCost -> enhancements = newCost },
                            initialText = "Enhancements: ",
                            range = 0F..5F
                        )
                    }
                    AnimatedVisibility(visible = equipmentType == EquipmentType.WEAPON || equipmentType == EquipmentType.AMULET) {
                        StandardSliderCard(
                            mainValue = focus,
                            updateMainValue = { newCost -> focus = newCost },
                            initialText = "Focus: ",
                            range = 0F..5F
                        )
                    }
                    AnimatedVisibility(visible = equipmentType == EquipmentType.WEAPON) {
                        CustomTextField(
                            value = range,
                            updateValue = {
                                range = it
                            },
                            label = "Range",
                            leadingIconDrawable = R.drawable.ic_bow,
                            inputType = KeyboardType.Text,
                            singleLine = true
                        )
                    }
                    AnimatedVisibility(visible = equipmentType == EquipmentType.WEAPON) {
                        CustomDropDownMenu(
                            selection = concealment,
                            updateSelection = { newSelection ->
                                concealment = newSelection
                            },
                            options = listOf("Tiny", "Small", "Large", "Can't Hide"),
                            label = "Concealment",
                            leadingIconDrawable = R.drawable.ic_dagger
                        )
                    }
                    CustomTextField(
                        value = weight,
                        updateValue = {
                            weight = if (it.isEmpty()) {
                                it
                            } else {
                                when (it.toDoubleOrNull()) {
                                    null -> weight
                                    else -> it
                                }
                            }
                        },
                        label = "Weight",
                        leadingIconDrawable = R.drawable.ic_weight,
                        inputType = KeyboardType.Decimal,
                        singleLine = true
                    )
                    CustomTextField(
                        value = cost,
                        updateValue = {
                            cost = if (it.isEmpty()) {
                                it
                            } else {
                                when (it.toDoubleOrNull()) {
                                    null -> cost
                                    else -> it
                                }
                            }
                        },
                        label = "Cost",
                        leadingIconDrawable = R.drawable.ic_money_bag,
                        inputType = KeyboardType.Number,
                        singleLine = true
                    )
                    CustomTextField(
                        value = effect,
                        updateValue = { newEffect ->
                            effect = newEffect
                        },
                        label = "Effect",
                        maxLines = 50,
                        leadingIconDrawable = R.drawable.ic_magic_wand
                    )

                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 15.dp, end = 15.dp, start = 15.dp, top = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        label = "Cancel",
                        onClick = {
                            activity?.finish()
                        }
                    )
                    Button(
                        label = "Save",
                        onClick = {
                            when (equipmentType) {
                                EquipmentType.WEAPON, EquipmentType.AMULET -> {
                                    if (
                                        !customAttributeViewModel.isWeaponValid(
                                            equipmentLevel = if (equipmentType == EquipmentType.WEAPON) levelSelection else WeaponTypes.AMULET.typeString,
                                            equipmentName = equipmentName,
                                            availability = availability,
                                            accuracy = weaponAccuracy.toInt(),
                                            reliability = weaponReliability.toInt(),
                                            focus = focus.toInt(),
                                            weight = weight,
                                            cost = cost,
                                            effect = effect,
                                            damage = damage,
                                            range = range,
                                            enhancements = enhancements.toInt(),
                                            concealment = concealment,
                                            bludgeoning,
                                            piercing,
                                            slashing,
                                            elemental,
                                            isTwoHanded = twoHanded,
                                            isRelic = isRelic
                                        )
                                    ) {
                                        Toast.makeText(activity, "Please fill out necessary fields", Toast.LENGTH_SHORT).show()
                                    }
                                }
                                EquipmentType.HEAD, EquipmentType.CHEST, EquipmentType.LEG, EquipmentType.ARMOR_SET, EquipmentType.SHIELD -> {
                                    if (
                                        !customAttributeViewModel.isEquipmentValid(
                                            equipmentType = equipmentType,
                                            equipmentLevel = levelSelection,
                                            equipmentName = equipmentName,
                                            availability = availability,
                                            stoppingPower = stoppingPower,
                                            weight = weight,
                                            cost = cost,
                                            effect = effect,
                                            enhancements = armorEnhancement,
                                            encumbrance = encumbranceValue,
                                            isRelic = isRelic
                                        )
                                    ) {
                                        Toast.makeText(activity, "Please fill out necessary fields", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun StandardSliderCard(
    mainValue: Float,
    updateMainValue: (Float) -> Unit,
    initialText: String,
    range: ClosedFloatingPointRange<Float>
) {

    Column {
        Text(
            text = initialText + mainValue.toInt().toString(),
            modifier = Modifier.padding(top = 16.dp)
        )
        Slider(
            modifier = Modifier.fillMaxWidth(),
            value = mainValue,
            onValueChange = { updateMainValue(it) },
            valueRange = range
        )
    }
}

@Composable
fun DamageTypeRow(
    bludgeoning: Boolean,
    piercing: Boolean,
    slashing: Boolean,
    elemental: Boolean,
    updateBludgeoning: (Boolean) -> Unit,
    updatePiercing: (Boolean) -> Unit,
    updateSlashing: (Boolean) -> Unit,
    updateElemental: (Boolean) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            Row {
                Text(text = "Bludgeoning", modifier = Modifier.padding(top = 16.dp))
                Checkbox(checked = bludgeoning, onCheckedChange = {
                    updateBludgeoning(it)
                }, colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colors.primary))
            }
            Row {
                Text(text = "Piercing", modifier = Modifier.padding(top = 16.dp))
                Checkbox(checked = piercing, onCheckedChange = {
                    updatePiercing(it)
                }, colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colors.primary))
            }
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            Row {
                Text(text = "Slashing", modifier = Modifier.padding(top = 16.dp))
                Checkbox(checked = slashing, onCheckedChange = {
                    updateSlashing(it)
                }, colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colors.primary))
            }
            Row {
                Text(text = "Elemental", modifier = Modifier.padding(top = 16.dp))
                Checkbox(checked = elemental, onCheckedChange = {
                    updateElemental(it)
                }, colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colors.primary))
            }
        }
    }
}

enum class EquipmentType {
    HEAD,
    CHEST,
    LEG,
    ARMOR_SET,
    WEAPON,
    SHIELD,
    AMULET
}

enum class EquipmentLevel(val levelString: String) {
    LIGHT("Light"),
    MEDIUM("Medium"),
    HEAVY("Heavy")
}

enum class Availability(val toString: String) {
    EVERYWHERE("Everywhere"),
    COMMON("Common"),
    POOR("Poor"),
    RARE("Rare")
}
