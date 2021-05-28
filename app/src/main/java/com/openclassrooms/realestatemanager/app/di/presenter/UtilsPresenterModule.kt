package com.openclassrooms.realestatemanager.app.di.presenter

import com.openclassrooms.realestatemanager.presenter.protocols.utils.NetworkSchedulers
import com.openclassrooms.realestatemanager.presenter.protocols.utils.NetworkSchedulersImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UtilsPresenterModule {
    
    @Binds
    abstract fun bindNetworkSchedulers(networkSchedulers: NetworkSchedulersImpl): NetworkSchedulers
}