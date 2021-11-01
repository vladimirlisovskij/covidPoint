package com.example.corona.presentation.base.view

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.corona.presentation.base.dumbEventBus.EventHolder
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

abstract class BaseFlowActivity(
    @LayoutRes layout: Int
) : AppCompatActivity(layout), BaseView, EventHolder, FlowHolder {
    private val _eventFlow = MutableSharedFlow<EventHolder.Event>()
    override val eventFlow = _eventFlow.asSharedFlow()

    override fun sendEvent(event: EventHolder.Event) {
        lifecycleScope.launch {
            _eventFlow.emit(event)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        initObservers()
        initListeners()
        onOpenStartScreen()
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onBackPressed() {
        (supportFragmentManager.findFragmentById(containerId) as? BackListener)?.onBackClick()
            ?: super.onBackPressed()
    }

    open fun initViews() {}
    open fun initObservers() {}
    open fun initListeners() {}
    abstract fun onOpenStartScreen()
}

