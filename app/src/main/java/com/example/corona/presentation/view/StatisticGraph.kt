package com.example.corona.presentation.view

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.example.corona.R

class StatisticGraph(
    context: Context,
    attributes: AttributeSet? = null
) : LinearLayout(context, attributes) {
    var data = listOf<Int>()
        set(value) {
            field = value
            setGraphsHeight()
        }

    init {
        orientation = HORIZONTAL
        showDividers = SHOW_DIVIDER_MIDDLE
        dividerDrawable = ContextCompat.getDrawable(context, R.drawable.divider_graph)
    }

    private fun setGraphsHeight() {
        removeAllViews()
        val max = data.maxOf { it }.toDouble()
        weightSum = data.size.toFloat()

        data.forEachIndexed { index, data ->
            addView(View(context).apply {
                setBackgroundColor(ContextCompat.getColor(context, R.color.graph))
                layoutParams = LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.MATCH_PARENT,
                    1.0f
                ).apply {
                    this.height = (this@StatisticGraph.minimumHeight * data / max).toInt()
                }
                gravity = Gravity.BOTTOM
            })
        }
    }
}