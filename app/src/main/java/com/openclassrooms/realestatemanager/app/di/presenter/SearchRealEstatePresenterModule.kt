package com.openclassrooms.realestatemanager.app.di.presenter

import com.openclassrooms.realestatemanager.presenter.modules.searchRealEstate.SearchRealEstatePresenter
import com.openclassrooms.realestatemanager.presenter.modules.searchRealEstate.SearchRealEstatePresenterImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class SearchRealEstatePresenterModule {
    
    @Binds
    abstract fun bindSearchRealEstatePresenter(searchRealEstatePresenter: SearchRealEstatePresenterImpl): SearchRealEstatePresenter
}