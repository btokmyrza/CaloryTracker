package kz.btokmyrza.tracker_data.repository

import com.google.common.truth.Truth.assertThat
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kz.btokmyrza.calorytracker.tracker_data.mapper.TrackedFoodModelMapper
import kz.btokmyrza.calorytracker.tracker_data.network.OpenFoodAPI
import kz.btokmyrza.calorytracker.tracker_data.repository.DefaultTrackerRepository
import kz.btokmyrza.tracker_data.network.invalidFoodResponse
import kz.btokmyrza.tracker_data.network.validFoodResponse
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

internal class DefaultTrackerRepositoryTest {

    private lateinit var repository: DefaultTrackerRepository
    private lateinit var mockWebServer: MockWebServer
    private lateinit var okHttpClient: OkHttpClient
    private lateinit var api: OpenFoodAPI

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        okHttpClient = OkHttpClient.Builder()
            .writeTimeout(1, TimeUnit.SECONDS)
            .readTimeout(1, TimeUnit.SECONDS)
            .connectTimeout(1, TimeUnit.SECONDS)
            .build()
        api = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(mockWebServer.url("/"))
            .build()
            .create(OpenFoodAPI::class.java)
        repository = DefaultTrackerRepository(
            dao = mockk(relaxed = true),
            api = api,
            ioDispatcher = Dispatchers.IO,
            trackedFoodModelMapper = TrackedFoodModelMapper(),
        )
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `Search food, valid response, returns results`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(validFoodResponse)
        )
        val result = repository.searchFood("banana", 1, 40)

        assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun `Search food, invalid response, returns failure`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(403)
                .setBody(validFoodResponse)
        )
        val result = repository.searchFood("banana", 1, 40)

        assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `Search food, malformed response, returns failure`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setBody(invalidFoodResponse)
        )
        val result = repository.searchFood("banana", 1, 40)

        assertThat(result.isFailure).isTrue()
    }
}