package com.yerko.rest.com.yerko

import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.get
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class HealthCheckIntegrationTest {

    @Test
    fun `should return UP`() {
        val response = runBlocking { getHealthCheck() }

        assertThat(response).isEqualTo("OK")
    }

    private suspend fun getHealthCheck(): String {
        val httpClient = HttpClient() {
            install(JsonFeature)
        }

        val response = httpClient.get<String>("http://0.0.0.0:8080/api/v1/health")
        httpClient.close()
        return response
    }
}
