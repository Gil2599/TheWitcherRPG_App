package com.witcher.thewitcherrpg.feature_character_sheet.presentation.magic.addMagicFragments

import android.app.Dialog
import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.addCallback
import androidx.core.text.HtmlCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.witcher.thewitcherrpg.R
import com.witcher.thewitcherrpg.core.presentation.MainCharacterViewModel
import com.witcher.thewitcherrpg.databinding.CustomDialogAddHexBinding
import com.witcher.thewitcherrpg.databinding.CustomDialogAddSpellBinding
import com.witcher.thewitcherrpg.databinding.FragmentMagicAddBinding
import com.witcher.thewitcherrpg.feature_character_sheet.domain.item_types.MagicType
import com.witcher.thewitcherrpg.feature_character_sheet.domain.models.ListHeader
import com.witcher.thewitcherrpg.feature_character_sheet.domain.models.MagicItem
import com.witcher.thewitcherrpg.feature_character_sheet.presentation.magic.MagicViewPagerAdapter
import com.witcher.thewitcherrpg.feature_character_sheet.presentation.magic.spellListAdapter.CharacterMagicListAdapter
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList


class MagicAddFragment : Fragment() {
    private var _binding: FragmentMagicAddBinding? = null
    private val binding get() = _binding!!
    private var itemList = arrayListOf<Any>()
    private lateinit var magicType: MagicViewPagerAdapter.FragmentName

    private val mainCharacterViewModel: MainCharacterViewModel by activityViewModels()

    private val magicAdapter = CharacterMagicListAdapter { magic ->
        when (magic.type) {
            MagicType.HEX -> showHexDialog(magic)
            MagicType.NOVICE_RITUAL, MagicType.JOURNEYMAN_RITUAL, MagicType.MASTER_RITUAL -> showRitualDialog(magic)
            else -> {showSpellDialog(magic)}
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMagicAddBinding.inflate(inflater, container, false)
        val view = binding.root

        magicType = MagicAddFragmentArgs.fromBundle(arguments!!).magicGeneralType


        //Adds a callback to back button to return to previous fragment in nav graph instead of destroying activity
        val callback = requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            // Handle the back button event
            Navigation.findNavController(view)
                .navigate(R.id.action_magicAddFragment_to_charMagicFragment)
        }
        callback.isEnabled = true

        binding.magicAddRv.adapter = magicAdapter
        binding.magicAddRv.layoutManager = LinearLayoutManager(requireContext())

        lifecycleScope.launch {
            launch {
                mainCharacterViewModel.customMagicList.collect {
                    listAdaptersInit()
                }
            }
        }

        listAdaptersInit()
        setAdapterData()

        binding.magicFilterEt.addTextChangedListener {
            if (it != null) {
                updateSpellListSearch(it)
            }
        }

        binding.autoCompleteTextViewSortBy.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, _, _ ->
                val sortByArray = resources.getStringArray(R.array.sortBy)
                val selection: String = binding.autoCompleteTextViewSortBy.text.toString()

                if (sortByArray.contains(selection)) {
                    when (selection) {
                        sortByArray[0] -> sortSpellList(SortBy.DEFAULT)
                        sortByArray[1] -> sortSpellList(SortBy.A_Z_DEC)
                        sortByArray[2] -> sortSpellList(SortBy.A_Z_INC)
                        sortByArray[3] -> sortSpellList(SortBy.ELEMENT)
                        sortByArray[4] -> sortSpellList(SortBy.RANGE)
                        sortByArray[5] -> sortSpellList(SortBy.STA_COST)
                    }
                } else if (selection == "Difficulty") {
                    sortSpellList(SortBy.DIFFICULTY)
                } else if (selection == "Danger") {
                    sortSpellList(SortBy.DANGER)
                }
            }

        return view
    }

    private fun listAdaptersInit() {

        val newItemList = arrayListOf<Any>()

        when (magicType) {
            MagicViewPagerAdapter.FragmentName.SPELLS -> {
                newItemList.add(ListHeader(resources.getString(R.string.novice_spells)))
                newItemList.addAll(mainCharacterViewModel.getMagicList(R.array.novice_spells_list_data))
                newItemList.addAll(getCustomMagic(MagicType.NOVICE_SPELL))

                newItemList.add(ListHeader(resources.getString(R.string.journeyman_spells)))
                newItemList.addAll(mainCharacterViewModel.getMagicList(R.array.journeyman_spells_list_data))
                newItemList.addAll(getCustomMagic(MagicType.JOURNEYMAN_SPELL))


                newItemList.add(ListHeader(resources.getString(R.string.master_spells)))
                newItemList.addAll(mainCharacterViewModel.getMagicList(R.array.master_spells_list_data))
                newItemList.addAll(getCustomMagic(MagicType.MASTER_SPELL))

            }
            MagicViewPagerAdapter.FragmentName.DRUID_INVOCATIONS -> {
                newItemList.add(ListHeader(resources.getString(R.string.novice_druid_invocations)))
                newItemList.addAll(mainCharacterViewModel.getMagicList(R.array.novice_druidInvo_list_data))
                newItemList.addAll(getCustomMagic(MagicType.NOVICE_DRUID_INVOCATION))

                newItemList.add(ListHeader(resources.getString(R.string.journeyman_druid_invocations)))
                newItemList.addAll(mainCharacterViewModel.getMagicList(R.array.journeyman_druidInvo_list_data))
                newItemList.addAll(getCustomMagic(MagicType.JOURNEYMAN_DRUID_INVOCATION))

                newItemList.add(ListHeader(resources.getString(R.string.master_druid_invocations)))
                newItemList.addAll(mainCharacterViewModel.getMagicList(R.array.master_druidInvo_data))
                newItemList.addAll(getCustomMagic(MagicType.MASTER_DRUID_INVOCATION))

                newItemList.add(ListHeader(resources.getString(R.string.hierophant_flaminika_druid_invocations)))
                newItemList.addAll(mainCharacterViewModel.getMagicList(R.array.hierophant_flaminika_priestInvo_list_data))
                newItemList.addAll(getCustomMagic(MagicType.HIEROPHANT_FLAMINIKA_DRUID_INVOCATION))
            }
            MagicViewPagerAdapter.FragmentName.PRIEST_INVOCATIONS -> {
                newItemList.add(ListHeader(resources.getString(R.string.novice_priest_invocations)))
                newItemList.addAll(mainCharacterViewModel.getMagicList(R.array.novice_priestInvo_list_data))
                newItemList.addAll(getCustomMagic(MagicType.NOVICE_PREACHER_INVOCATION))

                newItemList.add(ListHeader(resources.getString(R.string.journeyman_priest_invocations)))
                newItemList.addAll(mainCharacterViewModel.getMagicList(R.array.journeyman_priestInvo_list_data))
                newItemList.addAll(getCustomMagic(MagicType.JOURNEYMAN_PREACHER_INVOCATION))

                newItemList.add(ListHeader(resources.getString(R.string.master_priest_invocations)))
                newItemList.addAll(mainCharacterViewModel.getMagicList(R.array.master_priestInvo_list_data))
                newItemList.addAll(getCustomMagic(MagicType.MASTER_PREACHER_INVOCATION))

                newItemList.add(ListHeader(resources.getString(R.string.arch_priest_invocations)))
                newItemList.addAll(mainCharacterViewModel.getMagicList(R.array.archPriestInvo_list_data))
                newItemList.addAll(getCustomMagic(MagicType.ARCH_PRIEST_INVOCATION))
            }
            MagicViewPagerAdapter.FragmentName.RITUALS -> {
                newItemList.add(ListHeader(resources.getString(R.string.novice_rituals)))
                newItemList.addAll(mainCharacterViewModel.getMagicList(R.array.novice_rituals_list_data))
                newItemList.addAll(getCustomMagic(MagicType.NOVICE_RITUAL))

                newItemList.add(ListHeader(resources.getString(R.string.journeyman_rituals)))
                newItemList.addAll(mainCharacterViewModel.getMagicList(R.array.journeyman_rituals_list_data))
                newItemList.addAll(getCustomMagic(MagicType.JOURNEYMAN_RITUAL))

                newItemList.add(ListHeader(resources.getString(R.string.master_rituals)))
                newItemList.addAll(mainCharacterViewModel.getMagicList(R.array.master_rituals_list_data))
                newItemList.addAll(getCustomMagic(MagicType.MASTER_RITUAL))
            }
            MagicViewPagerAdapter.FragmentName.HEXES -> {
                newItemList.add(ListHeader(resources.getString(R.string.hexes)))
                newItemList.addAll(mainCharacterViewModel.getMagicList(R.array.hexes_list_data))
                newItemList.addAll(getCustomMagic(MagicType.HEX))
            }
            MagicViewPagerAdapter.FragmentName.SIGNS -> {
                newItemList.add(ListHeader(resources.getString(R.string.basic_signs)))
                newItemList.addAll(mainCharacterViewModel.getMagicList(R.array.basic_signs_list_data))
                newItemList.addAll(getCustomMagic(MagicType.BASIC_SIGN))

                newItemList.add(ListHeader(resources.getString(R.string.alternate_signs)))
                newItemList.addAll(mainCharacterViewModel.getMagicList(R.array.alternate_signs_list_data))
                newItemList.addAll(getCustomMagic(MagicType.ALTERNATE_SIGN))
            }
        }
        if (newItemList != itemList){
            itemList = newItemList
            setAdapterData()
        }
    }

    private fun setAdapterData() {
        magicAdapter.setData(itemList)
        binding.magicAddRv.scheduleLayoutAnimation()
    }

    private fun updateSpellListSearch(search: Editable) {
        val tempItemList = arrayListOf<Any>()
        listAdaptersInit()
        for (item in itemList) {
            if (item is MagicItem) {
                if (item.name.lowercase(Locale.getDefault()).contains(
                        search.toString()
                            .lowercase(Locale.getDefault())
                    )
                ) {
                    tempItemList.add(item)
                }
            } else {
                tempItemList.add(item)
            }
        }
        itemList = tempItemList
        setAdapterData()
    }

    private fun sortSpellList(sortBy: SortBy) {
        val magicItemList: ArrayList<MagicItem> =
            ArrayList(itemList.mapNotNull { if (it is MagicItem) it else null })

        when (sortBy) {
            SortBy.DEFAULT, SortBy.DANGER -> {
                val tempList = itemList
                val tempList2 = arrayListOf<Any>()
                listAdaptersInit()
                for (item in itemList) {
                    if (item in tempList) {
                        tempList2.add(item)
                    }
                }
                itemList = tempList2
                setAdapterData()
                return
            }
            SortBy.A_Z_DEC -> {
                magicItemList.sortWith(compareBy<MagicItem> { it.type }.thenBy { it.name })
            }
            SortBy.A_Z_INC -> {
                magicItemList.sortWith(compareBy<MagicItem> { it.type }.thenByDescending { it.name })
            }
            SortBy.ELEMENT -> {
                magicItemList.sortWith(compareBy<MagicItem> { it.type }.thenBy { it.element })
            }
            SortBy.RANGE -> {
                magicItemList.sortWith(compareBy<MagicItem> { it.type }.thenBy {
                    if (!it.range?.filter(Char::isDigit).isNullOrBlank()) it.range?.filter(
                        Char::isDigit
                    )
                        ?.toInt() else 0
                })
            }
            SortBy.STA_COST -> {
                magicItemList.sortWith(compareBy<MagicItem> { it.type }.thenBy { it.staminaCost })
            }
            SortBy.DIFFICULTY -> {
                magicItemList.sortWith(compareBy<MagicItem> { it.type }.thenBy { it.difficulty })
            }
        }
        addHeadersToList(magicItemList)
    }

    private fun addHeadersToList(magicList: ArrayList<MagicItem>) {
        val tempItemList = arrayListOf<Any>()
        var journeymanHeaderAdded = false
        var masterHeaderAdded = false
        var novicePreacherHeaderAdded = false
        var journeyPreacherHeaderAdded = false
        var masterPreacherHeaderAdded = false
        var archPriestHeaderAdded = false

        when (magicType) {
            MagicViewPagerAdapter.FragmentName.SPELLS -> {
                if (magicList.any { it.type == MagicType.NOVICE_SPELL }) {
                    tempItemList.add(ListHeader(resources.getString(R.string.novice_spells)))
                }
                for (item in magicList) {

                    if (!journeymanHeaderAdded && item.type == MagicType.JOURNEYMAN_SPELL) {
                        tempItemList.add(ListHeader(resources.getString(R.string.journeyman_spells)))
                        tempItemList.add(item)
                        journeymanHeaderAdded = true
                        continue
                    }

                    if (!masterHeaderAdded && item.type == MagicType.MASTER_SPELL) {
                        tempItemList.add(ListHeader(resources.getString(R.string.master_spells)))
                        tempItemList.add(item)
                        masterHeaderAdded = true
                        continue
                    }

                    tempItemList.add(item)
                }
            }
            MagicViewPagerAdapter.FragmentName.PRIEST_INVOCATIONS -> {
                if (magicList.any { it.type == MagicType.NOVICE_PREACHER_INVOCATION }) {
                    tempItemList.add(ListHeader(resources.getString(R.string.novice_priest_invocations)))
                }
                for (item in magicList) {
                    if (!journeyPreacherHeaderAdded && item.type == MagicType.JOURNEYMAN_PREACHER_INVOCATION) {
                        tempItemList.add(ListHeader(resources.getString(R.string.journeyman_priest_invocations)))
                        tempItemList.add(item)
                        journeyPreacherHeaderAdded = true
                        continue
                    }

                    if (!masterPreacherHeaderAdded && item.type == MagicType.MASTER_PREACHER_INVOCATION) {
                        tempItemList.add(ListHeader(resources.getString(R.string.master_priest_invocations)))
                        tempItemList.add(item)
                        masterPreacherHeaderAdded = true
                        continue
                    }

                    if (!archPriestHeaderAdded && item.type == MagicType.ARCH_PRIEST_INVOCATION) {
                        tempItemList.add(ListHeader(resources.getString(R.string.arch_priest_invocations)))
                        tempItemList.add(item)
                        archPriestHeaderAdded = true
                        continue
                    }

                    tempItemList.add(item)
                }
            }
            MagicViewPagerAdapter.FragmentName.DRUID_INVOCATIONS -> {
                if (magicList.any { it.type == MagicType.NOVICE_PREACHER_INVOCATION }) {
                    tempItemList.add(ListHeader(resources.getString(R.string.novice_druid_invocations)))
                }
                for (item in magicList) {
                    if (!journeymanHeaderAdded && item.type == MagicType.JOURNEYMAN_DRUID_INVOCATION) {
                        tempItemList.add(ListHeader(resources.getString(R.string.journeyman_druid_invocations)))
                        tempItemList.add(item)
                        journeymanHeaderAdded = true
                        continue
                    }

                    if (!masterHeaderAdded && item.type == MagicType.MASTER_DRUID_INVOCATION) {
                        tempItemList.add(ListHeader(resources.getString(R.string.master_druid_invocations)))
                        tempItemList.add(item)
                        masterHeaderAdded = true
                        continue
                    }

                    if (!archPriestHeaderAdded && item.type == MagicType.HIEROPHANT_FLAMINIKA_DRUID_INVOCATION) {
                        tempItemList.add(ListHeader(resources.getString(R.string.hierophant_flaminika_druid_invocations)))
                        tempItemList.add(item)
                        archPriestHeaderAdded = true
                        continue
                    }
                    tempItemList.add(item)
                }
            }
            MagicViewPagerAdapter.FragmentName.RITUALS -> {
                if (magicList.any { it.type == MagicType.NOVICE_RITUAL }) {
                    tempItemList.add(ListHeader(resources.getString(R.string.novice_rituals)))
                }
                for (item in magicList) {

                    if (!journeymanHeaderAdded && item.type == MagicType.JOURNEYMAN_RITUAL) {
                        tempItemList.add(ListHeader(resources.getString(R.string.journeyman_rituals)))
                        tempItemList.add(item)
                        journeymanHeaderAdded = true
                        continue
                    }

                    if (!masterHeaderAdded && item.type == MagicType.MASTER_RITUAL) {
                        tempItemList.add(ListHeader(resources.getString(R.string.master_rituals)))
                        tempItemList.add(item)
                        masterHeaderAdded = true
                        continue
                    }
                    tempItemList.add(item)
                }
            }
            MagicViewPagerAdapter.FragmentName.HEXES -> {
                tempItemList.add(ListHeader(resources.getString(R.string.hexes)))
                tempItemList.addAll(magicList)
            }
            MagicViewPagerAdapter.FragmentName.SIGNS -> {
                if (magicList.any { it.type == MagicType.BASIC_SIGN }) {
                    tempItemList.add(ListHeader(resources.getString(R.string.basic_signs)))
                }
                for (item in magicList) {

                    if (!journeymanHeaderAdded && item.type == MagicType.ALTERNATE_SIGN) {
                        tempItemList.add(ListHeader(resources.getString(R.string.alternate_signs)))
                        tempItemList.add(item)
                        journeymanHeaderAdded = true
                        continue
                    }

                    tempItemList.add(item)
                }
            }
        }
        itemList = tempItemList
        setAdapterData()
    }

    private fun showSpellDialog(item: MagicItem) {
        val dialog = Dialog(requireContext())
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val bind: CustomDialogAddSpellBinding = CustomDialogAddSpellBinding.inflate(layoutInflater)
        dialog.setContentView(bind.root)

        val staCost = "<b>" + resources.getString(R.string.sta_cost) + ": " + "</b>" + item.staminaCost
        val effect = "<b>" + resources.getString(R.string.effect) + ": " + "</b>" + item.description
        val range = "<b>" + resources.getString(R.string.range) + ": " + "</b>" + item.range
        val duration = "<b>" + resources.getString(R.string.duration) + ": " + "</b>" + item.duration
        val defense = "<b>" + resources.getString(R.string.defense) + ": " + "</b>" + item.defense
        val element = item.element

        bind.customTitle.setTitle(item.name)
        bind.customTitle.setTitleSize(18F)
        bind.addStaCostText.text = HtmlCompat.fromHtml(staCost, HtmlCompat.FROM_HTML_MODE_LEGACY)
        bind.addRangeText.text = HtmlCompat.fromHtml(range, HtmlCompat.FROM_HTML_MODE_LEGACY)
        bind.addDefenseText.text = HtmlCompat.fromHtml(defense, HtmlCompat.FROM_HTML_MODE_LEGACY)
        bind.addEffectText.text = HtmlCompat.fromHtml(effect, HtmlCompat.FROM_HTML_MODE_LEGACY)
        bind.addEffectText.typeface = Typeface.DEFAULT
        bind.addDurationText.text = HtmlCompat.fromHtml(duration, HtmlCompat.FROM_HTML_MODE_LEGACY)
        bind.addSpellElementText.text = element

        if(item.isCustom) bind.removeSpellButton.visibility = View.VISIBLE
        else bind.removeSpellButton.visibility = View.GONE

        bind.removeSpellButton.setOnClickListener {
            mainCharacterViewModel.deleteCustomMagic(item)
            Snackbar.make(
                binding.root, "${item.name} ${resources.getString(R.string.deleted)}",
                Snackbar.LENGTH_SHORT
            ).show()
            dialog.dismiss()
        }

        //Check spell level to add it to correct character spell list
        bind.addSpellbutton.setOnClickListener {

            mainCharacterViewModel.addMagicItem(item)
            Snackbar.make(
                binding.root, "${item.name} ${resources.getString(R.string.added_to)} ${mainCharacterViewModel.name.value}",
                Snackbar.LENGTH_SHORT
            ).show()
            dialog.dismiss()
        }

        bind.addSpellCancelButton.setOnClickListener() {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showHexDialog(item: MagicItem) {
        val dialog = Dialog(requireContext())
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val bind : CustomDialogAddHexBinding = CustomDialogAddHexBinding.inflate(layoutInflater)
        dialog.setContentView(bind.root)

        val staCost = "<b>" + resources.getString(R.string.sta_cost) + ": " + "</b>" + if (item.staminaCost == null) resources.getString(R.string.variable) else item.staminaCost
        val effect = "<b>" + resources.getString(R.string.effect) + ": " + "</b>" + item.description
        val danger = "<b>" + resources.getString(R.string.danger) + ": " + "</b>" + item.danger
        val lift = "<b>" + resources.getString(R.string.req_to_lift) + ": " + "</b>" + item.requirementToLift

        bind.customTitle.setTitle(item.name)
        bind.customTitle.setTitleSize(18F)
        bind.addStaCostText.text = HtmlCompat.fromHtml(staCost, HtmlCompat.FROM_HTML_MODE_LEGACY)
        bind.addEffectText.text = HtmlCompat.fromHtml(effect, HtmlCompat.FROM_HTML_MODE_LEGACY)
        bind.addEffectText.typeface = Typeface.DEFAULT
        bind.addLiftText.text = HtmlCompat.fromHtml(lift, HtmlCompat.FROM_HTML_MODE_LEGACY)
        bind.addLiftText.typeface = Typeface.DEFAULT
        bind.addDangerText.text = HtmlCompat.fromHtml(danger, HtmlCompat.FROM_HTML_MODE_LEGACY)

        if(item.isCustom) bind.removeSpellButton.visibility = View.VISIBLE
        else bind.removeSpellButton.visibility = View.GONE

        bind.removeSpellButton.setOnClickListener {
            mainCharacterViewModel.deleteCustomMagic(item)
            Snackbar.make(
                binding.root, "${item.name} ${resources.getString(R.string.deleted)}",
                Snackbar.LENGTH_SHORT
            ).show()
            dialog.dismiss()
        }

        //Check spell level to add it to correct character spell list
        bind.addSpellbutton.setOnClickListener{
            mainCharacterViewModel.addMagicItem(item)
            Snackbar.make(
                binding.root, "${item.name} ${resources.getString(R.string.added_to)} ${mainCharacterViewModel.name.value}",
                Snackbar.LENGTH_SHORT
            ).show()
            dialog.dismiss()
        }

        bind.addSpellCancelButton.setOnClickListener(){
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showRitualDialog(item: MagicItem) {
        val dialog = Dialog(requireContext())
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val bind: CustomDialogAddSpellBinding = CustomDialogAddSpellBinding.inflate(layoutInflater)
        dialog.setContentView(bind.root)

        val staCost = "<b>" + resources.getString(R.string.sta_cost) + ": " + "</b>" + if (item.staminaCost == null) resources.getString(R.string.variable) else item.staminaCost
        val effect = "<b>" + resources.getString(R.string.effect) + ": " + "</b>" + item.description
        val preparation = "<b>" + resources.getString(R.string.prep_time) + ": " + "</b>" + item.preparation
        val difficulty = "<b>" + resources.getString(R.string.difficulty) + ": " + "</b>" + if (item.difficulty == null) resources.getString(R.string.variable) else item.difficulty
        val duration = "<b>" + resources.getString(R.string.duration) + ": " + "</b>" + item.duration
        val components = "<b>" + resources.getString(R.string.components) + ": " + "</b>" + item.components

        bind.customTitle.setTitle(item.name)
        bind.customTitle.setTitleSize(18F)
        bind.addStaCostText.text = HtmlCompat.fromHtml(staCost, HtmlCompat.FROM_HTML_MODE_LEGACY)
        bind.addRangeText.text = HtmlCompat.fromHtml(preparation, HtmlCompat.FROM_HTML_MODE_LEGACY)
        bind.addDefenseText.text = HtmlCompat.fromHtml(components, HtmlCompat.FROM_HTML_MODE_LEGACY)
        bind.addDefenseText.typeface = Typeface.DEFAULT
        bind.addEffectText.text = HtmlCompat.fromHtml(effect, HtmlCompat.FROM_HTML_MODE_LEGACY)
        bind.addEffectText.typeface = Typeface.DEFAULT
        bind.addDurationText.text = HtmlCompat.fromHtml(duration, HtmlCompat.FROM_HTML_MODE_LEGACY)
        bind.addSpellElementText.text =
            HtmlCompat.fromHtml(difficulty, HtmlCompat.FROM_HTML_MODE_LEGACY)

        if(item.isCustom) bind.removeSpellButton.visibility = View.VISIBLE
        else bind.removeSpellButton.visibility = View.GONE

        bind.removeSpellButton.setOnClickListener {
            mainCharacterViewModel.deleteCustomMagic(item)
            Snackbar.make(
                binding.root, "${item.name} ${resources.getString(R.string.deleted)}",
                Snackbar.LENGTH_SHORT
            ).show()
            dialog.dismiss()
        }

        //Check spell level to add it to correct character spell list
        bind.addSpellbutton.setOnClickListener{
            mainCharacterViewModel.addMagicItem(item)
            Snackbar.make(
                binding.root, "${item.name} ${resources.getString(R.string.added_to)} ${mainCharacterViewModel.name.value}",
                Snackbar.LENGTH_SHORT
            ).show()
            dialog.dismiss()
        }
        bind.addSpellCancelButton.setOnClickListener{
            dialog.dismiss()
        }
        dialog.show()
    }

    override fun onResume() {
        super.onResume()

        val sortByArray = resources.getStringArray(R.array.sortBy).toMutableList()
        val iterator = sortByArray.iterator()
        while (iterator.hasNext()) {
            val item = iterator.next()
            when (magicType) {
                MagicViewPagerAdapter.FragmentName.PRIEST_INVOCATIONS, MagicViewPagerAdapter.FragmentName.DRUID_INVOCATIONS -> {
                    if (item == "Element") {
                        iterator.remove()
                    }
                }
                MagicViewPagerAdapter.FragmentName.RITUALS, MagicViewPagerAdapter.FragmentName.HEXES -> {
                    if (item == "Element" || item == "Range") {
                        iterator.remove()
                    }
                }
                else -> {}
            }
        }

        if (magicType == MagicViewPagerAdapter.FragmentName.RITUALS) {
            sortByArray.add("Difficulty")
        }
        if (magicType == MagicViewPagerAdapter.FragmentName.HEXES) {
            sortByArray.add("Danger")
        }

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            sortByArray
        )
        binding.autoCompleteTextViewSortBy.setAdapter(adapter)

    }

    private fun getCustomMagic(magicType: MagicType) : List<MagicItem> {
        return mainCharacterViewModel.customMagicList.value.filter { it.type ==  magicType}
    }

    enum class SortBy {
        DEFAULT,
        A_Z_DEC,
        A_Z_INC,
        ELEMENT,
        RANGE,
        STA_COST,
        DIFFICULTY,
        DANGER
    }
}