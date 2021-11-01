package com.example.corona.application.di

import com.example.corona.data.di.dataModule
import com.example.corona.domain.di.domainModule
import com.example.corona.presentation.di.presentationModule

val applicationModule = dataModule + domainModule + presentationModule