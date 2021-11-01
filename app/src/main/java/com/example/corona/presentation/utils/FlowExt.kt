package com.example.corona.presentation.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

inline fun <T> LifecycleCoroutineScope.collectOnStarted(
    flow: Flow<T>,
    crossinline action: suspend (value: T) -> Unit
): Job =
    launchWhenStarted {
        flow.collect(action)
    }


// todo
inline fun <T> AppCompatActivity.collectOnStarted(
    flow: Flow<T>,
    crossinline action: suspend (value: T) -> Unit
): Job = lifecycleScope.collectOnStarted(flow, action)

inline fun <T> Fragment.collectOnStarted(
    flow: Flow<T>,
    crossinline action: suspend (value: T) -> Unit
): Job = viewLifecycleOwner.lifecycleScope.collectOnStarted(flow, action)