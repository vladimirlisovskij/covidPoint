package com.example.corona.presentation.di

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.example.corona.presentation.base.tabNavigator.BaseAnimatedNavigator
import com.example.corona.presentation.base.tabNavigator.TabNavigator
import com.example.corona.presentation.base.tabNavigator.TabRouter
import com.example.corona.presentation.ui.listTab.coronaList.CoronaListAdapter
import com.example.corona.presentation.ui.listTab.coronaList.CoronaListViewModel
import com.example.corona.presentation.ui.main.MainViewModel
import com.example.corona.presentation.ui.mapTab.coronaMap.CoronaViewModel
import com.example.corona.presentation.utils.Constants
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        MainViewModel(
            updateLocationsCase = get()
        )
    }
    viewModel {
        CoronaViewModel(
            getLocationsMarkersCase = get(),
            getLocationStatByCode = get()
        )
    }
    viewModel {
        CoronaListViewModel(
            getLocationsStatsCase = get()
        )
    }
}

val adapterModule = module {
    factory {
        CoronaListAdapter()
    }
}

val navigationModule = module {
    scope(named(Constants.TAB_NAVIGATION)) {
        scoped {
            TabRouter()
        }

        scoped {
            Cicerone
                .create(get<TabRouter>())
                .getNavigatorHolder()
        }

        scoped { (activity: AppCompatActivity, containerId: Int, fragmentManager: FragmentManager) ->
            TabNavigator(activity, containerId, fragmentManager)
        }
    }

    scope(named(Constants.DEFAULT_NAVIGATION)) {
        scoped {
            Router()
        }

        scoped {
            Cicerone
                .create(get<Router>())
                .getNavigatorHolder()
        }

        scoped { (activity: AppCompatActivity, containerId: Int, fragmentManager: FragmentManager) ->
            BaseAnimatedNavigator(activity, containerId, fragmentManager)
        }
    }
}


val presentationModule = viewModelModule + navigationModule + adapterModule