package com.openclassrooms.realestatemanager.app.di.network

import android.annotation.SuppressLint
import android.app.Application
import android.location.Criteria
import android.location.Geocoder
import android.location.LocationManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import java.util.Locale

@Module
@InstallIn(ActivityComponent::class)
class GeocoderNetworkModule {

    @Provides
    fun provideCriteria(): Criteria = Criteria().apply { accuracy = Criteria.ACCURACY_FINE }

    @Provides
    fun provideFusedLocationProviderClient(context: Application): FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    @Provides
    fun provideGeocoder(context: Application): Geocoder = Geocoder(context, Locale.getDefault())

    @SuppressLint("NewApi")
    @Provides
    fun provideLocationManager(context: Application): LocationManager = context.getSystemService(LocationManager::class.java)
}
