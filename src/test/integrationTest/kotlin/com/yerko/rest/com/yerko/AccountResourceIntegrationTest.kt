package com.yerko.rest.com.yerko

import com.yerko.application.rest.account.*
import com.yerko.domain.moneytransfer.Money
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JacksonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class AccountResourceIntegrationTest {
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
    fun `should return AccountId when I open a new account`() {
        val account = CreateAccountRequest(Money(BigDecimal(100L), "USD"), "CUSTOMER-ID")
        requestBuilder.url("http://0.0.0.0:8080/api/v1/accounts")
        requestBuilder.body = account

        val response = runBlocking { createAccount(account) }

        assertThat(response.accountId).isNotNull()
    }

    @Test
    fun `should return AccountInformation when I pass accountId`() {
        val accountId = "212885e8-91d4-4ceb-9a28-df2401ede790"
        requestBuilder.url("http://0.0.0.0:8080/api/v1/accounts/${accountId}")
        val response = runBlocking { getAccountInformation() }

        assertThat(response.accountId).isNotNull()
    }

    private suspend fun getAccountInformation(): AccountInformationResponse {
        val response = httpClient.get<AccountInformationResponse>(requestBuilder)
        httpClient.close()
        return response
    }

    private suspend fun createAccount(): AccountCreatedResponse {
        val response = httpClient.post<AccountCreatedResponse>(requestBuilder)
        httpClient.close()
        return response
    }
}
