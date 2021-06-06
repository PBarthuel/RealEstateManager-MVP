package com.openclassrooms.realestatemanager.domain.useCases.permissions

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import com.openclassrooms.realestatemanager.domain.repositories.permissions.PermissionsRepository
import com.openclassrooms.realestatemanager.utils.fixtures.entities.DomainFixtures
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class IsGeolocationEnabledUseCaseTests {
    private val mockPermissionsRepository: PermissionsRepository = mock()
    private val useCase = IsGeolocationEnabledUseCase(mockPermissionsRepository)

    @Test
    fun testInvokeWithPermissions() {
        whenever(mockPermissionsRepository.getGeolocationAccessPermissionStatus()).thenReturn(
            DomainFixtures.DomainMultiplePermissionsUtils.createWithGeolocation())

        val result = useCase.invoke()

        assertThat(result, equalTo(true))
        verify(mockPermissionsRepository).getGeolocationAccessPermissionStatus()
        verifyNoMoreInteractions(mockPermissionsRepository)
    }

    @Test
    fun testInvokeWithoutPermissions() {
        whenever(mockPermissionsRepository.getGeolocationAccessPermissionStatus()).thenReturn(
            DomainFixtures.DomainMultiplePermissionsUtils.createEmpty())

        val result = useCase.invoke()

        assertThat(result, equalTo(false))
        verify(mockPermissionsRepository).getGeolocationAccessPermissionStatus()
        verifyNoMoreInteractions(mockPermissionsRepository)
    }
}
