package com.example.covid100.data.api

import com.example.covid100.utils.Injector
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class NewsApiServiceTest {

    private lateinit var newsApiService: NewsApiService

    @Before
    fun setup() {
        newsApiService = Injector.getInstance().providesNewsApiService()
    }

    @Test
    fun getCovidNews_onSuccess_returnNonNull() = runBlocking {
        val newsResponse = newsApiService.getCovidNews()

        Assert.assertNotNull(newsResponse)
        Assert.assertTrue(newsResponse.isSuccessful)

    }
}