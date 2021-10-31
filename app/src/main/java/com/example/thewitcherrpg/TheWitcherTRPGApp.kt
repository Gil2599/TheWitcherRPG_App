package com.example.thewitcherrpg

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TheWitcherTRPGApp : Application() {

    companion object {
        private var mContext: Application? = null

        fun getContext(): Context? {
            return mContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        mContext = this
    }


}