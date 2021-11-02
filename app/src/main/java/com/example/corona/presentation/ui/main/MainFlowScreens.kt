package com.example.corona.presentation.ui.main

import com.example.corona.presentation.ui.splash.SplashFragment
import com.example.corona.presentation.ui.tabs.TabsFlowFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object MainFlowScreens {
    fun main() = FragmentScreen { TabsFlowFragment.newInstance() }
    fun splash() = FragmentScreen { SplashFragment.newInstance() }
}