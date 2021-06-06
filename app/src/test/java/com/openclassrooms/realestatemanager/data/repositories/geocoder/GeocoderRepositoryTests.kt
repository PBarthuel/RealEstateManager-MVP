package com.openclassrooms.realestatemanager.data.repositories.geocoder

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import com.openclassrooms.realestatemanager.data.vendors.geocoder.GeocoderServiceProvider
import com.openclassrooms.realestatemanager.utils.fixtures.entities.DataFixtures
import com.openclassrooms.realestatemanager.utils.fixtures.entities.DomainFixtures
import io.reactivex.rxjava3.core.Observable
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GeocoderRepositoryTests {
    private val mockGeocoderServiceProvider: GeocoderServiceProvider = mock()
    private val repository = GeocoderRepositoryImpl(mockGeocoderServiceProvider)
    
    //TODO reagrder pour les data exceptions
    
    @Test
    fun testGetListOfAddresses() {
        val expectedQuery = "query"
        val expectedAddressesResponse = listOf(DataFixtures.AddressResponseUtils.create())
        val expectedDomainAddresses = listOf(DomainFixtures.DomainAddressUtils.create())
        whenever(mockGeocoderServiceProvider.getListOfAddresses(any())).thenReturn(Observable.just(expectedAddressesResponse))

        repository.getListOfAddresses(expectedQuery)
            .test()
            .assertValue(expectedDomainAddresses)

        verify(mockGeocoderServiceProvider).getListOfAddresses(expectedQuery)
        verifyNoMoreInteractions(mockGeocoderServiceProvider)
    }

    @Test
    fun testGetUserAddress() {
        val expectedAddressesResponse = DataFixtures.AddressResponseUtils.create()
        val expectedDomainAddresses = DomainFixtures.DomainAddressUtils.create()
        whenever(mockGeocoderServiceProvider.getUserAddress()).thenReturn(Observable.just(expectedAddressesResponse))

        repository.getUserAddress()
            .test()
            .assertValue(expectedDomainAddresses)

        verify(mockGeocoderServiceProvider).getUserAddress()
        verifyNoMoreInteractions(mockGeocoderServiceProvider)
    }
}
