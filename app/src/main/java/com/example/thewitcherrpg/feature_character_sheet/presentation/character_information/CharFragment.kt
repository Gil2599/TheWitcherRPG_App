package com.example.thewitcherrpg.feature_character_sheet.presentation.character_information

import android.Manifest
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import com.example.thewitcherrpg.feature_character_sheet.presentation.character_information.child_fragments.ViewPagerAdapter
import com.example.thewitcherrpg.databinding.FragmentCharBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import android.graphics.Bitmap
import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import java.io.*
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.lifecycle.Observer
import com.example.thewitcherrpg.core.presentation.MainCharacterViewModel
import com.google.android.material.snackbar.Snackbar


class CharFragment : Fragment() {
    private var _binding: FragmentCharBinding? = null
    private val binding get() = _binding!!
    private lateinit var tabAdapter: ViewPagerAdapter

    private val mainCharacterViewModel: MainCharacterViewModel by activityViewModels()

    @SuppressLint("NewApi")
    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()){
            uri: Uri? -> binding.imageView.setImageURI(uri)
        try {
            val bitmap: Bitmap = when {
                Build.VERSION.SDK_INT < 28 -> MediaStore.Images.Media.getBitmap(
                    requireActivity().contentResolver, uri
                )
                else -> {
                    val source = ImageDecoder.createSource(requireActivity().contentResolver, uri!!)
                    ImageDecoder.decodeBitmap(source)
                }
            }
            mainCharacterViewModel.saveImageToInternalStorage(bitmap)
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

        val imagePath = mainCharacterViewModel.image.value

        Log.d("Test", mainCharacterViewModel.image.value!!)

        val imageObserver = Observer<String> { newImagePath ->
            if (newImagePath.isNotEmpty()){
                //Make margins 0 if image is found
                (binding.imageView.layoutParams as ViewGroup.MarginLayoutParams).setMargins(0,0,0,0)
                //Load Image into imageview
                loadImageFromStorage(newImagePath)
            }
        }

        mainCharacterViewModel.image.observe(viewLifecycleOwner, imageObserver)

        //Check whether permission is granted to access internal storage to set character image
        binding.imageView.setOnClickListener{
            requestPermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        
        //Setting up viewPager
        val numTabs = 5
        val tabTitles = listOf("Quick Stats", "Profession", "Armor", "Equipment", "Profession")

        tabAdapter = ViewPagerAdapter(childFragmentManager, lifecycle, numTabs)

        val viewPager = binding.viewPager
        viewPager.apply{adapter = tabAdapter}

        val tabs: TabLayout = binding.CharacterTabs

        TabLayoutMediator(tabs, viewPager,true) {tab, position ->
            tab.text = tabTitles[position]
        }.attach()

        return view

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadImageFromStorage(path: String) {
        try {
            val f = File(path, mainCharacterViewModel.id.value.toString() + ".jpeg")
            val b = BitmapFactory.decodeStream(FileInputStream(f))
            val img = binding.imageView
            img.setImageBitmap(b)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
    }
}