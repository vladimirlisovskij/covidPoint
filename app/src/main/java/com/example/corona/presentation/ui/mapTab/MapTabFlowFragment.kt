package com.example.corona.presentation.ui.mapTab

import androidx.appcompat.app.AppCompatActivity
import com.example.corona.R
import com.example.corona.presentation.base.tabNavigator.BaseAnimatedNavigator
import com.example.corona.presentation.base.tabNavigator.TabRouter
import com.example.corona.presentation.base.view.BaseFlowFragment
import com.example.corona.presentation.utils.Constants
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.getScopeId
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class MapTabFlowFragment : BaseFlowFragment(R.layout.fragment_map_tab_flow) {
    private val navigationScope =
        getKoin().createScope(getScopeId(), named(Constants.DEFAULT_NAVIGATION))

    override val navigatorHolder: NavigatorHolder by navigationScope.inject()
    override val router: Router by navigationScope.inject()
    override val navigator: BaseAnimatedNavigator by navigationScope.inject {
        parametersOf(
            requireActivity() as AppCompatActivity,
            containerId,
            childFragmentManager
        )
    }

    override val containerId: Int = R.id.map_tab_flow_container
    override val viewModel: MapTabFlowViewModel by viewModel()

    override fun onOpenStartScreen() {
        router.newRootScreen(MapFlowScreens.mapScreen())
    }

    override fun onDestroy() {
        super.onDestroy()
        navigationScope.close()
    }

    companion object {
        fun newInstance() = MapTabFlowFragment()
    }
}