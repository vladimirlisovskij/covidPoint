package com.example.corona.presentation.ui.mapTab

import com.example.corona.presentation.ui.mapTab.coronaMap.CoronaMapFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object MapFlowScreens {
    fun mapScreen() = FragmentScreen { CoronaMapFragment.newInstance() }
}