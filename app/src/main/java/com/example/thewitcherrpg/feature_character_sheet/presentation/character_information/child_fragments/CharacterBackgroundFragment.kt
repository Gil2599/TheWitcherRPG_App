package com.example.thewitcherrpg.feature_character_sheet.presentation.character_information.child_fragments

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thewitcherrpg.core.presentation.MainCharacterViewModel
import com.example.thewitcherrpg.databinding.CustomDialogLifeEventBinding
import com.example.thewitcherrpg.databinding.FragmentCharacterBackgroundBinding
import com.example.thewitcherrpg.feature_character_sheet.domain.models.LifeEvent
import com.example.thewitcherrpg.feature_character_sheet.presentation.character_information.child_fragments.adapters.LifeEventsListAdapter
import com.google.android.material.snackbar.Snackbar

class CharacterBackgroundFragment : Fragment() {
    private var _binding: FragmentCharacterBackgroundBinding? = null
    private val binding get() = _binding!!

    private val mainCharacterViewModel: MainCharacterViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCharacterBackgroundBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.customTitle.setTitle("Background Events")
        binding.customTitle.setTitleSize(20F)

        binding.addButton.setOnClickListener {
            showLifeEventDialog(editEvent = false, null)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        onRVInit()

        return view
    }

    private fun onRVInit() {
        val lifeEventAdapter = LifeEventsListAdapter { event ->
            showLifeEventDialog(editEvent = true, event)
        }
        lifeEventAdapter.setData(mainCharacterViewModel.lifeEvents.value)
        binding.recyclerView.adapter = lifeEventAdapter
    }

    private fun showLifeEventDialog(editEvent: Boolean, event: LifeEvent?) {
        val dialog = Dialog(requireContext())
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        val bind: CustomDialogLifeEventBinding =
            CustomDialogLifeEventBinding.inflate(layoutInflater)

        bind.customTitle.setTitle("Life Event")
        bind.customTitle.setTitleSize(17F)

        if (!editEvent) {
            bind.buttonRemoveItem.visibility = View.GONE
        } else {
            bind.etTitle.setText(event!!.title)
            bind.etAge.setText(event.age.toString())
            bind.etDescription.setText(event.description)
            bind.buttonAddItem.text = "Update"
        }

        bind.buttonAddItem.setOnClickListener {
            if (bind.etTitle.text.toString().isNotBlank() &&
                bind.etAge.text.toString().isNotBlank() &&
                bind.etDescription.text.toString().isNotBlank()
            ) {
                if (!editEvent) {
                    val lifeEvent = LifeEvent(
                        title = bind.etTitle.text.toString(),
                        age = bind.etAge.text.toString().toInt(),
                        description = bind.etDescription.text.toString()
                    )
                    mainCharacterViewModel.addLifeEvent(lifeEvent)
                } else {
                    event!!.title = bind.etTitle.text.toString()
                    event.age = bind.etAge.text.toString().toInt()
                    event.description = bind.etDescription.text.toString()
                    mainCharacterViewModel.updateLifeEvent(event)
                }
                onRVInit()
                dialog.dismiss()
            }
            else {
                Snackbar.make(
                    bind.root, "Please fill out every field.",
                    Snackbar.LENGTH_SHORT,
                ).show()
            }
        }

        bind.buttonCancel.setOnClickListener {
            dialog.dismiss()
        }

        bind.buttonRemoveItem.setOnClickListener {
            mainCharacterViewModel.removeLifeEvent(event!!)
            onRVInit()
            dialog.dismiss()
        }

        dialog.setContentView(bind.root)
        dialog.show()

    }
}
