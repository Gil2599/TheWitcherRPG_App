package com.witcher.thewitcherrpg.feature_character_sheet.presentation.campaign_notes

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.witcher.thewitcherrpg.core.presentation.MainCharacterViewModel
import com.witcher.thewitcherrpg.databinding.CustomDialogCampaignNoteBinding
import com.witcher.thewitcherrpg.databinding.FragmentCampaignNotesBinding
import com.witcher.thewitcherrpg.feature_character_sheet.domain.models.CampaignNote
import com.google.android.material.snackbar.Snackbar
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CampaignNotesFragment : Fragment() {
    private var _binding: FragmentCampaignNotesBinding? = null
    private val binding get() = _binding!!

    private val mainCharacterViewModel: MainCharacterViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCampaignNotesBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.customTitle.setTitle("Campaign Notes")
        binding.customTitle.setTitleSize(20F)

        binding.addButton.setOnClickListener {
            showCampaignNoteDialog(false, null)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        onRVInit()

        return view
    }

    private fun onRVInit() {
        val notesListAdapter = CampaignNotesListAdapter { event ->
            showCampaignNoteDialog(editEvent = true, event)
        }
        notesListAdapter.setData(mainCharacterViewModel.campaignNotes.value)
        binding.recyclerView.adapter = notesListAdapter
    }

    private fun showCampaignNoteDialog(editEvent: Boolean, note: CampaignNote?) {
        val dialog = Dialog(requireContext())
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        val bind: CustomDialogCampaignNoteBinding =
            CustomDialogCampaignNoteBinding.inflate(layoutInflater)

        bind.customTitle.setTitle("Campaign Note")
        bind.customTitle.setTitleSize(17F)
        bind.etTitleLayout

        if (!editEvent) {
            bind.buttonRemoveItem.visibility = View.GONE
        } else {
            bind.etTitle.setText(note!!.title)
            bind.etDescription.setText(note.description)
            bind.buttonAddItem.text = "Update"
        }

        bind.buttonAddItem.setOnClickListener {
            if (bind.etTitle.text.toString().isNotBlank() &&
                bind.etDescription.text.toString().isNotBlank()
            ) {
                if (!editEvent) {
                    val current = LocalDate.now()
                    val formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy")
                    val formatted = current.format(formatter)

                    val newNote = CampaignNote(
                        title = bind.etTitle.text.toString(),
                        description = bind.etDescription.text.toString(),
                        date = formatted
                    )
                    mainCharacterViewModel.addCampaignNote(newNote)
                    onRVInit()

                } else {
                    note!!.title = bind.etTitle.text.toString()
                    note.description = bind.etDescription.text.toString()
                    mainCharacterViewModel.updateCampaignNote(note)
                }
                onRVInit()
                dialog.dismiss()
            } else {
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
            mainCharacterViewModel.removeCampaignNote(note!!)
            onRVInit()
            dialog.dismiss()
        }

        dialog.setContentView(bind.root)
        dialog.show()

    }

}