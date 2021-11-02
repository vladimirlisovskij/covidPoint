package com.example.corona.presentation.utils

import android.view.View

fun View.animateGone() {
    animate()
        .setDuration(750)
        .alpha(0f)

    visibility = View.GONE
}

fun View.animateVisible() {
    visibility = View.VISIBLE

    animate()
        .setDuration(750)
        .alpha(1f)
}