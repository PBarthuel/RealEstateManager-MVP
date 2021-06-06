package com.openclassrooms.realestatemanager.data.repositories.permissions

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import com.openclassrooms.realestatemanager.data.models.entities.permissions.AccessPermission
import com.openclassrooms.realestatemanager.data.models.entities.permissions.DataMultiplePermission
import com.openclassrooms.realestatemanager.data.vendors.dexter.PermissionServiceApi
import io.reactivex.rxjava3.core.Single
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PermissionsRepositoryTests {
    private val mockPermission: PermissionServiceApi = mock()
    private val repository = PermissionsRepositoryImpl(mockPermission)

    @Test
    fun testRequestGeolocationAccessPermission() {
        val data = DataMultiplePermission(listOf(AccessPermission.GEOLOCATION_FOREGROUND, AccessPermission.GEOLOCATION_BACKGROUND), listOf())
        whenever(mockPermission.requestPermissions(any())).thenReturn(Single.just(data))

        repository.requestGeolocationAccessPermission()
            .test()
            .assertValue { it == data.toDomain() }

        verify(mockPermission).requestPermissions(any())
        verifyNoMoreInteractions(mockPermission)
    }

    @Test
    fun testGetGeolocationAccessPermissionStatus() {
        val expectedData = DataMultiplePermission(listOf(AccessPermission.GEOLOCATION_FOREGROUND, AccessPermission.GEOLOCATION_BACKGROUND), listOf())
        whenever(mockPermission.getPermissionsStatus(any())).thenReturn(expectedData)

        val result = repository.getGeolocationAccessPermissionStatus()

        assertThat(result, equalTo(expectedData.toDomain()))
        verify(mockPermission).getPermissionsStatus(any())
        verifyNoMoreInteractions(mockPermission)
    }
}
