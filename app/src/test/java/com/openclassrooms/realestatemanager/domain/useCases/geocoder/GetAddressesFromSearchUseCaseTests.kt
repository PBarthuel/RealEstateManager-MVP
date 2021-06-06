package com.openclassrooms.realestatemanager.domain.useCases.geocoder

import com.nhaarman.mockitokotlin2.any
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
class GetAddressesFromSearchUseCaseTests {
    private val mockGeocoderRepository: GeocoderRepository = mock()
    private val useCase = GetAddressesFromSearchUseCase(mockGeocoderRepository)

    @Test
    fun testInvokeWithShortQuery() {
        val expectedQuery = "query"
        useCase.invoke(expectedQuery)
            .test()
            .assertValue(listOf())
        verifyNoMoreInteractions(mockGeocoderRepository)
    }

    @Test
    fun testInvoke() {
        val expectedQuery = "query query query"
        val expectedDomainAddresses = listOf(DomainFixtures.DomainAddressUtils.create())
        whenever(mockGeocoderRepository.getListOfAddresses(any())).thenReturn(Observable.just(expectedDomainAddresses))

        useCase.invoke(expectedQuery)
            .test()
            .assertValue(expectedDomainAddresses)

        verify(mockGeocoderRepository).getListOfAddresses(expectedQuery)
        verifyNoMoreInteractions(mockGeocoderRepository)
    }
}
