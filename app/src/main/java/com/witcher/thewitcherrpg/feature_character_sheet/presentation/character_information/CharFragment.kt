package com.witcher.thewitcherrpg.feature_character_sheet.presentation.character_information

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageView
import com.canhub.cropper.options
import com.witcher.thewitcherrpg.R
import com.witcher.thewitcherrpg.core.presentation.MainCharacterViewModel
import com.witcher.thewitcherrpg.databinding.CustomDialogHelpInfoBinding
import com.witcher.thewitcherrpg.databinding.FragmentCharBinding
import com.witcher.thewitcherrpg.feature_character_sheet.presentation.character_information.child_fragments.adapters.ViewPagerAdapter
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import kotlin.contracts.contract


class CharFragment : Fragment() {
    private var _binding: FragmentCharBinding? = null
    private val binding get() = _binding!!
    private lateinit var tabAdapter: ViewPagerAdapter
    private val mainCharacterViewModel: MainCharacterViewModel by activityViewModels()

    private val requestPermission =
        registerForActivityResult(RequestPermission()) { isGranted ->
            // Do something if permission granted
            if (isGranted) {
                Log.i("DEBUG", "permission granted")
                cropLauncher.launch(
                    options {
                        setImageSource(includeGallery = true, includeCamera = false)
                        setGuidelines(CropImageView.Guidelines.ON)
                        setAspectRatio(aspectRatioX = 1, aspectRatioY = 1)
                        setCropShape(CropImageView.CropShape.OVAL)
                    }
                )
            } else {
                Log.i("DEBUG", "permission denied")
                Snackbar.make(
                    binding.root, "Permission required to set image.",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCharBinding.inflate(inflater, container, false)
        val view = binding.root

        lifecycleScope.launch {
            launch {
                mainCharacterViewModel.isDarkModeEnabled.collect { darkModeIsEnabled ->
                    if (darkModeIsEnabled) {
                        binding.lottieAnimationView.setAnimation("border_animation.json")
                    } else {
                        binding.lottieAnimationView.setAnimation("border_animation_blue.json")
                    }
                }
            }
        }

        binding.lifecycleOwner = this
        binding.mainViewModel = mainCharacterViewModel

        //Check whether permission is granted to access internal storage to set character image
        binding.imageView.setOnClickListener {
            requestPermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        //Setting up viewPager
        val numTabs = 4
        val tabTitles = listOf("Quick Stats", "Profession", "Personal", "Background")

        tabAdapter = ViewPagerAdapter(childFragmentManager, lifecycle, numTabs)

        val viewPager = binding.viewPager
        viewPager.apply { adapter = tabAdapter }

        val tabs: TabLayout = binding.CharacterTabs

        TabLayoutMediator(tabs, viewPager, true) { tab, position ->
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
                mainCharacterViewModel.imageBitmap.collect { newImage ->
                    (binding.imageView.layoutParams as ViewGroup.MarginLayoutParams).setMargins(
                        0,
                        0,
                        0,
                        0
                    )
                    binding.imageView.setImageBitmap(newImage)
                }
            }
        }

        binding.buttonHelp.setOnClickListener {
            showDialogDisclaimer()
        }

        return view
    }

    private var cropLauncher =
        registerForActivityResult(CropImageContract()) { result ->
            if (result.isSuccessful) {
                //val uriContent = result.uriContent
                val uriFilePath = result.getUriFilePath(requireContext())
                if (uriFilePath != null) {
                    mainCharacterViewModel.loadImageFromStorage(uriFilePath)
                        ?.let { mainCharacterViewModel.setCharacterImage(it) }
                }

            } else {
                // An error occurred.
                val exception = result.error
            }
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
