package com.example.corona.presentation.ui.splash

import android.content.Context
import android.content.Intent
import com.example.corona.presentation.ui.main.MainActivity
import com.github.terrakok.cicerone.androidx.ActivityScreen
import com.github.terrakok.cicerone.androidx.FragmentScreen

object SplashFlowScreens {
    fun splash() = FragmentScreen { SplashFragment.newInstance() }

    fun mainScreen(context: Context) = ActivityScreen { Intent(context, MainActivity::class.java) }
}