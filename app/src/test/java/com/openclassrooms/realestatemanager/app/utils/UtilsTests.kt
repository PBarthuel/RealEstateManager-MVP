package com.openclassrooms.realestatemanager.app.utils

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt

@RunWith(MockitoJUnitRunner::class)
class UtilsTests {
    
    @Test
    fun testDollarToEuro() {
        val dollar = 250000
        val expectedEuro = (dollar * 0.823).roundToInt()
        val result = Utils.convertDollarToEuro(dollar)
        assertThat(result, equalTo(expectedEuro))
    }
    
    @Test
    fun testTodayDate() {
        val date = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
        val result = Utils.todayDate
        assertThat(date, equalTo(result))
    }
}