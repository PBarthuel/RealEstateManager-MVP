package com.openclassrooms.realestatemanager.app.di.presenter

import com.openclassrooms.realestatemanager.presenter.modules.editRealEstate.EditRealEstatePresenter
import com.openclassrooms.realestatemanager.presenter.modules.editRealEstate.EditRealEstatePresenterImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class EditRealEstatePresenterModule {

    @Binds
    abstract fun bindEditRealEstatePresenter(editRealEstatePresenter: EditRealEstatePresenterImpl): EditRealEstatePresenter
}