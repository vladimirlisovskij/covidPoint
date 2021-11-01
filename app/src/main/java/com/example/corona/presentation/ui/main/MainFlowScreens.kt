package com.example.corona.presentation.ui.main

import com.example.corona.presentation.ui.listTab.ListTabFlowFragment
import com.example.corona.presentation.ui.listTab.coronaList.CoronaListFragment
import com.example.corona.presentation.ui.mapTab.MapTabFlowFragment
import com.example.corona.presentation.ui.mapTab.coronaMap.CoronaMapFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object MainFlowScreens {
    fun mapTab() = FragmentScreen { MapTabFlowFragment.newInstance() }
    fun listTab() = FragmentScreen { ListTabFlowFragment.newInstance() }
}