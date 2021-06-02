package com.openclassrooms.realestatemanager.app.modules.map

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.app.utils.viewBindings.activityViewBinding
import com.openclassrooms.realestatemanager.databinding.ActivityMapBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapActivity: AppCompatActivity(), OnMapReadyCallback {
    
    private val binding by activityViewBinding(ActivityMapBinding::inflate)
    
    private var googleMap: GoogleMap? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        
    }
    
    override fun onResume() {
        super.onResume()
        setupMap()
    }
    
    private fun setupMap() {
        (supportFragmentManager.findFragmentById(R.id.mapFragment) as? SupportMapFragment)?.also { mapFragment ->
            mapFragment.getMapAsync(this)
        }
    }
    
    @SuppressLint("MissingPermission")
    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        googleMap?.apply {
            setMaxZoomPreference(16.5F)
            uiSettings.isRotateGesturesEnabled = false
            uiSettings.isMyLocationButtonEnabled = true
            isMyLocationEnabled = true
        }
    }
}