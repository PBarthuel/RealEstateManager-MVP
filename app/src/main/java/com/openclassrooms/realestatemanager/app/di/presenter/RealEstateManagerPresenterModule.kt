package com.openclassrooms.realestatemanager.app.di.presenter

import com.openclassrooms.realestatemanager.presenter.modules.main.MainPresenter
import com.openclassrooms.realestatemanager.presenter.modules.main.MainPresenterImpl
import com.openclassrooms.realestatemanager.presenter.modules.main.views.RealEstateMasterDetailPresenter
import com.openclassrooms.realestatemanager.presenter.modules.main.views.RealEstateMasterDetailPresenterImpl
import com.openclassrooms.realestatemanager.presenter.modules.main.views.realEstateList.RealEstateListPresenter
import com.openclassrooms.realestatemanager.presenter.modules.main.views.realEstateList.RealEstateListPresenterImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class MainPresenterModule {

    @Binds
    abstract fun bindMainPresenter(mainPresenter: MainPresenterImpl): MainPresenter

    @Binds
    abstract fun bindRealEstateListPresenter(realEstateListPresenter: RealEstateListPresenterImpl): RealEstateListPresenter

    @Binds
    abstract fun bindRealEstateMasterDetailPresenter(realEstateMasterDetailPresenter: RealEstateMasterDetailPresenterImpl): RealEstateMasterDetailPresenter
}