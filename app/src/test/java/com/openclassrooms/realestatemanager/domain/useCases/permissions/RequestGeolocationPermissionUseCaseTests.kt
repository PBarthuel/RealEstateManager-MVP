package com.openclassrooms.realestatemanager.domain.useCases.permissions

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import com.openclassrooms.realestatemanager.domain.repositories.permissions.PermissionsRepository
import com.openclassrooms.realestatemanager.utils.fixtures.entities.DomainFixtures
import io.reactivex.rxjava3.core.Single
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RequestGeolocationPermissionUseCaseTests {
    private val mockPermissionsRepository: PermissionsRepository = mock()
    private val useCase = RequestGeolocationPermissionUseCase(mockPermissionsRepository)

    @Test
    fun testInvokeWithPermission() {
        val expectedPermissions = DomainFixtures.DomainMultiplePermissionsUtils.createWithGeolocation()
        whenever(mockPermissionsRepository.requestGeolocationAccessPermission()).thenReturn(Single.just(expectedPermissions))

        useCase.invoke()
            .test()
            .assertValue(expectedPermissions)

        verify(mockPermissionsRepository).requestGeolocationAccessPermission()
        verifyNoMoreInteractions(mockPermissionsRepository)
    }

    @Test
    fun testInvokeWithoutPermission() {
        val expectedPermissions = DomainFixtures.DomainMultiplePermissionsUtils.createWithoutGeolocation()
        whenever(mockPermissionsRepository.requestGeolocationAccessPermission()).thenReturn(Single.just(expectedPermissions))

        useCase.invoke()
            .test()
            .assertValue(expectedPermissions)

        verify(mockPermissionsRepository).requestGeolocationAccessPermission()
        verifyNoMoreInteractions(mockPermissionsRepository)
    }
}
