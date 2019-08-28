package com.yerko.rest.com.yerko

import io.ktor.client.HttpClient
import io.ktor.client.features.json.JacksonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MoneyTransferResourceIntegrationTest {
    private lateinit var requestBuilder: HttpRequestBuilder
    private lateinit var httpClient: HttpClient

    @BeforeEach
    internal fun setUp() {
        requestBuilder = HttpRequestBuilder()
        requestBuilder.contentType(ContentType.Application.Json)
        httpClient = HttpClient() {
            install(JsonFeature){
                serializer = JacksonSerializer()
            }
        }
    }

    @Test
    fun `should transfer money`() {
        val fromAccount = "156f6516-33e3-41b6-9335-abcff54d7003"
        val toAccount = "156f6516-33e3-41b6-9335-abcff54d7003"
        requestBuilder.url("http://0.0.0.0:8080/api/v1/accounts/${accountId}")
        val response = runBlocking { getAccountInformation() }

        Assertions.assertThat(response.accountInformation.accountId).isEqualTo(UUID.fromString(accountId))
        Assertions.assertThat(response.accountInformation.active).isTrue()
        Assertions.assertThat(response.accountInformation.customerId).isNotNull()
        Assertions.assertThat(response.accountInformation.moneyDto.amount).isGreaterThan(BigDecimal.ZERO)
        Assertions.assertThat(response.accountInformation.moneyDto.currency).isNotNull()
    }
}
