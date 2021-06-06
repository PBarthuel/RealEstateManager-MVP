package com.openclassrooms.realestatemanager.data.models.entities.permissions

import com.openclassrooms.realestatemanager.domain.models.permissions.DomainAccessPermission
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.fail
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AccessPermissionTests {

    @Test
    fun testToDomain() {
        AccessPermission.values().forEach {
            when (it) {
                AccessPermission.GEOLOCATION_FOREGROUND -> { assertThat(AccessPermission.GEOLOCATION_FOREGROUND.toDomain(), equalTo(DomainAccessPermission.GEOLOCATION_FOREGROUND)) }
                AccessPermission.GEOLOCATION_BACKGROUND -> { assertThat(AccessPermission.GEOLOCATION_BACKGROUND.toDomain(), equalTo(DomainAccessPermission.GEOLOCATION_BACKGROUND)) }
                AccessPermission.NONE -> { assertThat(AccessPermission.NONE.toDomain(), equalTo(DomainAccessPermission.NONE)) }
                else -> fail("Missing AccessPermission type in test")
            }
        }
    }

    @Test
    fun testListToDomain() {
        val dataPermissions = listOf(AccessPermission.GEOLOCATION_FOREGROUND)
        assertThat(dataPermissions.toDomain(), equalTo(listOf(DomainAccessPermission.GEOLOCATION_FOREGROUND)))
    }
}
