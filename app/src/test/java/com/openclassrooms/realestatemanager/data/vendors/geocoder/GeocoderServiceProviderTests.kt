package com.openclassrooms.realestatemanager.data.vendors.geocoder

import android.location.Address
import android.location.Criteria
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import com.openclassrooms.realestatemanager.data.models.entities.addressSearch.AddressResponse
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GeocoderServiceProviderTests {
    private val mockGeocoder: Geocoder = mock()
    private val mockCriteria: Criteria = mock()
    private val mockLocationManager: LocationManager = mock()
    private val mockFusedLocationProviderClient: FusedLocationProviderClient = mock()
    private val serviceProvider = GeocoderServiceProvider(mockGeocoder, mockCriteria, mockLocationManager, mockFusedLocationProviderClient)

    private val expectedQuery = "query"
    private val expectedMaxQuery = 10
    private val expectedCountryName = "countryName"
    private val expectedPostalCode = "postalCode"
    private val expectedSubThoroughfare = "subThoroughfare"
    private val expectedThoroughfare = "thoroughfare"
    private val expectedLocality = "locality"
    private val expectedLatitude = 1.0
    private val expectedLongitude = 2.0
    private val mockLocation: Location = mock()
    private val mockAddress: Address = mock()
    private val expectedAddressResponse = AddressResponse(
        expectedCountryName,
        expectedLocality,
        "$expectedSubThoroughfare $expectedThoroughfare",
        expectedPostalCode,
        expectedLatitude,
        expectedLongitude
    )

    @Before
    fun setup() {
        whenever(mockAddress.subThoroughfare).thenReturn(expectedSubThoroughfare)
        whenever(mockAddress.thoroughfare).thenReturn(expectedThoroughfare)
        whenever(mockAddress.countryName).thenReturn(expectedCountryName)
        whenever(mockAddress.postalCode).thenReturn(expectedPostalCode)
        whenever(mockAddress.locality).thenReturn(expectedLocality)
        whenever(mockAddress.latitude).thenReturn(expectedLatitude)
        whenever(mockAddress.longitude).thenReturn(expectedLongitude)
        whenever(mockLocation.latitude).thenReturn(expectedLatitude)
        whenever(mockLocation.longitude).thenReturn(expectedLongitude)
    }

    @Test
    fun testGetListOfAddresses() {
        whenever(mockGeocoder.getFromLocationName(any(), any())).thenReturn(listOf(mockAddress))

        serviceProvider.getListOfAddresses(expectedQuery)
            .test()
            .assertValue(listOf(expectedAddressResponse))
    
        verify(mockAddress).subThoroughfare
        verify(mockAddress).thoroughfare
        verify(mockAddress).countryName
        verify(mockAddress).postalCode
        verify(mockAddress).locality
        verify(mockAddress).latitude
        verify(mockAddress).longitude
        verify(mockGeocoder).getFromLocationName(expectedQuery, expectedMaxQuery)
        verifyNoMoreInteractions(mockGeocoder, mockAddress, mockFusedLocationProviderClient)
    }
    
    @Test
    fun testGetListOfAddressesWithNoThoroughfareAndSubThoroughfare() {
        val expectedAddressResponseBis = AddressResponse(
                expectedCountryName,
                expectedLocality,
                "featureName",
                expectedPostalCode,
                expectedLatitude,
                expectedLongitude
        )
        whenever(mockAddress.subThoroughfare).thenReturn(null)
        whenever(mockAddress.thoroughfare).thenReturn(null)
        whenever(mockAddress.featureName).thenReturn("featureName")
        whenever(mockGeocoder.getFromLocationName(any(), any())).thenReturn(listOf(mockAddress))
        
        serviceProvider.getListOfAddresses(expectedQuery)
                .test()
                .assertValue(listOf(expectedAddressResponseBis))
        
        verify(mockAddress).subThoroughfare
        verify(mockAddress).thoroughfare
        verify(mockAddress).featureName
        verify(mockAddress).countryName
        verify(mockAddress).postalCode
        verify(mockAddress).locality
        verify(mockAddress).latitude
        verify(mockAddress).longitude
        verify(mockGeocoder).getFromLocationName(expectedQuery, expectedMaxQuery)
        verifyNoMoreInteractions(mockGeocoder, mockAddress, mockFusedLocationProviderClient)
    }

    @Test
    fun testGetUserAddressesUsingLocationManager() {
        val expectedProvider = "provider"
        whenever(mockLocationManager.getBestProvider(any(), any())).thenReturn(expectedProvider)
        whenever(mockLocationManager.getLastKnownLocation(any())).thenReturn(mockLocation)
        whenever(mockGeocoder.getFromLocation(any(), any(), any())).thenReturn(listOf(mockAddress))

        serviceProvider.getUserAddress()
            .test()
            .assertValue(expectedAddressResponse)
    
        verify(mockAddress).subThoroughfare
        verify(mockAddress).thoroughfare
        verify(mockAddress).countryName
        verify(mockAddress).postalCode
        verify(mockAddress).locality
        verify(mockAddress).latitude
        verify(mockAddress).longitude
        verify(mockLocation).latitude
        verify(mockLocation).longitude
        verify(mockLocationManager).getLastKnownLocation(expectedProvider)
        verify(mockGeocoder).getFromLocation(expectedLatitude, expectedLongitude, 1)
        verifyNoMoreInteractions(mockGeocoder, mockAddress, mockFusedLocationProviderClient)
    }

    @Test
    fun testGetUserAddressesUsingFusedLocationProvider() {
        val mockTaskLocation: Task<Location> = mock()
        val successCaptor = argumentCaptor<OnSuccessListener<Location>>()
        val failureCaptor = argumentCaptor<OnFailureListener>()
        whenever(mockLocationManager.getBestProvider(any(), any())).thenReturn(null)
        whenever(mockFusedLocationProviderClient.lastLocation).thenReturn(mockTaskLocation)
        whenever(mockTaskLocation.addOnSuccessListener(any())).thenReturn(mockTaskLocation)
        whenever(mockTaskLocation.addOnFailureListener(any())).thenReturn(mockTaskLocation)
        whenever(mockGeocoder.getFromLocation(any(), any(), any())).thenReturn(listOf(mockAddress))

        val observer = serviceProvider.getUserAddress()
            .test()

        verify(mockTaskLocation).addOnSuccessListener(successCaptor.capture())
        verify(mockTaskLocation).addOnFailureListener(failureCaptor.capture())
        successCaptor.firstValue.onSuccess(mockLocation)

        observer.assertValue(expectedAddressResponse)
    
        verify(mockAddress).subThoroughfare
        verify(mockAddress).thoroughfare
        verify(mockAddress).countryName
        verify(mockAddress).postalCode
        verify(mockAddress).locality
        verify(mockAddress).latitude
        verify(mockAddress).longitude
        verify(mockLocation).latitude
        verify(mockLocation).longitude
        verify(mockFusedLocationProviderClient).lastLocation
        verify(mockGeocoder).getFromLocation(expectedLatitude, expectedLongitude, 1)
        verifyNoMoreInteractions(mockGeocoder, mockAddress, mockFusedLocationProviderClient)
    }
}
