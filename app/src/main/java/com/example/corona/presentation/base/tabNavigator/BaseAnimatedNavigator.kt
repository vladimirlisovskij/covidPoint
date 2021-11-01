package com.example.corona.presentation.base.tabNavigator

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.*
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.FragmentScreen

open class BaseAnimatedNavigator(
    activity: AppCompatActivity,
    @IdRes containerId: Int,
    fragmentManager: FragmentManager,
    fragmentFactory: FragmentFactory = fragmentManager.fragmentFactory
) : AppNavigator(activity, containerId, fragmentManager, fragmentFactory) {
    override fun setupFragmentTransaction(
        screen: FragmentScreen,
        fragmentTransaction: FragmentTransaction,
        currentFragment: Fragment?,
        nextFragment: Fragment
    ) {
        fragmentTransaction.setCustomAnimations(
            android.R.animator.fade_in, android.R.animator.fade_out,
            android.R.animator.fade_in, android.R.animator.fade_out
        )
    }
}