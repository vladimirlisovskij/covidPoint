package com.example.corona.presentation.ui.main

import android.os.Bundle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.corona.R
import com.example.corona.databinding.ActivityMainBinding
import com.example.corona.presentation.base.tabNavigator.BaseAnimatedNavigator
import com.example.corona.presentation.base.tabNavigator.TabNavigator
import com.example.corona.presentation.base.tabNavigator.TabRouter
import com.example.corona.presentation.base.view.BaseFlowActivity
import com.example.corona.presentation.utils.Constants
import com.example.corona.presentation.utils.collectOnStarted
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.getScopeId
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class MainActivity : BaseFlowActivity(R.layout.activity_main) {
    private val navigationScope =
        getKoin().createScope(getScopeId(), named(Constants.DEFAULT_NAVIGATION))

    override val router: Router by navigationScope.inject()
    override val navigatorHolder: NavigatorHolder by navigationScope.inject()
    override val navigator: BaseAnimatedNavigator by navigationScope.inject {
        parametersOf(
            this,
            containerId,
            supportFragmentManager
        )
    }

    override val viewModel: MainViewModel by viewModel()
    override val containerId: Int = R.id.main_flow_container

    private val binding: ActivityMainBinding by viewBinding()

    override fun onOpenStartScreen() {
        router.newRootScreen(MainFlowScreens.splash())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
        navigationScope.close()
    }

    override fun initObservers() {
        super.initObservers()

        collectOnStarted(viewModel.screenEvents) {
            when (it) {
                MainViewModel.ScreenEvents.OpenMainFlowScreen -> router.newRootScreen(
                    MainFlowScreens.main()
                )
            }
        }

    }
}