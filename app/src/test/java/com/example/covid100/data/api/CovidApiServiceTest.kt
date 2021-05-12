package com.example.covid100.data.api

import com.example.covid100.utils.Injector
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class CovidApiServiceTest {

    lateinit var api: CovidApiService

    @Before
    fun setup() {
        api = Injector.getInstance().providesCovidApiService()
    }

    @Test
    fun getCovidStats_onSuccess_returnsNonNullResponse() = runBlocking {
        val response = api.getCovidStats()

        Assert.assertNotNull(response)
        Assert.assertTrue(response.isSuccessful)
    }
}