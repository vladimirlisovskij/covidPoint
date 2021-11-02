package com.example.corona.presentation.ui.tabs

import com.example.corona.presentation.ui.listTab.ListTabFlowFragment
import com.example.corona.presentation.ui.listTab.coronaList.CoronaListFragment
import com.example.corona.presentation.ui.mapTab.MapTabFlowFragment
import com.example.corona.presentation.ui.mapTab.coronaMap.CoronaMapFragment
import com.example.corona.presentation.ui.splash.SplashFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object TabsFlowScreens {
    fun mapTab() = FragmentScreen { MapTabFlowFragment.newInstance() }
    fun listTab() = FragmentScreen { ListTabFlowFragment.newInstance() }
}