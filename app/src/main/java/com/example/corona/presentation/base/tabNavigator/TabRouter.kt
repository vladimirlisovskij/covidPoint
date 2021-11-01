package com.example.corona.presentation.base.tabNavigator

import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.FragmentScreen

class TabRouter : Router() {
    fun initTabs(screens: List<FragmentScreen>) {
        executeCommands(InitTabs(screens))
    }

    fun openTab(screen: FragmentScreen) {
        executeCommands(OpenTab(screen))
    }
}