package com.openclassrooms.realestatemanager.presenter.modules.addressSearch

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import com.openclassrooms.realestatemanager.domain.useCases.geocoder.GetAddressesFromSearchUseCase
import com.openclassrooms.realestatemanager.domain.useCases.geocoder.GetUserAddressUseCase
import com.openclassrooms.realestatemanager.domain.useCases.permissions.IsGeolocationEnabledUseCase
import com.openclassrooms.realestatemanager.domain.useCases.permissions.RequestGeolocationPermissionUseCase
import com.openclassrooms.realestatemanager.utils.TestNetworkSchedulers
import com.openclassrooms.realestatemanager.utils.fixtures.entities.DomainFixtures
import com.openclassrooms.realestatemanager.utils.fixtures.entities.PresenterFixtures
import io.reactivex.rxjava3.core.Observable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AddressSearchPresenterTests {
    private val mockGetAddressesFromSearch: GetAddressesFromSearchUseCase = mock()
    private val mockIsGeolocationEnabled: IsGeolocationEnabledUseCase = mock()
    private val mockRequestGeolocationPermission: RequestGeolocationPermissionUseCase = mock()
    private val mockGetUserAddress: GetUserAddressUseCase = mock()
    private val testNetworkSchedulers = TestNetworkSchedulers()
    private val mockView: AddressSearchView = mock()
    private val presenter = AddressSearchPresenterImpl(mockGetAddressesFromSearch, mockIsGeolocationEnabled, mockRequestGeolocationPermission, mockGetUserAddress, testNetworkSchedulers)
    
    @Before
    fun setup() {
        presenter.attach(mockView)
    }
    
    @Test
    fun testSearchAddresses() {
        val expectedQuery = "query"
        val expectedDomainAddresses = listOf(DomainFixtures.DomainAddressUtils.create())
        val expectedUISearchAddresses = listOf(PresenterFixtures.UIAddressItemUtils.create())
        whenever(mockGetAddressesFromSearch.invoke(any())).thenReturn(Observable.just(expectedDomainAddresses))
    
        presenter.searchAddresses(expectedQuery)
    
        verify(mockView).onShowAddressesList(expectedUISearchAddresses)
        verify(mockGetAddressesFromSearch).invoke(expectedQuery)
        verifyNoMoreInteractions(mockView, mockGetAddressesFromSearch, mockIsGeolocationEnabled, mockRequestGeolocationPermission, mockGetUserAddress)
    }
    
    @Test
    fun testSearchAddressesWithEmptyResult() {
        val expectedQuery = "query"
        whenever(mockGetAddressesFromSearch.invoke(any())).thenReturn(Observable.just(listOf()))
        
        presenter.searchAddresses(expectedQuery)
        
        verify(mockView).onShowEmptyAddresses()
        verify(mockGetAddressesFromSearch).invoke(expectedQuery)
        verifyNoMoreInteractions(mockView, mockGetAddressesFromSearch, mockIsGeolocationEnabled, mockRequestGeolocationPermission, mockGetUserAddress)
    }
    
    @Test
    fun testDidSelectUserLocationWithPermission() {
        val expectedDomainAddresses = DomainFixtures.DomainAddressUtils.create()
        val expectedUISearchAddresses = PresenterFixtures.UIAddressItemUtils.create()
        whenever(mockGetUserAddress.invoke()).thenReturn(Observable.just(expectedDomainAddresses))
        whenever(mockIsGeolocationEnabled.invoke()).thenReturn(true)
        
        presenter.didSelectUserLocation()
        
        verify(mockView).onReceiveUserAddress(expectedUISearchAddresses)
        verify(mockGetUserAddress).invoke()
        verify(mockIsGeolocationEnabled).invoke()
        verifyNoMoreInteractions(mockView, mockGetAddressesFromSearch, mockIsGeolocationEnabled, mockRequestGeolocationPermission, mockGetUserAddress)
    }
    
    @Test
    fun testDidSelectUserLocationWithoutPermission() {
        whenever(mockIsGeolocationEnabled.invoke()).thenReturn(false)
        
        presenter.didSelectUserLocation()
        
        verify(mockView).onShowMissingPermission()
        verify(mockIsGeolocationEnabled).invoke()
        verifyNoMoreInteractions(mockView, mockGetAddressesFromSearch, mockIsGeolocationEnabled, mockRequestGeolocationPermission, mockGetUserAddress)
    }
}