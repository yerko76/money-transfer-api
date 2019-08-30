package com.yerko.rest.com.yerko

import com.yerko.application.rest.account.AccountCreatedResponse
import com.yerko.application.rest.account.AccountInformationResponse
import com.yerko.application.rest.account.CreateAccountRequest
import com.yerko.application.rest.moneytransfer.CreateMoneyTransferRequest
import com.yerko.application.rest.moneytransfer.MoneyTransferResponse
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
import java.util.*

class AccountResourceIntegrationTest {
    private lateinit var requestBuilder: HttpRequestBuilder
    private lateinit var httpClient: HttpClient
    private val appUrl= "http://0.0.0.0:8080"

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
        //TODO CLEAN DATABASE BEFORE INSERT
        val existingCustomerInDb = "156f6516-33e3-41b6-9335-bbbff54d9098"
        val account = CreateAccountRequest(Money(BigDecimal(100L), "USD"), UUID.fromString(existingCustomerInDb))
        requestBuilder.url("${appUrl}/api/v1/accounts")
        requestBuilder.body = account

        val response = runBlocking { createAccount() }

        assertThat(response.accountId).isNotNull()
    }

    @Test
    fun `should return AccountInformation when I pass accountId`() {
        val accountId = "156f6516-33e3-41b6-9335-abcff54d7001"
        requestBuilder.url("${appUrl}/api/v1/accounts/${accountId}")

        val response = runBlocking { getAccountInformation() }

        assertThat(response.accountInformation.accountId).isEqualTo(UUID.fromString(accountId))
        assertThat(response.accountInformation.active).isTrue()
        assertThat(response.accountInformation.customerId).isNotNull()
        assertThat(response.accountInformation.moneyDto.amount).isGreaterThan(BigDecimal.ZERO)
        assertThat(response.accountInformation.moneyDto.currency).isNotNull()
    }

    @Test
    fun `should transfer money`() {
        val fromAccount = "156f6516-33e3-41b6-9335-abcff54d7003"
        val toAccount = UUID.fromString("156f6516-33e3-41b6-9335-abcff54d7000")
        val transferAmount = BigDecimal.TEN
        val transferRequest  = CreateMoneyTransferRequest(toAccount, transferAmount)
        requestBuilder.url("${appUrl}/api/v1/accounts/${fromAccount}/transfer")
        requestBuilder.body = transferRequest

        val response = runBlocking { transferMoney() }

        assertThat(response.transactionId).isNotNull()
        assertThat(response.fromAccountId).isNotNull()
        assertThat(response.toAccountId).isNotNull()
        assertThat(response.transferredAmount.amount).isGreaterThan(BigDecimal.ZERO)
        assertThat(response.transferredAmount.currency).isNotNull()
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

    private suspend fun transferMoney(): MoneyTransferResponse {
        val response = httpClient.post<MoneyTransferResponse>(requestBuilder)
        httpClient.close()
        return response
    }
}
