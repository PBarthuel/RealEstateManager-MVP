package com.openclassrooms.realestatemanager.app.di.presenter

import com.openclassrooms.realestatemanager.presenter.modules.settings.SettingsPresenter
import com.openclassrooms.realestatemanager.presenter.modules.settings.SettingsPresenterImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class SettingsPresenterModule {
    
    @Binds
    abstract fun bindSettingsPresenter(settingsPresenter: SettingsPresenterImpl): SettingsPresenter
}