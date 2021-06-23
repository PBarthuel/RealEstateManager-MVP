package com.openclassrooms.realestatemanager.app.modules.map

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.collection.ArrayMap
import androidx.collection.arrayMapOf
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.app.modules.main.MainActivity
import com.openclassrooms.realestatemanager.app.modules.map.adapter.CustomInfoWindowAdapter
import com.openclassrooms.realestatemanager.app.modules.searchRealEstate.SearchRealEstateActivity
import com.openclassrooms.realestatemanager.app.utils.viewBindings.activityViewBinding
import com.openclassrooms.realestatemanager.databinding.ActivityMapBinding
import com.openclassrooms.realestatemanager.presenter.models.uiAddressItem.UIAddressItem
import com.openclassrooms.realestatemanager.presenter.models.uiRealEstateMasterDetailItem.UIRealEstateMasterDetailItem
import com.openclassrooms.realestatemanager.presenter.modules.map.MapPresenter
import com.openclassrooms.realestatemanager.presenter.modules.map.MapView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MapActivity: AppCompatActivity(), MapView, OnMapReadyCallback {
    
    @Inject
    lateinit var presenter: MapPresenter
    
    private val binding by activityViewBinding(ActivityMapBinding::inflate)
    
    private var googleMap: GoogleMap? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        presenter.attach(this)
    }
    
    override fun onResume() {
        super.onResume()
        setupMap()
        presenter.setupMapMarker()
    }
    
    override fun onDestroy() {
        presenter.destroy()
        super.onDestroy()
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
        }
        presenter.setup()
    }
    
    //region MapView Callback
    override fun onSetupMapMarker(list: List<UIRealEstateMasterDetailItem>, isEuro: Boolean) {
        val allMarkersMap: ArrayMap<Marker, UIRealEstateMasterDetailItem> = arrayMapOf()
        list.forEach { uiRealEstateMasterDetailItem ->
            if (uiRealEstateMasterDetailItem.address.latitude != 0.0 && uiRealEstateMasterDetailItem.address.longitude != 0.0) {
                val marker: Marker? = googleMap?.addMarker(
                    MarkerOptions().position(
                        LatLng(
                            uiRealEstateMasterDetailItem.address.latitude,
                            uiRealEstateMasterDetailItem.address.longitude
                        )
                    ).icon(BitmapDescriptorFactory.defaultMarker())
                )
                marker?.let { it.tag = uiRealEstateMasterDetailItem }
                allMarkersMap[marker] = uiRealEstateMasterDetailItem
            }
        }
        googleMap?.setInfoWindowAdapter(CustomInfoWindowAdapter(this, isEuro))
        googleMap?.setOnInfoWindowClickListener {
            val result = MainActivity.RESULT_MAP
    
            Intent().apply { putExtra(SearchRealEstateActivity.INTENT_ID_ITEM_DATA, allMarkersMap[it]?.id) }
                    .also { intent ->
                        setResult(result, intent)
                        finish()
                    }
        }
    }
    
    override fun onShowUserPosition(item: UIAddressItem) {
        with(item) {
            LatLngBounds.builder().include(LatLng(latitude, longitude)).build().also {
                googleMap?.animateCamera(CameraUpdateFactory.newLatLngBounds(it, 1))
            }
        }
    }
    
    @SuppressLint("MissingPermission")
    override fun onEnableUserPosition() {
        googleMap?.isMyLocationEnabled = true
    }
    //endregion
    
    companion object {
        const val INTENT_ID_ITEM_DATA = "intent_id_item_data"
    }
}