package com.example.corona.presentation.base.view

import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator

interface FlowHolder {
    val navigatorHolder: NavigatorHolder
    val navigator: AppNavigator
    val containerId: Int
}