package com.example.corona.presentation.base.tabNavigator

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.github.terrakok.cicerone.Command

class TabNavigator(
    activity: AppCompatActivity,
    @IdRes containerId: Int,
    fragmentManager: FragmentManager,
    fragmentFactory: FragmentFactory = fragmentManager.fragmentFactory
) : BaseAnimatedNavigator(activity, containerId, fragmentManager, fragmentFactory) {

    private var tabs: List<Fragment> = listOf()

    override fun applyCommand(command: Command) {
        when (command) {
            is OpenTab -> {
                fragmentManager.commit {
                    fragmentManager.findFragmentByTag(command.screen.screenKey)?.let { tab ->
                        setupFragmentTransaction(
                            command.screen,
                            this,
                            fragmentManager.findFragmentById(containerId),
                            tab
                        )
                        fragmentManager.fragments.forEach {
                            hide(it)
                        }
                        show(tab)
                    }
                }
            }
            is InitTabs -> {
                fragmentManager.commit {
                    tabs.forEach { remove(it) }
                    tabs = command.screens.map {
                        it.createFragment(fragmentFactory).also { fragment ->
                            add(containerId, fragment, it.screenKey)
                        }
                    }
                }
            }
            else -> super.applyCommand(command)
        }
    }
}