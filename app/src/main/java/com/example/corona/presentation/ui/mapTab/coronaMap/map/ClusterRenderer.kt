package com.example.corona.presentation.ui.mapTab.coronaMap.map

import android.content.Context
import android.graphics.Color
import com.example.corona.R
import com.example.corona.presentation.utils.createRoundBitmap
import com.example.corona.presentation.utils.getBitmapFromVectorDrawable
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer

class ClusterRenderer(
    private val context: Context,
    map: GoogleMap,
    clusterManager: ClusterManager<CoronaMarkerItem>
) : DefaultClusterRenderer<CoronaMarkerItem>(context, map, clusterManager) {
    var maxConfirmed: Int = 0

    override fun onBeforeClusterRendered(
        cluster: Cluster<CoronaMarkerItem>,
        markerOptions: MarkerOptions
    ) {
        val confirmed = cluster.items.sumOf { it.confirmed }
        markerOptions.apply {
            position(cluster.position)
            icon(
                BitmapDescriptorFactory.fromBitmap(
                    context.createRoundBitmap(
                        100,
                        calculateRGB(confirmed),
                        28f,
                        "${confirmed / 1000}к",
                        Color.BLACK
                    )
                )

            )
        }
    }

    override fun onBeforeClusterItemRendered(item: CoronaMarkerItem, markerOptions: MarkerOptions) {
        markerOptions.apply {
            position(item.position)
            icon(
                BitmapDescriptorFactory.fromBitmap(
                    context.getBitmapFromVectorDrawable(
                        R.drawable.ic_marker,
                        calculateRGB(item.confirmed)
                    )
                )

            )
        }
    }

    private fun calculateRGB(confirmed: Int): Int {
        val redColor = (255 * (confirmed / maxConfirmed.toDouble())).toInt()
        val greenColor = 255 - redColor
        return Color.rgb(redColor, greenColor, 0)
    }
}