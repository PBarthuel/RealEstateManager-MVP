package com.openclassrooms.realestatemanager.domain.useCases.geocoder

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import com.openclassrooms.realestatemanager.domain.repositories.geocoder.GeocoderRepository
import com.openclassrooms.realestatemanager.utils.fixtures.entities.DomainFixtures
import io.reactivex.rxjava3.core.Observable
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetUserAddressUseCaseTests {
    private val mockGeocoderRepository: GeocoderRepository = mock()
    private val useCase = GetUserAddressUseCase(mockGeocoderRepository)

    @Test
    fun testInvoke() {
        val expectedDomainAddresses = DomainFixtures.DomainAddressUtils.create()
        whenever(mockGeocoderRepository.getUserAddress()).thenReturn(Observable.just(expectedDomainAddresses))

        useCase.invoke()
            .test()
            .assertValue(expectedDomainAddresses)

        verify(mockGeocoderRepository).getUserAddress()
        verifyNoMoreInteractions(mockGeocoderRepository)
    }
}
