package com.openclassrooms.realestatemanager.presenter.modules.main

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import com.openclassrooms.realestatemanager.domain.useCases.isEuro.CreateIsEuroUseCase
import com.openclassrooms.realestatemanager.domain.useCases.isEuro.GetIsEuroUseCase
import com.openclassrooms.realestatemanager.domain.useCases.permissions.IsGeolocationEnabledUseCase
import com.openclassrooms.realestatemanager.domain.useCases.permissions.RequestGeolocationPermissionUseCase
import com.openclassrooms.realestatemanager.utils.TestNetworkSchedulers
import com.openclassrooms.realestatemanager.utils.fixtures.entities.DomainFixtures
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainPresenterTests {
    private val mockIsGeolocationEnabled: IsGeolocationEnabledUseCase = mock()
    private val mockRequestGeolocationPermission: RequestGeolocationPermissionUseCase = mock()
    private val mockGetIsEuro: GetIsEuroUseCase = mock()
    private val mockCreateIsEuro: CreateIsEuroUseCase = mock()
    private val testNetworkSchedulers = TestNetworkSchedulers()
    private val mockView: MainView = mock()
    private val presenter = MainPresenterImpl(mockIsGeolocationEnabled, mockRequestGeolocationPermission, mockCreateIsEuro, mockGetIsEuro, testNetworkSchedulers)
    
    @Before
    fun setup() {
        presenter.attach(mockView)
    }
    
    @Test
    fun testSetupWithGeolocation() {
        whenever(mockIsGeolocationEnabled.invoke()).thenReturn(true)
        whenever(mockGetIsEuro.invoke()).thenReturn(Single.just(true))

        presenter.setup()
        
        verify(mockIsGeolocationEnabled).invoke()
        verify(mockGetIsEuro).invoke()
        verifyNoMoreInteractions(mockView, mockIsGeolocationEnabled, mockRequestGeolocationPermission)
    }
    
    @Test
    fun testSetupWithoutGeolocation() {
        val expectedDomainMultiplePermission = DomainFixtures.DomainMultiplePermissionsUtils.createWithGeolocation()
        whenever(mockIsGeolocationEnabled.invoke()).thenReturn(false)
        whenever(mockRequestGeolocationPermission.invoke()).thenReturn(Single.just(expectedDomainMultiplePermission))
        whenever(mockGetIsEuro.invoke()).thenReturn(Single.just(true))
        
        presenter.setup()
        
        verify(mockIsGeolocationEnabled).invoke()
        verify(mockRequestGeolocationPermission).invoke()
        verify(mockGetIsEuro).invoke()
        verifyNoMoreInteractions(mockView, mockIsGeolocationEnabled, mockRequestGeolocationPermission)
    }
}