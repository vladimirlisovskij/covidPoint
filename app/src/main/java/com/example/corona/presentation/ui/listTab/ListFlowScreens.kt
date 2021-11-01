package com.example.corona.presentation.ui.listTab

import com.example.corona.presentation.ui.listTab.coronaList.CoronaListFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object ListFlowScreens {
    fun listScreen() = FragmentScreen { CoronaListFragment.newInstance() }
}