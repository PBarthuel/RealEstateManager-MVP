package com.openclassrooms.realestatemanager.data.utils

import com.openclassrooms.realestatemanager.data.models.exceptions.DataAPIDecodeException
import com.openclassrooms.realestatemanager.domain.models.DomainNetworkException
import com.openclassrooms.realestatemanager.domain.models.error.DomainError
import kotlinx.serialization.SerializationException
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RxErrorExtensionTests {

    @Test
    fun testGetDomainExceptions() {
        // TODO si je rajoute data exception rajouter test ici
        val expectedErrorMessage = "DataAPIDecodeExceptionMessage"
        assertThat(
            getDomainException(DataAPIDecodeException(expectedErrorMessage)) as DomainNetworkException,
            equalTo(DomainNetworkException.InternalError(DomainError(expectedErrorMessage))))

        assertThat(
            getDomainException(SerializationException(expectedErrorMessage)) as DomainNetworkException,
            equalTo(DomainNetworkException.InternalError(DomainError(expectedErrorMessage))))

        assertThat(
            getDomainException(Throwable("error")),
            equalTo(DomainNetworkException.InternalError(DomainError("error"))))
    }
}
