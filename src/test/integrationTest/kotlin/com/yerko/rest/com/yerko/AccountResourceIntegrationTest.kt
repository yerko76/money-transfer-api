package com.yerko.rest.com.yerko

import com.yerko.application.rest.account.AccountCreatedResponse
import com.yerko.application.rest.account.AccountInformationResponse
import com.yerko.application.rest.account.CreateAccountRequest
import com.yerko.application.rest.moneytransfer.CreateMoneyTransferRequest
import com.yerko.application.rest.moneytransfer.MoneyTransferResponse
import com.yerko.domain.moneytransfer.Money
import com.yerko.infrastructure.configuration.DatabaseFactory
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
    private lateinit var originAccountId: UUID
    private lateinit var destinationAccountId: UUID
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

        DatabaseFactory.init()
    }

    @Test
    fun `random test`() {
        val existingCustomerFromDb = "156f6516-33e3-41b6-9335-bbbff54d9094"

        assertThat(existingCustomerFromDb).isNotNull()
    }


    @Test
    fun `should create an accounts`() {
        val existingCustomerFromDb = "156f6516-33e3-41b6-9335-bbbff54d9094"
        val originAccountId = createAccount(existingCustomerFromDb)

        assertThat(originAccountId).isNotNull()
    }

    private fun createAccount(existingCustomerFromDb: String): UUID {
        val firstAccount = CreateAccountRequest(Money(BigDecimal(100L), "USD"), UUID.fromString(existingCustomerFromDb))
        requestBuilder.url("${appUrl}/api/v1/accounts")

        val originAccountId = runBlocking { createAccount(firstAccount) }.accountId
        return originAccountId
    }

    @Test
    fun `should return AccountInformation when I pass accountId`() {
        val existingCustomerFromDb = "156f6516-33e3-41b6-9335-bbbff54d9094"
        val accountId =createAccount(existingCustomerFromDb)
        requestBuilder.url("${appUrl}/api/v1/accounts/${accountId}")

        val response = runBlocking { getAccountInformation() }

        assertThat(response.accountInformation.accountId).isEqualTo(originAccountId)
        assertThat(response.accountInformation.active).isTrue()
        assertThat(response.accountInformation.customerId).isNotNull()
        assertThat(response.accountInformation.moneyDto.amount).isGreaterThan(BigDecimal.ZERO)
        assertThat(response.accountInformation.moneyDto.currency).isNotNull()
    }


    @Test
    fun `should transfer money`() {
        val fromAccount = originAccountId::toString
        val toAccount = destinationAccountId
        val transferAmount = BigDecimal.valueOf(50L)
        val transferRequest  = CreateMoneyTransferRequest(toAccount, transferAmount)
        requestBuilder.url("${appUrl}/api/v1/accounts/${fromAccount}/transfer")
        requestBuilder.body = transferRequest

        val response = runBlocking { transferMoney() }

        assertThat(response.transactionId).isNotNull()
        assertThat(response.fromAccountId).isEqualTo(originAccountId)
        assertThat(response.toAccountId).isEqualTo(destinationAccountId)
        assertThat(response.transferredAmount.amount).isEqualTo(transferAmount)
        assertThat(response.transferredAmount.currency).isEqualTo("USD")
    }

    private suspend fun getAccountInformation(): AccountInformationResponse {
        val response = httpClient.get<AccountInformationResponse>(requestBuilder)
        httpClient.close()
        return response
    }

    private suspend fun createAccount(account: CreateAccountRequest): AccountCreatedResponse {
        requestBuilder.body = account
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
