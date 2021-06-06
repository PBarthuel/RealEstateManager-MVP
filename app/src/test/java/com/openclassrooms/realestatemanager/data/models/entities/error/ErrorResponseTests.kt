package com.openclassrooms.realestatemanager.data.models.entities.error

import com.openclassrooms.realestatemanager.utils.fixtures.exceptions.DataExceptionsFixtures
import com.openclassrooms.realestatemanager.utils.fixtures.exceptions.DomainExceptionsFixtures
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ErrorResponseTests {

    @Test
    fun testToDomain() {
        val expectedData = mapOf(Pair("Test", "test"))
        var expectedDomain = DomainExceptionsFixtures.DomainErrorUtils.create(expectedData)
        var actualResponse = DataExceptionsFixtures.ErrorResponseUtils.create(expectedData)
        assertThat(actualResponse.toDomain(), equalTo(expectedDomain))

        expectedDomain = DomainExceptionsFixtures.DomainErrorUtils.create()
        actualResponse = DataExceptionsFixtures.ErrorResponseUtils.create()
        assertThat(actualResponse.toDomain(), equalTo(expectedDomain))
    }
}
