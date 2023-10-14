package com.example.currentweather

import com.example.currentweather.util.timeByDayMonth
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun assertEquals() {
        assertEquals("11/11", timeByDayMonth("2222-11-11"))
    }
}