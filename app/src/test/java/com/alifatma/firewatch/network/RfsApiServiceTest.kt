package com.alifatma.firewatch.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Retrofit

@OptIn(ExperimentalCoroutinesApi::class)
class RfsApiServiceTest {

    private lateinit var server: MockWebServer
    private lateinit var api: RfsApiService

    private fun readResource(path: String): String {
        val stream = checkNotNull(javaClass.classLoader?.getResourceAsStream(path)) {
            "Missing test resource: $path"
        }
        return stream.bufferedReader().use { it.readText() }
    }

    @Before
    fun setUp() {
        server = MockWebServer()
        server.start()

        val json = Json {
            classDiscriminator = "type"
            ignoreUnknownKeys = true
        }

        val retrofit = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()

        api = retrofit.create(RfsApiService::class.java)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun `getMajorIncidents parses success response`() = runTest {
        val jsonBody = readResource("network/major-incidents-success.json")
        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .addHeader("Content-Type", "application/json")
                .setBody(jsonBody)
        )

        val response = api.getMajorIncidents()

        assertEquals("FeatureCollection", response.type)
        assertEquals(8, response.features.size)
        assertEquals("(GWYDIR HWY), MATHESON", response.features.first().properties.title)
        assertEquals("/feeds/majorIncidents.json", server.takeRequest().path)
    }

    @Test
    fun `getMajorIncidents throws HttpException on 404`() = runTest {
        server.enqueue(
            MockResponse()
                .setResponseCode(404)
                .addHeader("Content-Type", "text/html")
                .setBody("<html><body><h1>404 Not Found</h1></body></html>")
        )

        var exception: HttpException? = null
        try {
            api.getMajorIncidents()
            fail("Expected HttpException for 404 response")
        } catch (e: HttpException) {
            exception = e
        }

        assertEquals(404, exception?.code())
        assertEquals("/feeds/majorIncidents.json", server.takeRequest().path)
    }
}

