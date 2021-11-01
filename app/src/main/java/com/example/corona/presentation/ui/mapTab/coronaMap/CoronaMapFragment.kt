package com.example.corona.presentation.ui.mapTab.coronaMap

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.corona.R
import com.example.corona.databinding.FragmentCoronaMapBinding
import com.example.corona.presentation.base.view.BaseFragment
import com.example.corona.presentation.model.dto.MarkerInfoUiDto
import com.example.corona.presentation.ui.dialogs.LocationInfoDialog
import com.example.corona.presentation.ui.mapTab.coronaMap.map.ClusterRenderer
import com.example.corona.presentation.ui.mapTab.coronaMap.map.CoronaMarkerItem
import com.example.corona.presentation.utils.collectOnStarted
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.ktx.awaitMap
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

class CoronaMapFragment : BaseFragment(R.layout.fragment_corona_map) {
    override val viewModel: CoronaViewModel by viewModel()

    private val binding: FragmentCoronaMapBinding by viewBinding()
    private val mapFragment: SupportMapFragment by lazy { childFragmentManager.findFragmentById(R.id.corona_map) as SupportMapFragment }
    private lateinit var map: GoogleMap
    private lateinit var clusterManager: ClusterManager<CoronaMarkerItem>
    private lateinit var clusterRenderer: ClusterRenderer

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMap()
    }

    override fun initObservers() {
        super.initObservers()

        collectOnStarted(viewModel.screenEvents) {
            onScreenEvent(it)
        }

        collectOnStarted(eventFlow) {
            when (it) {
                is MoveToLocation -> {
                    viewModel.onMoveToLocation(it.code)
                }
            }
        }
    }

    private fun onScreenEvent(event: CoronaViewModel.ScreenEvents) {
        when (event) {
            is CoronaViewModel.ScreenEvents.ShowLocationInfoDialog -> showLocationInfo(event)
            is CoronaViewModel.ScreenEvents.MoveCamera -> map.animateCamera(
                CameraUpdateFactory.newLatLngZoom(LatLng(event.lat, event.lon), 8f)
            )
        }
    }

    private fun showLocationInfo(event: CoronaViewModel.ScreenEvents.ShowLocationInfoDialog) {
        LocationInfoDialog.newInstance(event.data).show(childFragmentManager, "123")
    }


    private fun initMap() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            map = mapFragment.awaitMap()
                .apply {
                    clusterManager = ClusterManager<CoronaMarkerItem>(requireContext(), this)

                    clusterManager.setOnClusterItemClickListener {
                        viewModel.onMarkerClick(it.code)
                        true
                    }

                    clusterRenderer = ClusterRenderer(requireContext(), this, clusterManager)
                    clusterManager.renderer = clusterRenderer
                    setOnCameraIdleListener(clusterManager)
                    setOnMarkerClickListener(clusterManager)
                }

            viewModel.locations.collect {
                plotMap(it)
            }
        }
    }

    private fun plotMap(data: List<MarkerInfoUiDto>) {
        map.clear()
        clusterManager.clearItems()
        clusterRenderer.maxConfirmed = data.sumOf { country -> country.confirmed }

        clusterManager.let {
            data.forEach { location ->
                it.addItem(
                    CoronaMarkerItem(
                        location.countryCode,
                        location.confirmed,
                        LatLng(location.lat, location.lon)
                    )
                )
            }

            it.cluster()
        }
    }

    companion object {
        fun newInstance() = CoronaMapFragment()
    }
}
