package com.example.corona.presentation.ui.listTab

import androidx.appcompat.app.AppCompatActivity
import com.example.corona.R
import com.example.corona.presentation.base.tabNavigator.BaseAnimatedNavigator
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

class ListTabFlowFragment : BaseFlowFragment(R.layout.fragment_list_tab_flow) {
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

    override val containerId: Int = R.id.list_tab_flow_container
    override val viewModel: ListTabFlowViewModel by viewModel()

    override fun onDestroy() {
        super.onDestroy()
        navigationScope.close()
    }

    override fun onOpenStartScreen() {
        router.newRootScreen(ListFlowScreens.listScreen())
    }

    companion object {
        fun newInstance() = ListTabFlowFragment()
    }
}

