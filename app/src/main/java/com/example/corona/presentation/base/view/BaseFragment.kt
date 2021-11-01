package com.example.corona.presentation.base.view

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.example.corona.presentation.base.dumbEventBus.EventHolder
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.flow.SharedFlow

abstract class BaseFragment(
    @LayoutRes layout: Int
) : Fragment(layout), BaseView, BackListener, EventHolder {
    override val router: Router by lazy { (requireActivity() as BaseView).router }

    override val eventFlow: SharedFlow<EventHolder.Event> by lazy {
        (requireActivity() as EventHolder).eventFlow
    }

    override fun sendEvent(event: EventHolder.Event) {
        (requireActivity() as EventHolder).sendEvent(event)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
        initListeners()
    }

    override fun onBackClick() {
        router.exit()
    }

    open fun initViews() {}
    open fun initObservers() {}
    open fun initListeners() {}
}