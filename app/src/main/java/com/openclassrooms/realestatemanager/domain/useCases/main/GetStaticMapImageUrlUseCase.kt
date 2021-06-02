package com.openclassrooms.realestatemanager.domain.useCases.main

import android.net.Uri
import javax.inject.Inject

class GetStaticMapImageUrlUseCase @Inject constructor() {
    
    fun invoke(latitude: Double, longitude: Double): String {
        return Uri.parse("https://maps.googleapis.com/maps/api/staticmap").buildUpon().apply {
            appendQueryParameter("key", "AIzaSyDBqXB_lCpa8qS9ZLExBkDkehjCPDFYaOY")
            appendQueryParameter("center", "$latitude,$longitude")
            appendQueryParameter("zoom", "14")
            appendQueryParameter("size", "128x128")
        }.build().toString()
    }
}