package com.yerko.rest.com.yerko

import com.yerko.application.rest.Health
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.get
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class HealthCheckIntegrationTest {
    private val appUrl= "http://0.0.0.0:8080"

    @Test
    fun `should return UP`() {
        val response = runBlocking { getHealthCheck() }
        assertThat(response.status).isEqualTo("UP")
    }

    private suspend fun getHealthCheck(): Health {
        val httpClient = HttpClient() {
            install(JsonFeature)
        }

        val response = httpClient.get<Health>("${appUrl}/api/v1/health")
        httpClient.close()
        return response
    }
}
