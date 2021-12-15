package com.witcher.thewitcherrpg.feature_character_sheet.presentation.character_information

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.BitmapFactory
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.witcher.thewitcherrpg.R
import com.witcher.thewitcherrpg.core.presentation.MainCharacterViewModel
import com.witcher.thewitcherrpg.databinding.CustomDialogHelpInfoBinding
import com.witcher.thewitcherrpg.databinding.FragmentCharBinding
import com.witcher.thewitcherrpg.feature_character_sheet.presentation.character_information.child_fragments.adapters.ViewPagerAdapter
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException


class CharFragment : Fragment() {
    private var _binding: FragmentCharBinding? = null
    private val binding get() = _binding!!
    private lateinit var tabAdapter: ViewPagerAdapter

    private val mainCharacterViewModel: MainCharacterViewModel by activityViewModels()

    @SuppressLint("NewApi")
    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()){
            uri: Uri? -> binding.imageView.setImageURI(uri)
        try {
            if (uri != null) {
                mainCharacterViewModel.saveImageToInternalStorage(uri)
            }
            (binding.imageView.layoutParams as ViewGroup.MarginLayoutParams).setMargins(0,0,0,0)
        }
        catch (e: NullPointerException){
            Snackbar.make(binding.root, "Action Cancelled",
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    private val requestPermission =
        registerForActivityResult(RequestPermission()) { isGranted ->
            // Do something if permission granted
            if (isGranted) {
                Log.i("DEBUG", "permission granted")
                getContent.launch("image/*")
            } else {
                Log.i("DEBUG", "permission denied")
                Snackbar.make(binding.root, "Permission required to set image.",
                    Snackbar.LENGTH_SHORT).show()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCharBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.lifecycleOwner = this
        binding.mainViewModel = mainCharacterViewModel

        //Check whether permission is granted to access internal storage to set character image
        binding.imageView.setOnClickListener{
            requestPermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        //Setting up viewPager
        val numTabs = 4
        val tabTitles = listOf("Quick Stats", "Profession", "Personal", "Background")

        tabAdapter = ViewPagerAdapter(childFragmentManager, lifecycle, numTabs)

        val viewPager = binding.viewPager
        viewPager.apply{adapter = tabAdapter}

        val tabs: TabLayout = binding.CharacterTabs

        TabLayoutMediator(tabs, viewPager,true) {tab, position ->
            tab.text = tabTitles[position]
        }.attach()

        lifecycleScope.launch {
            launch {
                mainCharacterViewModel.charInfoMode.collect { infoIsEnabled ->
                    if (infoIsEnabled) {
                        showDialogDisclaimer()
                    }
                }
            }
            launch {
                mainCharacterViewModel.image.collect { newImage ->
                    if (loadImageFromStorage(newImage))
                        (binding.imageView.layoutParams as ViewGroup.MarginLayoutParams).setMargins(0,0,0,0)
                }
            }
        }

        binding.buttonHelp.setOnClickListener {
            showDialogDisclaimer()
        }

        return view

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadImageFromStorage(path: String): Boolean {
        return try {
            val f = File(path)
            val b = BitmapFactory.decodeStream(FileInputStream(f))
            val img = binding.imageView
            img.setImageBitmap(b)
            true
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            false
        }
    }

    private fun showDialogDisclaimer() {
        val dialog = Dialog(requireContext())
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val bind: CustomDialogHelpInfoBinding = CustomDialogHelpInfoBinding.inflate(layoutInflater)
        dialog.setContentView(bind.root)

        bind.textViewInfo.text = resources.getString(R.string.characterInformation_info)
        bind.textViewInfo.typeface = Typeface.DEFAULT

        bind.customTitle.setTitle("Character Information")
        bind.customTitle.setTitleSize(18F)
        bind.checkBox.visibility = View.GONE

        bind.okButton.setOnClickListener {
            mainCharacterViewModel.saveCharInfoMode(false)
            dialog.dismiss()
        }
        dialog.show()
    }
}