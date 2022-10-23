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
                openGallery()
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

    private fun openGallery() {
        val galIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        cropLauncher.launch(
            Intent.createChooser(
                galIntent,
                "Select Image From Gallery "
            )
        )
    }

    private var cropLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                if (data != null) {
                    val uri = result.data!!.data!!
                    cropImages(uri)
                }
            }
        }

    private var setImageLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                if (result.data != null) {
                    try {
                        val bundle = result.data!!.extras
                        val bitmap = bundle!!.getParcelable<Bitmap>("data")
                        if (bitmap != null) {
                            mainCharacterViewModel.setCharacterImage(bitmap)
                        }
                    } catch (e: Exception) {
                        Snackbar.make(
                            binding.root, "Action Cancelled",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

    private fun cropImages(uri: Uri) {
        try {
            val cropIntent = Intent("com.android.camera.action.CROP")
            cropIntent.setDataAndType(uri, "image/*")
            cropIntent.putExtra("crop", true)
            cropIntent.putExtra("outputX", 180)
            cropIntent.putExtra("outputY", 180)
            cropIntent.putExtra("aspectX", 1)
            cropIntent.putExtra("aspectY", 1)
            cropIntent.putExtra("scaleUpIfNeeded", true)
            cropIntent.putExtra("return-data", true)
            cropIntent.putExtra("circleCrop", " ")
            setImageLauncher.launch(cropIntent)

        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
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
