package com.example.corona.presentation.base.view

import android.content.Context
import android.util.Log
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.example.corona.presentation.base.tabNavigator.BaseAnimatedNavigator
import com.github.terrakok.cicerone.Router
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

abstract class BaseFlowFragment(
    @LayoutRes layout: Int
) : BaseFragment(layout), FlowHolder {
    abstract override val router: Router

    override fun onBackClick() {
        (childFragmentManager.findFragmentById(containerId) as BackListener).onBackClick()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onOpenStartScreen()
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }


    abstract fun onOpenStartScreen()
}