package com.openclassrooms.realestatemanager.app.di.presenter

import com.openclassrooms.realestatemanager.presenter.modules.createRealEstate.CreateRealEstatePresenter
import com.openclassrooms.realestatemanager.presenter.modules.createRealEstate.CreateRealEstatePresenterImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class CreateRealEstatePresenterModule {
    
    @Binds
    abstract fun bindCreateRealEstatePresenter(createRealEstatePresenter: CreateRealEstatePresenterImpl): CreateRealEstatePresenter
}