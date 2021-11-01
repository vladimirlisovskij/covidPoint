package com.example.corona.presentation.base.tabNavigator

import com.github.terrakok.cicerone.Command
import com.github.terrakok.cicerone.androidx.FragmentScreen

data class InitTabs(val screens: List<FragmentScreen>) : Command

data class OpenTab(val screen: FragmentScreen): Command