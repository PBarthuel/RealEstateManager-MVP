package com.openclassrooms.realestatemanager.presenter.protocols.errorProtocol

import android.view.ViewGroup
import com.openclassrooms.realestatemanager.domain.models.DomainLocationException
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

class TestLocationErrorProtocol : LocationErrorProtocol {
    private var onReceiveEmptyUserLocationErrorCalled = false

    override val rootView: ViewGroup?
        get() = null

    override fun onReceiveEmptyUserLocationError() {
        onReceiveEmptyUserLocationErrorCalled = true
    }

    fun isOnReceiveEmptyUserLocationErrorCalled() = onReceiveEmptyUserLocationErrorCalled

    fun resetExpectedCall() {
        onReceiveEmptyUserLocationErrorCalled = false
    }
}

@RunWith(MockitoJUnitRunner::class)
class LocationErrorProtocolTests {

    private val testView = TestLocationErrorProtocol()

    @Test
    fun testOnReceiveError() {
        DomainLocationException::class.nestedClasses.forEach {
            when (it) {
                DomainLocationException.EmptyLocationError::class -> {
                    testView.onReceiveError(DomainLocationException.EmptyLocationError("DomainLocationException empty error"))
                    assertThat(testView.isOnReceiveEmptyUserLocationErrorCalled(), equalTo(true))
                }
            }
            testView.resetExpectedCall()
        }
    }
}
