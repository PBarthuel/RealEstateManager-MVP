package com.openclassrooms.realestatemanager.app.di.repositories

import com.openclassrooms.realestatemanager.data.repositories.geocoder.GeocoderRepositoryImpl
import com.openclassrooms.realestatemanager.domain.repositories.geocoder.GeocoderRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class GeocoderRepositoryModule {

    @Binds
    abstract fun bindGeocoderRepository(geocoderRepository: GeocoderRepositoryImpl): GeocoderRepository
}
