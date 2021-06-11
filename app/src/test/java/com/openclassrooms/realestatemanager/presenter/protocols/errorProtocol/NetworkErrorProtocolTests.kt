package com.openclassrooms.realestatemanager.presenter.protocols.errorProtocol

import android.view.ViewGroup
import com.openclassrooms.realestatemanager.domain.models.DomainNetworkException
import com.openclassrooms.realestatemanager.domain.models.error.DomainError
import com.openclassrooms.realestatemanager.presenter.models.uiErrorItem.UIErrorItem
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.fail
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

class TestNetworkErrorProtocol : NetworkErrorProtocol {
    
    private var onReceiveInternalErrorCalled = false

    override val rootView: ViewGroup?
        get() = null

    override fun onReceiveInternalError(errorItem: UIErrorItem?) {
        onReceiveInternalErrorCalled = true
    }

    fun resetExpectedCall() {
        onReceiveInternalErrorCalled = false
    }

    fun isOnReceiveInternalErrorCalled(): Boolean {
        return onReceiveInternalErrorCalled
    }
}

@RunWith(MockitoJUnitRunner::class)
class NetworkErrorProtocolTests {
    private val testView = TestNetworkErrorProtocol()

    @Test
    fun testOnReceiveError() {
        DomainNetworkException::class.nestedClasses.forEach {
            when (it) {
                DomainNetworkException.InternalError::class -> {
                    testView.onReceiveError(DomainNetworkException.InternalError(DomainError("expected message")))
                    assertThat(testView.isOnReceiveInternalErrorCalled(), equalTo(true))
                }
                else -> fail("Missing test for new value of enum DomainNetworkException.Error")
            }
            testView.resetExpectedCall()
        }
    }
}
