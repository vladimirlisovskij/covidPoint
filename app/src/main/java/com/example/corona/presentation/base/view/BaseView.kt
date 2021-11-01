package com.example.corona.presentation.base.view

import com.github.terrakok.cicerone.Router

interface BaseView {
    val viewModel: BaseViewModel
    val router: Router
}