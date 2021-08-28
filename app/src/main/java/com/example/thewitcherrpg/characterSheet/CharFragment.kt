package com.example.thewitcherrpg.characterSheet

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.activityViewModels
import com.example.thewitcherrpg.characterSheet.charFrags.ViewPagerAdapter
import com.example.thewitcherrpg.databinding.FragmentCharBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class CharFragment : Fragment() {
    private var _bindind: FragmentCharBinding? = null
    private val binding get() = _bindind!!
    lateinit var tabAdapter: ViewPagerAdapter

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _bindind = FragmentCharBinding.inflate(inflater, container, false)
        val view = binding.root


        if (ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(context, "Permission Granted!", Toast.LENGTH_SHORT).show()
        }
        else Toast.makeText(context, "Permission Not Granted!", Toast.LENGTH_SHORT).show()



        val numTabs = 5
        val tabTitles = listOf<String>("Character", "Stats", "Armor", "Equipment", "Profession")

        tabAdapter = ViewPagerAdapter(childFragmentManager, lifecycle, numTabs)

        val viewPager = binding.viewPager
        viewPager.apply{adapter = tabAdapter}

        val tabs: TabLayout = binding.CharacterTabs

        TabLayoutMediator(tabs, viewPager,true) {tab, position ->
            tab.text = tabTitles[position]
        }.attach()

        setData()

        return view

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bindind = null
    }

    private fun setData(){

        val name = sharedViewModel.name
        binding.etCharName.setText(name.value)
    }

    override fun onPause() {

        sharedViewModel.setName(binding.etCharName.text.toString())

        super.onPause()
    }
}