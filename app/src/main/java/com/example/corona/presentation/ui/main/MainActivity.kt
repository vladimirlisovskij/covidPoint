package com.example.corona.presentation.ui.main

import android.app.AlertDialog
import android.os.Bundle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.corona.R
import com.example.corona.databinding.ActivityMainBinding
import com.example.corona.presentation.base.tabNavigator.TabNavigator
import com.example.corona.presentation.base.tabNavigator.TabRouter
import com.example.corona.presentation.base.view.BaseFlowActivity
import com.example.corona.presentation.utils.Constants
import com.example.corona.presentation.utils.collectOnStarted
import com.example.corona.presentation.utils.createProgressDialog
import com.github.terrakok.cicerone.NavigatorHolder
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.getScopeId
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class MainActivity : BaseFlowActivity(R.layout.activity_main) {
    private val navigationScope =
        getKoin().createScope(getScopeId(), named(Constants.TAB_NAVIGATION))

    override val router: TabRouter by navigationScope.inject()
    override val navigatorHolder: NavigatorHolder by navigationScope.inject()
    override val navigator: TabNavigator by navigationScope.inject {
        parametersOf(
            this,
            containerId,
            supportFragmentManager
        )
    }

    override val viewModel: MainViewModel by viewModel()
    override val containerId: Int = R.id.main_flow_container

    private val binding: ActivityMainBinding by viewBinding()
    private var progressDialog: AlertDialog? = null

    override fun onOpenStartScreen() {
        router.initTabs(
            listOf(
                MainFlowScreens.mapTab(),
                MainFlowScreens.listTab()
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.onUpdate()
    }

    override fun onDestroy() {
        super.onDestroy()
        navigationScope.close()
    }

    override fun initObservers() {
        super.initObservers()

        collectOnStarted(viewModel.tab) {
            openTab(it)
        }

        collectOnStarted(viewModel.progressDialogIsVisible) {
            setDialogIsVisible(it)
        }

        collectOnStarted(eventFlow) {
            when (it) {
                NavigateToMapInputEvent -> {
                    viewModel.onMapTabClick()
                    binding.tabMap.isChecked = true
                }
            }
        }

    }

    override fun initListeners() {
        super.initListeners()
        with(binding) {
            tabList.setOnClickListener {
                viewModel.onListTabClick()
            }

            tabMap.setOnClickListener {
                viewModel.onMapTabClick()
            }
        }
    }


    private fun setDialogIsVisible(isVisible: Boolean) {
        if (isVisible) {
            progressDialog = createProgressDialog()
            progressDialog?.show()
        } else {
            progressDialog?.dismiss()
        }
    }


    private fun openTab(tab: MainViewModel.MainFlowTabs) {
        when (tab) {
            MainViewModel.MainFlowTabs.MAP_TAB -> {
                router.openTab(MainFlowScreens.mapTab())
            }
            MainViewModel.MainFlowTabs.LIST_TAB -> {
                router.openTab(MainFlowScreens.listTab())
            }
        }
    }
}