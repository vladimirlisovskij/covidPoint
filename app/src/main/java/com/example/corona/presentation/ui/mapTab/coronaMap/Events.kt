package com.example.corona.presentation.ui.mapTab.coronaMap

import com.example.corona.presentation.base.dumbEventBus.EventHolder

data class MoveToLocation(
    val code: String
): EventHolder.Event