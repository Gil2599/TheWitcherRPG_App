package com.witcher.thewitcherrpg.feature_character_sheet.presentation.skills

import android.animation.LayoutTransition
import android.app.Dialog
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.model.KeyPath
import com.google.android.material.snackbar.Snackbar
import com.witcher.thewitcherrpg.R
import com.witcher.thewitcherrpg.core.Constants
import com.witcher.thewitcherrpg.core.Resource
import com.witcher.thewitcherrpg.core.presentation.MainCharacterViewModel
import com.witcher.thewitcherrpg.databinding.CustomDialogHelpInfoBinding
import com.witcher.thewitcherrpg.databinding.FragmentSkillsExpandableBinding
import com.witcher.thewitcherrpg.feature_character_creation.presentation.CharCreationActivity
import com.witcher.thewitcherrpg.feature_character_sheet.presentation.MainActivity
import kotlinx.coroutines.launch


class SkillsExpandableFragment : Fragment() {
    private var _binding: FragmentSkillsExpandableBinding? = null
    private val binding get() = _binding!!

    private val allEditTexts = arrayListOf<CustomSkillRow>()
    private val mainCharacterViewModel: MainCharacterViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_skills_expandable, container, false)
        val view = binding.root

        binding.lifecycleOwner = this
        binding.mainViewModel = mainCharacterViewModel

        onSkillsInit()

        if (!mainCharacterViewModel.inCharacterCreation.value) {
            setOnSkillClickListeners()
        }

        binding.etIP.setRawInputType(0)
        binding.etIP.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                showDialogIP()
            }
        }
        binding.etIP.setOnClickListener {
            showDialogIP()
        }

        binding.buttonHelp.setOnClickListener {
            showDialogDisclaimer()
        }

        binding.buttonPlus.setOnClickListener {
            increaseButton()
        }

        binding.buttonMinus.setOnClickListener {
            decreaseButton()
        }

        lifecycleScope.launch {
            mainCharacterViewModel.skillsInfoMode.collect { infoIsEnabled ->
                if (infoIsEnabled) {
                    showDialogDisclaimer()
                }
            }
        }
        lifecycleScope.launch {
            mainCharacterViewModel.isDarkModeEnabled.collect { isDarkMode ->
                setupLottieAnimations(isDarkMode)
            }
        }


        return view
    }

    private fun setupLottieAnimations(isDarkMode: Boolean){
        if (isDarkMode) {
            binding.lottieAnimationViewInt.addValueCallback(
                KeyPath("**"),
                LottieProperty.COLOR_FILTER
            ) {
                PorterDuffColorFilter(
                    resources.getColor(R.color.white),
                    PorterDuff.Mode.SRC_ATOP
                )
            }
            binding.lottieAnimationViewRef.addValueCallback(
                KeyPath("**"),
                LottieProperty.COLOR_FILTER
            ) {
                PorterDuffColorFilter(
                    resources.getColor(R.color.white),
                    PorterDuff.Mode.SRC_ATOP
                )
            }
            binding.lottieAnimationViewDex.addValueCallback(
                KeyPath("**"),
                LottieProperty.COLOR_FILTER
            ) {
                PorterDuffColorFilter(
                    resources.getColor(R.color.white),
                    PorterDuff.Mode.SRC_ATOP
                )
            }
            binding.lottieAnimationViewBody.addValueCallback(
                KeyPath("**"),
                LottieProperty.COLOR_FILTER
            ) {
                PorterDuffColorFilter(
                    resources.getColor(R.color.white),
                    PorterDuff.Mode.SRC_ATOP
                )
            }
            binding.lottieAnimationViewEmpathy.addValueCallback(
                KeyPath("**"),
                LottieProperty.COLOR_FILTER
            ) {
                PorterDuffColorFilter(
                    resources.getColor(R.color.white),
                    PorterDuff.Mode.SRC_ATOP
                )
            }
            binding.lottieAnimationViewCraft.addValueCallback(
                KeyPath("**"),
                LottieProperty.COLOR_FILTER
            ) {
                PorterDuffColorFilter(
                    resources.getColor(R.color.white),
                    PorterDuff.Mode.SRC_ATOP
                )
            }
            binding.lottieAnimationViewWill.addValueCallback(
                KeyPath("**"),
                LottieProperty.COLOR_FILTER
            ) {
                PorterDuffColorFilter(
                    resources.getColor(R.color.white),
                    PorterDuff.Mode.SRC_ATOP
                )
            }
        }
    }

    private fun setOnSkillClickListeners(){
        binding.constraintLayoutInt.setOnClickListener {
            if (binding.expandableIntView.visibility == View.GONE) {
                binding.lottieAnimationViewInt.setMinAndMaxFrame(10, 50)
                binding.lottieAnimationViewInt.speed = 3F
                binding.lottieAnimationViewInt.playAnimation()
                binding.expandableIntView.visibility = View.VISIBLE
                notifyTransition()
            } else {
                binding.lottieAnimationViewInt.setMinAndMaxFrame(50, 90)
                binding.lottieAnimationViewInt.speed = 2.5F
                binding.lottieAnimationViewInt.playAnimation()
                notifyTransition()
                binding.expandableIntView.visibility = View.GONE
            }
        }
        binding.constraintLayoutRef.setOnClickListener {
            if (binding.expandableRefView.visibility == View.GONE) {
                binding.lottieAnimationViewRef.setMinAndMaxFrame(10, 50)
                binding.lottieAnimationViewRef.speed = 3F
                binding.lottieAnimationViewRef.playAnimation()
                binding.expandableRefView.visibility = View.VISIBLE
                notifyTransition()
            } else {
                binding.lottieAnimationViewRef.setMinAndMaxFrame(50, 90)
                binding.lottieAnimationViewRef.speed = 2.5F
                binding.lottieAnimationViewRef.playAnimation()
                notifyTransition()
                binding.expandableRefView.visibility = View.GONE
            }
        }
        binding.constraintLayoutDex.setOnClickListener {
            if (binding.expandableDexView.visibility == View.GONE) {
                binding.lottieAnimationViewDex.setMinAndMaxFrame(10, 50)
                binding.lottieAnimationViewDex.speed = 3F
                binding.lottieAnimationViewDex.playAnimation()
                binding.expandableDexView.visibility = View.VISIBLE
                notifyTransition()
            } else {
                binding.lottieAnimationViewDex.setMinAndMaxFrame(50, 90)
                binding.lottieAnimationViewDex.speed = 2.5F
                binding.lottieAnimationViewDex.playAnimation()
                notifyTransition()
                binding.expandableDexView.visibility = View.GONE
            }
        }
        binding.constraintLayoutBody.setOnClickListener {
            if (binding.expandableBodyView.visibility == View.GONE) {
                binding.lottieAnimationViewBody.setMinAndMaxFrame(10, 50)
                binding.lottieAnimationViewBody.speed = 3F
                binding.lottieAnimationViewBody.playAnimation()
                binding.expandableBodyView.visibility = View.VISIBLE
                notifyTransition()
            } else {
                binding.lottieAnimationViewBody.setMinAndMaxFrame(50, 90)
                binding.lottieAnimationViewBody.speed = 2.5F
                binding.lottieAnimationViewBody.playAnimation()
                notifyTransition()
                binding.expandableBodyView.visibility = View.GONE
            }
        }
        binding.constraintLayoutEmpathy.setOnClickListener {
            if (binding.expandableEmpathyView.visibility == View.GONE) {
                binding.lottieAnimationViewEmpathy.setMinAndMaxFrame(10, 50)
                binding.lottieAnimationViewEmpathy.speed = 3F
                binding.lottieAnimationViewEmpathy.playAnimation()
                binding.expandableEmpathyView.visibility = View.VISIBLE
                notifyTransition()
            } else {
                binding.lottieAnimationViewEmpathy.setMinAndMaxFrame(50, 90)
                binding.lottieAnimationViewEmpathy.speed = 2.5F
                binding.lottieAnimationViewEmpathy.playAnimation()
                notifyTransition()
                notifyTransition()
                binding.expandableEmpathyView.visibility = View.GONE
            }
        }
        binding.constraintLayoutCraft.setOnClickListener {
            if (binding.expandableCraftView.visibility == View.GONE) {
                binding.lottieAnimationViewCraft.setMinAndMaxFrame(10, 50)
                binding.lottieAnimationViewCraft.speed = 3F
                binding.lottieAnimationViewCraft.playAnimation()
                binding.expandableCraftView.visibility = View.VISIBLE
                notifyTransition()
            } else {
                binding.lottieAnimationViewCraft.setMinAndMaxFrame(50, 90)
                binding.lottieAnimationViewCraft.speed = 2.5F
                binding.lottieAnimationViewCraft.playAnimation()
                notifyTransition()
                binding.expandableCraftView.visibility = View.GONE
            }
        }
        binding.constraintLayoutWill.setOnClickListener {
            if (binding.expandableWillView.visibility == View.GONE) {
                binding.lottieAnimationViewWill.setMinAndMaxFrame(10, 50)
                binding.lottieAnimationViewWill.speed = 3F
                binding.lottieAnimationViewWill.playAnimation()
                binding.expandableWillView.visibility = View.VISIBLE
                notifyTransition()
            } else {
                binding.lottieAnimationViewWill.setMinAndMaxFrame(50, 90)
                binding.lottieAnimationViewWill.speed = 2.5F
                binding.lottieAnimationViewWill.playAnimation()
                notifyTransition()
                binding.expandableWillView.visibility = View.GONE
            }
        }
    }

    private fun notifyTransition(){
        binding.constraintLayoutInt.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        binding.constraintLayoutRef.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        binding.constraintLayoutDex.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        binding.constraintLayoutBody.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        binding.constraintLayoutEmpathy.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        binding.constraintLayoutCraft.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        binding.constraintLayoutWill.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
    }

    private fun onSkillsInit() {

        val inCharCreation = mainCharacterViewModel.inCharacterCreation.value
        //Initialize all EditTexts
        if (inCharCreation) allEditTexts.add(binding.editText1)
        binding.editText1.setSkillText("Awareness:")

        if (inCharCreation) allEditTexts.add(binding.editText2)
        binding.editText2.setSkillText("Business:")

        if (inCharCreation) allEditTexts.add(binding.editText3)
        binding.editText3.setSkillText("Deduction:")

        if (inCharCreation) allEditTexts.add(binding.editText4)
        binding.editText4.setSkillText("Education:")

        if (inCharCreation) allEditTexts.add(binding.editText5)
        binding.editText5.setSkillText("Common Speech:")

        if (inCharCreation) allEditTexts.add(binding.editText6)
        binding.editText6.setSkillText("Elder Speech:")

        if (inCharCreation) allEditTexts.add(binding.editText7)
        binding.editText7.setSkillText("Dwarven:")

        if (inCharCreation) allEditTexts.add(binding.editText8)
        binding.editText8.setSkillText("Monster Lore:")

        if (inCharCreation) allEditTexts.add(binding.editText9)
        binding.editText9.setSkillText("Social Etiquette:")

        if (inCharCreation) allEditTexts.add(binding.editText10)
        binding.editText10.setSkillText("Streetwise:")

        if (inCharCreation) allEditTexts.add(binding.editText11)
        binding.editText11.setSkillText("Tactics:")

        if (inCharCreation) allEditTexts.add(binding.editText12)
        binding.editText12.setSkillText("Teaching:")

        if (inCharCreation) allEditTexts.add(binding.editText13)
        binding.editText13.setSkillText("Wilderness Survival:")

        if (inCharCreation) allEditTexts.add(binding.editText14)
        binding.editText14.setSkillText("Brawling:")

        if (inCharCreation) allEditTexts.add(binding.editText15)
        binding.editText15.setSkillText("Dodge/Escape:")

        if (inCharCreation) allEditTexts.add(binding.editText16)
        binding.editText16.setSkillText("Melee:")

        if (inCharCreation) allEditTexts.add(binding.editText17)
        binding.editText17.setSkillText("Riding:")

        if (inCharCreation) allEditTexts.add(binding.editText18)
        binding.editText18.setSkillText("Sailing:")

        if (inCharCreation) allEditTexts.add(binding.editText19)
        binding.editText19.setSkillText("Small Blades:")

        if (inCharCreation) allEditTexts.add(binding.editText20)
        binding.editText20.setSkillText("Staff/Spear:")

        if (inCharCreation) allEditTexts.add(binding.editText21)
        binding.editText21.setSkillText("Swordsmanship:")

        if (inCharCreation) allEditTexts.add(binding.editText22)
        binding.editText22.setSkillText("Archery:")

        if (inCharCreation) allEditTexts.add(binding.editText23)
        binding.editText23.setSkillText("Athletics:")

        if (inCharCreation) allEditTexts.add(binding.editText24)
        binding.editText24.setSkillText("Crossbow:")

        if (inCharCreation) allEditTexts.add(binding.editText25)
        binding.editText25.setSkillText("Sleight of Hand:")

        if (inCharCreation) allEditTexts.add(binding.editText26)
        binding.editText26.setSkillText("Stealth:")

        if (inCharCreation) allEditTexts.add(binding.editText27)
        binding.editText27.setSkillText("Physique:")

        if (inCharCreation) allEditTexts.add(binding.editText28)
        binding.editText28.setSkillText("Endurance:")

        if (inCharCreation) allEditTexts.add(binding.editText29)
        binding.editText29.setSkillText("Charisma:")

        if (inCharCreation) allEditTexts.add(binding.editText30)
        binding.editText30.setSkillText("Deceit:")

        if (inCharCreation) allEditTexts.add(binding.editText31)
        binding.editText31.setSkillText("Fine Arts:")

        if (inCharCreation) allEditTexts.add(binding.editText32)
        binding.editText32.setSkillText("Gambling:")

        if (inCharCreation) allEditTexts.add(binding.editText33)
        binding.editText33.setSkillText("Grooming and Style:")

        if (inCharCreation) allEditTexts.add(binding.editText34)
        binding.editText34.setSkillText("Human Perception:")

        if (inCharCreation) allEditTexts.add(binding.editText35)
        binding.editText35.setSkillText("Leadership:")

        if (inCharCreation) allEditTexts.add(binding.editText36)
        binding.editText36.setSkillText("Persuasion:")

        if (inCharCreation) allEditTexts.add(binding.editText37)
        binding.editText37.setSkillText("Performance:")

        if (inCharCreation) allEditTexts.add(binding.editText38)
        binding.editText38.setSkillText("Seduction:")

        if (inCharCreation) allEditTexts.add(binding.editText39)
        binding.editText39.setSkillText("Alchemy:")

        if (inCharCreation) allEditTexts.add(binding.editText40)
        binding.editText40.setSkillText("Crafting:")

        if (inCharCreation) allEditTexts.add(binding.editText41)
        binding.editText41.setSkillText("Disguise:")

        if (inCharCreation) allEditTexts.add(binding.editText42)
        binding.editText42.setSkillText("First Aid:")

        if (inCharCreation) allEditTexts.add(binding.editText43)
        binding.editText43.setSkillText("Forgery:")

        if (inCharCreation) allEditTexts.add(binding.editText44)
        binding.editText44.setSkillText("Pick Lock:")

        if (inCharCreation) allEditTexts.add(binding.editText45)
        binding.editText45.setSkillText("Trap Crafting:")

        if (inCharCreation) allEditTexts.add(binding.editText46)
        binding.editText46.setSkillText("Courage:")

        if (inCharCreation) allEditTexts.add(binding.editText47)
        binding.editText47.setSkillText("Hex Weaving:")

        if (inCharCreation) allEditTexts.add(binding.editText48)
        binding.editText48.setSkillText("Intimidation:")

        if (inCharCreation) allEditTexts.add(binding.editText49)
        binding.editText49.setSkillText("Spell Casting:")

        if (inCharCreation) allEditTexts.add(binding.editText50)
        binding.editText50.setSkillText("Resist Magic:")

        if (inCharCreation) allEditTexts.add(binding.editText51)
        binding.editText51.setSkillText("Resist Coercion:")

        if (inCharCreation) allEditTexts.add(binding.editText52)
        binding.editText52.setSkillText("Ritual Crafting:")

        if (inCharCreation) {
            val indexArray = mainCharacterViewModel.getProfessionIndices()
            for (index in 0 until allEditTexts.size) {
                if (index !in indexArray) allEditTexts[index].setInCharCreationMode(professionSkill = false)
                else allEditTexts[index].setInCharCreationMode(professionSkill = true)
            }
        }

        when (mainCharacterViewModel.profession.value){
            Constants.Professions.BARD -> {
                binding.etBusking.visibility = View.VISIBLE
                binding.etBusking.setSkillText("Busking:")
                binding.etBusking.setDefSkillColor()
            }
            Constants.Professions.CRIMINAL -> {
                binding.etPracticedParanoia.visibility = View.VISIBLE
                binding.etPracticedParanoia.setSkillText("Practiced Paranoia:")
                binding.etPracticedParanoia.setDefSkillColor()
            }
            Constants.Professions.CRAFTSMAN -> {
                binding.etPatchJob.visibility = View.VISIBLE
                binding.etPatchJob.setSkillText("Patch Job:")
                binding.etPatchJob.setDefSkillColor()
            }
            Constants.Professions.DOCTOR -> {
                binding.etHealingHands.visibility = View.VISIBLE
                binding.etHealingHands.setSkillText("Healing Hands:")
                binding.etHealingHands.setDefSkillColor()
            }
            Constants.Professions.MAGE -> {
                binding.etMagicTraining.visibility = View.VISIBLE
                binding.etMagicTraining.setSkillText("Magic Training:")
                binding.etMagicTraining.setDefSkillColor()
            }
            Constants.Professions.MAN_AT_ARMS -> {
                binding.etToughAsNails.visibility = View.VISIBLE
                binding.etToughAsNails.setSkillText("Tough As Nails:")
                binding.etToughAsNails.setDefSkillColor()
            }
            Constants.Professions.PRIEST -> {
                binding.etInitiateOfTheGods.visibility = View.VISIBLE
                binding.etInitiateOfTheGods.setSkillText("Initiate of The Gods:")
                binding.etInitiateOfTheGods.setDefSkillColor()
            }
            Constants.Professions.WITCHER -> {
                binding.etWitcherTraining.visibility = View.VISIBLE
                binding.etWitcherTraining.setSkillText("Witcher Training:")
                binding.etWitcherTraining.setDefSkillColor()
            }
            Constants.Professions.MERCHANT -> {
                binding.etWellTraveled.visibility = View.VISIBLE
                binding.etWellTraveled.setSkillText("Well Traveled:")
                binding.etWellTraveled.setDefSkillColor()
            }
            Constants.Professions.NOBLE -> {
                binding.etNotoriety.visibility = View.VISIBLE
                binding.etNotoriety.setSkillText("Notoriety:")
                binding.etNotoriety.setDefSkillColor()
            }
            Constants.Professions.PEASANT -> {
                binding.etIntolerance.visibility = View.VISIBLE
                binding.etIntolerance.setSkillText("Intolerance:")
                binding.etIntolerance.setDefSkillColor()
            }
        }
        if (inCharCreation) {
            binding.expandableIntView.visibility = View.VISIBLE
            binding.expandableRefView.visibility = View.VISIBLE
            binding.expandableDexView.visibility = View.VISIBLE
            binding.expandableBodyView.visibility = View.VISIBLE
            binding.expandableEmpathyView.visibility = View.VISIBLE
            binding.expandableCraftView.visibility = View.VISIBLE
            binding.expandableWillView.visibility = View.VISIBLE
        }
    }

    private fun increaseButton() {
        if (activity?.currentFocus is EditText) {
            val skill = (activity?.currentFocus as EditText).tag
            if (skill != null) {
                val row = getCustomRow(skill.toString())
                if (row.editModifier) mainCharacterViewModel.onSkillModifierChange(
                    skill.toString(),
                    true
                )
                else {
                    val result = mainCharacterViewModel.onSkillChange(
                        skill.toString(),
                        true)
                    if (result is Resource.Error){
                        Snackbar.make(
                            binding.root, result.message.toString(),
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
        mainCharacterViewModel.checkSaveAvailable()
    }

    private fun decreaseButton() {
        if (activity?.currentFocus is EditText) {
            val skill = (activity?.currentFocus as EditText).tag
            if (skill != null) {
                val row = getCustomRow(skill.toString())
                if (row.editModifier) mainCharacterViewModel.onSkillModifierChange(
                    skill.toString(),
                    false
                )
                else mainCharacterViewModel.onSkillChange(
                    skill.toString(),
                    false
                )
            }
        }
        mainCharacterViewModel.checkSaveAvailable()
    }

    private fun getCustomRow(tag: String): CustomSkillRow {
        when (tag) {
            "Awareness" -> return binding.editText1
            "Business" -> return binding.editText2
            "Deduction" -> return binding.editText3
            "Education" -> return binding.editText4
            "Common Speech" -> return binding.editText5
            "Elder Speech" -> return binding.editText6
            "Dwarven" -> return binding.editText7
            "Monster Lore" -> return binding.editText8
            "Social Etiquette" -> return binding.editText9
            "Streetwise" -> return binding.editText10
            "Tactics" -> return binding.editText11
            "Teaching" -> return binding.editText12
            "Wilderness Survival" -> return binding.editText13
            "Brawling" -> return binding.editText14
            "Dodge/Escape" -> return binding.editText15
            "Melee" -> return binding.editText16
            "Riding" -> return binding.editText17
            "Sailing" -> return binding.editText18
            "Small Blades" -> return binding.editText19
            "Staff/Spear" -> return binding.editText20
            "Swordsmanship" -> return binding.editText21
            "Archery" -> return binding.editText22
            "Athletics" -> return binding.editText23
            "Crossbow" -> return binding.editText24
            "Sleight of Hand" -> return binding.editText25
            "Stealth" -> return binding.editText26
            "Physique" -> return binding.editText27
            "Endurance" -> return binding.editText28
            "Charisma" -> return binding.editText29
            "Deceit" -> return binding.editText30
            "Fine Arts" -> return binding.editText31
            "Gambling" -> return binding.editText32
            "Grooming and Style" -> return binding.editText33
            "Human Perception" -> return binding.editText34
            "Leadership" -> return binding.editText35
            "Persuasion" -> return binding.editText36
            "Performance" -> return binding.editText37
            "Seduction" -> return binding.editText38
            "Alchemy" -> return binding.editText39
            "Crafting" -> return binding.editText40
            "Disguise" -> return binding.editText41
            "First Aid" -> return binding.editText42
            "Forgery" -> return binding.editText43
            "Pick Lock" -> return binding.editText44
            "Trap Crafting" -> return binding.editText45
            "Courage" -> return binding.editText46
            "Hex Weaving" -> return binding.editText47
            "Intimidation" -> return binding.editText48
            "Spell Casting" -> return binding.editText49
            "Resist Magic" -> return binding.editText50
            "Resist Coercion" -> return binding.editText51
            "Ritual Crafting" -> return binding.editText52
        }
        return binding.editText1
    }

    private fun showDialogIP() {
        if (requireActivity() is MainActivity) {
            (requireActivity() as MainActivity).showEditStatDialog(
                label = "Current IP",
                onPlus = {
                    mainCharacterViewModel.onIpChange(it)
                },
                onMinus = {
                    mainCharacterViewModel.onIpChange(-it)
                },
                currentValue = mainCharacterViewModel.ip.value.toString()
            )
        } else if (requireActivity() is CharCreationActivity) {
            (requireActivity() as CharCreationActivity).showEditStatDialog(
                label = "Current IP",
                onPlus = {
                    mainCharacterViewModel.onIpChange(it)
                },
                onMinus = {
                    mainCharacterViewModel.onIpChange(-it)
                },
                currentValue = mainCharacterViewModel.ip.value.toString()
            )
        }
    }

    private fun showDialogDisclaimer() {
        val dialog = Dialog(requireContext())
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val bind: CustomDialogHelpInfoBinding = CustomDialogHelpInfoBinding.inflate(layoutInflater)
        dialog.setContentView(bind.root)

        bind.textViewInfo.text = resources.getString(R.string.characterSkills_info)
        bind.textViewInfo.typeface = Typeface.DEFAULT

        bind.customTitle.setTitle("Character Skills")
        bind.customTitle.setTitleSize(18F)
        bind.checkBox.visibility = View.GONE

        bind.okButton.setOnClickListener {
            dialog.dismiss()
        }
        mainCharacterViewModel.saveSkillsInfoMode(false)
        dialog.show()
    }
}