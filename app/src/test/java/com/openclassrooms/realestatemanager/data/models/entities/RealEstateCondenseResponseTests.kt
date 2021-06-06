package com.openclassrooms.realestatemanager.data.models.entities

import com.openclassrooms.realestatemanager.utils.fixtures.entities.DataFixtures
import com.openclassrooms.realestatemanager.utils.fixtures.entities.DomainFixtures
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RealEstateCondenseResponseTests {
    
    @Test
    fun testToDomain() {
        val expectedDomain = DomainFixtures.DomainRealEstateCondenseUtils.create()
        val actualResponse = DataFixtures.RealEstateCondenseResponseUtils.create()
        assertThat(actualResponse.toDomain(), equalTo(expectedDomain))
    }
}