package com.openclassrooms.realestatemanager.app.modules.main

import android.app.Application
import android.content.IntentFilter
import android.os.Build
import com.openclassrooms.realestatemanager.BuildConfig
import com.openclassrooms.realestatemanager.data.repositories.local.ConnectivityRepositoryImpl
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class RealEstateManagerApplication: Application() {

    @Inject
    lateinit var connectivityRepository: ConnectivityRepositoryImpl

    override fun onCreate() {
        super.onCreate()

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            registerReceiver(connectivityRepository, IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"))
            registerReceiver(connectivityRepository, IntentFilter("android.net.wifi.WIFI_STATE_CHANGED"))
        }
    }
}