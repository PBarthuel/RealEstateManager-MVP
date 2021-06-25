package com.openclassrooms.realestatemanager.data.repositories.local

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import com.openclassrooms.realestatemanager.app.utils.Utils
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConnectivityRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
): BroadcastReceiver() {
    val isConnectedPublishSubject = PublishSubject.create<Boolean>()

    init {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        connectivityManager?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                it.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
                    override fun onAvailable(network: Network) {
                        isConnectedPublishSubject.onNext(true)
                    }
                    override fun onLost(network: Network) {
                        isConnectedPublishSubject.onNext(false)
                    }
                })
            }
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        isConnectedPublishSubject.onNext(Utils.isInternetAvailable(context))
    }
}