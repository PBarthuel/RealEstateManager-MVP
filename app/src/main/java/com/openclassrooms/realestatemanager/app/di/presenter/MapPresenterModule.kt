package com.openclassrooms.realestatemanager.app.di.presenter

import com.openclassrooms.realestatemanager.presenter.modules.map.MapPresenter
import com.openclassrooms.realestatemanager.presenter.modules.map.MapPresenterImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class MapPresenterModule {
    
    @Binds
    abstract fun bindMapPresenter(mapPresenter: MapPresenterImpl): MapPresenter
}
