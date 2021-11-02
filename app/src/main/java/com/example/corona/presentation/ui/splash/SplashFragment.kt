package com.example.corona.presentation.ui.splash

import com.example.corona.R
import com.example.corona.presentation.base.view.BaseFragment
import com.example.corona.presentation.base.view.BaseViewModel
import org.koin.android.ext.android.inject

class SplashViewModel: BaseViewModel()

class SplashFragment: BaseFragment(R.layout.fragment_splash) {
    override val viewModel: SplashViewModel by inject()

    companion object {
        fun newInstance() = SplashFragment()
    }
}