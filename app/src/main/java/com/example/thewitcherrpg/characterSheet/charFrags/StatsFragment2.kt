package com.example.thewitcherrpg.characterSheet.charFrags

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.thewitcherrpg.databinding.FragmentStats2Binding

class StatsFragment2 : Fragment() {
    private var _bindind: FragmentStats2Binding? = null
    private val binding get() = _bindind!!

    private var focusedView: EditText? = null

    var editViews = mutableListOf<EditText>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _bindind = FragmentStats2Binding.inflate(inflater, container, false)
        val view = binding.root

        val etInt = binding.editInt
        etInt.setRawInputType(0)
        editViews.add(etInt)

        val etStun = binding.editStun
        etStun.setRawInputType(0)
        editViews.add(etStun)

        val etRef = binding.editRef
        etRef.setRawInputType(0)
        editViews.add(etRef)

        val etRun = binding.editRun
        etRun.setRawInputType(0)
        editViews.add(etRun)

        val etDex = binding.editDex
        etDex.setRawInputType(0)
        editViews.add(etDex)

        val etLeap = binding.editLeap
        etLeap.setRawInputType(0)
        editViews.add(etLeap)

        val etBody = binding.editBody
        etBody.setRawInputType(0)
        editViews.add(etBody)

        val etHP = binding.editHP
        etHP.setRawInputType(0)
        editViews.add(etHP)

        val etSpd = binding.editSpd
        etSpd.setRawInputType(0)
        editViews.add(etSpd)

        val etSta = binding.editSta
        etSta.setRawInputType(0)
        editViews.add(etSta)

        val etEmp = binding.editEmp
        etEmp.setRawInputType(0)
        editViews.add(etEmp)

        val etEnc = binding.editEnc
        etEnc.setRawInputType(0)
        editViews.add(etEnc)

        val etCra = binding.editCra
        etCra.setRawInputType(0)
        editViews.add(etCra)

        val etRec = binding.editRec
        etRec.setRawInputType(0)
        editViews.add(etRec)

        val etWill = binding.editWill
        etWill.setRawInputType(0)
        editViews.add(etWill)

        val etPunch = binding.editPunch
        etPunch.setRawInputType(0)
        editViews.add(etPunch)

        val etLuck = binding.editLuck
        etLuck.setRawInputType(0)
        editViews.add(etLuck)

        val etKick = binding.editKick
        etKick.setRawInputType(0)
        editViews.add(etKick)

        binding.buttonPlus.setOnClickListener{
            for (i in editViews)
            {
                onFocus(i)
            }
            increaseButton()
        }

        binding.buttonMinus.setOnClickListener{
            for (i in editViews)
            {
                onFocus(i)
            }
            decreaseButton()
        }


        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bindind = null

    }



    private fun onFocus(v: EditText){
        if (v.hasFocus()) {focusedView = v}
    }


    private fun increaseButton(){
        if (focusedView != null) {
            var count = focusedView?.text.toString().toInt()
            count++

            focusedView?.setText(count.toString())
        }
    }

    private fun decreaseButton(){
        if (focusedView != null) {
            var count = focusedView?.text.toString().toInt()
            if (count > 0) {
                count--

                focusedView?.setText(count.toString())
            }
        }
    }

    override fun onPause() {
        super.onPause()
        focusedView = null
    }
}