package com.openclassrooms.realestatemanager.data.models.entities.permissions

import com.openclassrooms.realestatemanager.domain.models.permissions.DomainAccessPermission
import com.openclassrooms.realestatemanager.domain.models.permissions.DomainMultiplePermission
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DataMultiplePermissionTests {

    @Test
    fun testToDomain() {
        val dataMultiplePermission = DataMultiplePermission(listOf(), listOf(AccessPermission.GEOLOCATION_FOREGROUND))
        assertThat(dataMultiplePermission.toDomain(), equalTo(DomainMultiplePermission(listOf(), listOf(DomainAccessPermission.GEOLOCATION_FOREGROUND))))
    }
}
