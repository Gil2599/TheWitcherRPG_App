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
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.witcher.thewitcherrpg.R
import com.witcher.thewitcherrpg.core.presentation.MainCharacterViewModel
import com.witcher.thewitcherrpg.databinding.CustomDialogAddSpellBinding
import com.witcher.thewitcherrpg.databinding.FragmentMagicAddBinding
import com.witcher.thewitcherrpg.feature_character_sheet.domain.item_types.MagicType
import com.witcher.thewitcherrpg.feature_character_sheet.domain.models.MagicHeader
import com.witcher.thewitcherrpg.feature_character_sheet.domain.models.MagicItem
import com.witcher.thewitcherrpg.feature_character_sheet.presentation.magic.MagicViewPagerAdapter
import com.witcher.thewitcherrpg.feature_character_sheet.presentation.magic.spellListAdapter.CharacterMagicListAdapter
import java.util.*
import kotlin.collections.ArrayList


class MagicAddFragment : Fragment() {
    private var _binding: FragmentMagicAddBinding? = null
    private val binding get() = _binding!!
    private var itemList = arrayListOf<Any>()
    private lateinit var magicType: MagicViewPagerAdapter.FragmentName

    private val mainCharacterViewModel: MainCharacterViewModel by activityViewModels()

    private val magicAdapter = CharacterMagicListAdapter { spell ->
        showSpellDialog(spell)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMagicAddBinding.inflate(inflater, container, false)
        val view = binding.root

        magicType = MagicAddFragmentArgs.fromBundle(arguments!!).magicType


        //Adds a callback to back button to return to previous fragment in nav graph instead of destroying activity
        val callback = requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            // Handle the back button event
            Navigation.findNavController(view)
                .navigate(R.id.action_magicAddFragment_to_charMagicFragment)
        }
        callback.isEnabled = true

        binding.magicAddRv.adapter = magicAdapter
        binding.magicAddRv.layoutManager = LinearLayoutManager(requireContext())

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
                newItemList.add(MagicHeader("Novice Spells"))
                newItemList.addAll(mainCharacterViewModel.getMagicList(R.array.novice_spells_list_data))

                newItemList.add(MagicHeader("Journeyman Spells"))
                newItemList.addAll(mainCharacterViewModel.getMagicList(R.array.journeyman_spells_list_data))

                newItemList.add(MagicHeader("Master Spells"))
                newItemList.addAll(mainCharacterViewModel.getMagicList(R.array.master_spells_list_data))

            }
            MagicViewPagerAdapter.FragmentName.INVOCATIONS -> {
                newItemList.add(MagicHeader("Novice Druid Invocations"))
                newItemList.addAll(mainCharacterViewModel.getMagicList(R.array.novice_druidInvo_list_data))

                newItemList.add(MagicHeader("Journeyman Druid Invocations"))
                newItemList.addAll(mainCharacterViewModel.getMagicList(R.array.journeyman_druidInvo_list_data))

                newItemList.add(MagicHeader("Master Druid Invocations"))
                newItemList.addAll(mainCharacterViewModel.getMagicList(R.array.master_druidInvo_data))

                newItemList.add(MagicHeader("Novice Preacher Invocations"))
                newItemList.addAll(mainCharacterViewModel.getMagicList(R.array.novice_preacherInvo_list_data))

                newItemList.add(MagicHeader("Journeyman Preacher Invocations"))
                newItemList.addAll(mainCharacterViewModel.getMagicList(R.array.journeyman_preacherInvo_list_data))

                newItemList.add(MagicHeader("Master Preacher Invocations"))
                newItemList.addAll(mainCharacterViewModel.getMagicList(R.array.master_preacherInvo_list_data))

                newItemList.add(MagicHeader("Arch Priest Invocations"))
                newItemList.addAll(mainCharacterViewModel.getMagicList(R.array.archPriestInvo_list_data))
            }
            MagicViewPagerAdapter.FragmentName.RITUALS -> {
                newItemList.add(MagicHeader("Novice Rituals"))
                newItemList.addAll(mainCharacterViewModel.getMagicList(R.array.novice_rituals_list_data))

                newItemList.add(MagicHeader("Journeyman Rituals"))
                newItemList.addAll(mainCharacterViewModel.getMagicList(R.array.journeyman_rituals_list_data))

                newItemList.add(MagicHeader("Master Rituals"))
                newItemList.addAll(mainCharacterViewModel.getMagicList(R.array.master_rituals_list_data))
            }
            MagicViewPagerAdapter.FragmentName.HEXES -> {
                newItemList.add(MagicHeader("Hexes"))
                newItemList.addAll(mainCharacterViewModel.getMagicList(R.array.hexes_list_data))
            }
            MagicViewPagerAdapter.FragmentName.SIGNS -> {
                newItemList.add(MagicHeader("Basic Signs"))
                newItemList.addAll(mainCharacterViewModel.getMagicList(R.array.basic_signs_list_data))

                newItemList.add(MagicHeader("Alternate Signs"))
                newItemList.addAll(mainCharacterViewModel.getMagicList(R.array.alternate_signs_list_data))
            }
        }
        itemList = newItemList
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
                    tempItemList.add(MagicHeader("Novice Spells"))
                }
                for (item in magicList) {

                    if (!journeymanHeaderAdded && item.type == MagicType.JOURNEYMAN_SPELL) {
                        tempItemList.add(MagicHeader("Journeyman Spells"))
                        tempItemList.add(item)
                        journeymanHeaderAdded = true
                        continue
                    }

                    if (!masterHeaderAdded && item.type == MagicType.MASTER_SPELL) {
                        tempItemList.add(MagicHeader("Master Spells"))
                        tempItemList.add(item)
                        masterHeaderAdded = true
                        continue
                    }

                    tempItemList.add(item)
                }
            }
            MagicViewPagerAdapter.FragmentName.INVOCATIONS -> {
                if (magicList.any { it.type == MagicType.NOVICE_DRUID_INVOCATION }) {
                    tempItemList.add(MagicHeader("Novice Druid Invocations"))
                }
                for (item in magicList) {

                    if (!journeymanHeaderAdded && item.type == MagicType.JOURNEYMAN_DRUID_INVOCATION) {
                        tempItemList.add(MagicHeader("Journeyman Druid Invocations"))
                        tempItemList.add(item)
                        journeymanHeaderAdded = true
                        continue
                    }

                    if (!masterHeaderAdded && item.type == MagicType.MASTER_DRUID_INVOCATION) {
                        tempItemList.add(MagicHeader("Master Druid Invocations"))
                        tempItemList.add(item)
                        masterHeaderAdded = true
                        continue
                    }

                    if (!novicePreacherHeaderAdded && item.type == MagicType.NOVICE_PREACHER_INVOCATION) {
                        tempItemList.add(MagicHeader("Novice Preacher Invocations"))
                        tempItemList.add(item)
                        novicePreacherHeaderAdded = true
                        continue
                    }

                    if (!journeyPreacherHeaderAdded && item.type == MagicType.JOURNEYMAN_PREACHER_INVOCATION) {
                        tempItemList.add(MagicHeader("Journeyman Preacher Invocations"))
                        tempItemList.add(item)
                        journeyPreacherHeaderAdded = true
                        continue
                    }

                    if (!masterPreacherHeaderAdded && item.type == MagicType.MASTER_PREACHER_INVOCATION) {
                        tempItemList.add(MagicHeader("Master Preacher Invocations"))
                        tempItemList.add(item)
                        masterPreacherHeaderAdded = true
                        continue
                    }

                    if (!archPriestHeaderAdded && item.type == MagicType.ARCH_PRIEST_INVOCATION) {
                        tempItemList.add(MagicHeader("Arch Priest Invocations"))
                        tempItemList.add(item)
                        archPriestHeaderAdded = true
                        continue
                    }

                    tempItemList.add(item)
                }
            }
            MagicViewPagerAdapter.FragmentName.RITUALS -> {
                if (magicList.any { it.type == MagicType.NOVICE_RITUAL }) {
                    tempItemList.add(MagicHeader("Novice Rituals"))
                }
                for (item in magicList) {

                    if (!journeymanHeaderAdded && item.type == MagicType.JOURNEYMAN_RITUAL) {
                        tempItemList.add(MagicHeader("Journeyman Rituals"))
                        tempItemList.add(item)
                        journeymanHeaderAdded = true
                        continue
                    }

                    if (!masterHeaderAdded && item.type == MagicType.MASTER_RITUAL) {
                        tempItemList.add(MagicHeader("Master Rituals"))
                        tempItemList.add(item)
                        masterHeaderAdded = true
                        continue
                    }
                    tempItemList.add(item)
                }
            }
            MagicViewPagerAdapter.FragmentName.HEXES -> {
                tempItemList.add(MagicHeader("Hexes"))
                tempItemList.addAll(magicList)
            }
            MagicViewPagerAdapter.FragmentName.SIGNS -> {
                if (magicList.any { it.type == MagicType.BASIC_SIGN }) {
                    tempItemList.add(MagicHeader("Basic Signs"))
                }
                for (item in magicList) {

                    if (!journeymanHeaderAdded && item.type == MagicType.ALTERNATE_SIGN) {
                        tempItemList.add(MagicHeader("Alternate Signs"))
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

        val staCost = "<b>" + "STA Cost: " + "</b>" + item.staminaCost
        val effect = "<b>" + "Effect: " + "</b>" + item.description
        val range = "<b>" + "Range: " + "</b>" + item.range
        val duration = "<b>" + "Duration: " + "</b>" + item.duration
        val defense = "<b>" + "Defense: " + "</b>" + item.defense
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

        //Check spell level to add it to correct character spell list
        bind.addSpellbutton.setOnClickListener {

            mainCharacterViewModel.addMagicItem(item)
            Snackbar.make(
                binding.root, "${item.name} added to ${mainCharacterViewModel.name.value}",
                Snackbar.LENGTH_SHORT
            ).show()
            dialog.dismiss()
        }

        bind.addSpellCancelButton.setOnClickListener() {
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
                MagicViewPagerAdapter.FragmentName.INVOCATIONS -> {
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