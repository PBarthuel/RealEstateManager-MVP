package com.openclassrooms.realestatemanager.app.di.presenter

import com.openclassrooms.realestatemanager.presenter.modules.addressSearch.AddressSearchPresenter
import com.openclassrooms.realestatemanager.presenter.modules.addressSearch.AddressSearchPresenterImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class AddressSearchPresenterModule {

    @Binds
    abstract fun bindAddressSearchPresenter(addressSearchPresenter: AddressSearchPresenterImpl): AddressSearchPresenter
}
