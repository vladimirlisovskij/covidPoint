package com.example.corona.presentation.ui.listTab.coronaList

import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.corona.R
import com.example.corona.databinding.FragmentCoronaListBinding
import com.example.corona.presentation.base.view.BaseFragment
import com.example.corona.presentation.ui.tabs.NavigateToMapInputEvent
import com.example.corona.presentation.ui.mapTab.coronaMap.MoveToLocation
import com.example.corona.presentation.utils.collectOnStarted
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class CoronaListFragment : BaseFragment(R.layout.fragment_corona_list) {
    override val viewModel: CoronaListViewModel by viewModel()
    private val binding: FragmentCoronaListBinding by viewBinding()
    private val adapter: CoronaListAdapter by inject()

    override fun initListeners() {
        super.initListeners()
        adapter.onClickListener = viewModel::onLocationClick
    }

    override fun initViews() {
        super.initViews()
        with(binding) {
            locationStatsRecycler.adapter = adapter
        }
    }

    override fun initObservers() {
        super.initObservers()

        collectOnStarted(viewModel.locationsStats) {
            adapter.items = it
        }

        collectOnStarted(viewModel.screenEvents) {
            onScreenEvent(it)
        }
    }

    private fun onScreenEvent(event: CoronaListViewModel.ScreenEvents) {
        when (event) {
            is CoronaListViewModel.ScreenEvents.NavigateToMapScreen -> {
                sendEvent(NavigateToMapInputEvent)
                sendEvent(MoveToLocation(event.code))
            }
        }
    }

    companion object {
        fun newInstance() = CoronaListFragment()
    }
}