package com.alifatma.firewatch

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.alifatma.firewatch.network.RfsApiService
import com.alifatma.firewatch.repository.IncidentRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import jakarta.inject.Inject
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class RfsInstrumentedTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var incidentRepository: IncidentRepository

    @Inject
    lateinit var rfsApiService: RfsApiService

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun graph_resolves_core_bindings() {
        assertNotNull(incidentRepository)
        assertNotNull(rfsApiService)
    }
}