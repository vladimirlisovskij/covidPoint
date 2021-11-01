package com.example.corona.presentation.ui.mapTab.coronaMap.map

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

class CoronaMarkerItem(
    val code: String,
    val confirmed: Int,
    private val pos: LatLng,
) : ClusterItem {
    override fun getPosition() = pos

    override fun getTitle(): String = "$confirmed"

    override fun getSnippet(): String? = null
}