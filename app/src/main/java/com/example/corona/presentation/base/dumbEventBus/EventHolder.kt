package com.example.corona.presentation.base.dumbEventBus

import kotlinx.coroutines.flow.SharedFlow

interface EventHolder {
    val eventFlow: SharedFlow<Event>

    fun sendEvent(event: Event)

    interface Event;
}