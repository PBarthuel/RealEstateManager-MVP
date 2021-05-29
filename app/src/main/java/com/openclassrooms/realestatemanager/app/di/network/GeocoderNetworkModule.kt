package com.openclassrooms.realestatemanager.app.di.network

import android.app.Application
import android.location.Geocoder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import java.util.Locale

@Module
@InstallIn(ActivityComponent::class)
class GeocoderNetworkModule {

    @Provides
    fun provideGeocoder(context: Application): Geocoder = Geocoder(context, Locale.getDefault())
}
