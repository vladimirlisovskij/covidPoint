package com.example.corona.presentation.ui.tabs

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.corona.R
import com.example.corona.databinding.FragmentMainBinding
import com.example.corona.presentation.base.tabNavigator.TabNavigator
import com.example.corona.presentation.base.tabNavigator.TabRouter
import com.example.corona.presentation.base.view.BaseFlowFragment
import com.example.corona.presentation.utils.Constants
import com.example.corona.presentation.utils.collectOnStarted
import com.github.terrakok.cicerone.NavigatorHolder
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.getScopeId
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class TabsFlowFragment : BaseFlowFragment(R.layout.fragment_main) {
    private val navigationScope =
        getKoin().createScope(getScopeId(), named(Constants.TAB_NAVIGATION))

    override val router: TabRouter by navigationScope.inject()
    override val navigatorHolder: NavigatorHolder by navigationScope.inject()
    override val navigator: TabNavigator by navigationScope.inject {
        parametersOf(
            requireActivity() as AppCompatActivity,
            containerId,
            childFragmentManager
        )
    }
    override val containerId: Int = R.id.tabs_flow_container
    override val viewModel: TabsFlowViewModel by viewModel()

    private val binding: FragmentMainBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onUpdate()
    }

    override fun onOpenStartScreen() {
        router.initTabs(
            listOf(
                TabsFlowScreens.mapTab(),
                TabsFlowScreens.listTab()
            )
        )
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


    private fun openTab(tab: TabsFlowViewModel.MainFlowTabs) {
        when (tab) {
            TabsFlowViewModel.MainFlowTabs.MAP_TAB -> {
                router.openTab(TabsFlowScreens.mapTab())
            }
            TabsFlowViewModel.MainFlowTabs.LIST_TAB -> {
                router.openTab(TabsFlowScreens.listTab())
            }
        }
    }

    companion object {
        fun newInstance() = TabsFlowFragment()
    }
}