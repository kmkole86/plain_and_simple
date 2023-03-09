package com.kmkole86.plainandsimple.di

import javax.inject.Qualifier

@Qualifier
annotation class DefaultDispatcher

@Qualifier
annotation class IoDispatcher

@Qualifier
annotation class MainImmediateDispatcher