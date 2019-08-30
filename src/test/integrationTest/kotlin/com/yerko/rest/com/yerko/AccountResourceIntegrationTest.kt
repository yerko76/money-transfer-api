package com.yerko.rest.com.yerko

import com.yerko.application.rest.account.AccountInformationResponse
import com.yerko.application.rest.account.CreateAccountRequest
import com.yerko.application.rest.moneytransfer.CreateMoneyTransferRequest
import com.yerko.application.rest.moneytransfer.MoneyTransferResponse
import com.yerko.domain.account.command.AccountCreatedResponse
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
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
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
      val dbUtil = DbUtil()
      runBlocking { dbUtil.clearDb() }
    }

    @AfterEach
    internal fun tearDown(){
        httpClient.close()
    }


    @Test
    @DisplayName("Should create one account")
    fun createAccount() {
        val existingCustomerFromDb = "156f6516-33e3-41b6-9335-bbbff54d9094"
        val response = createAccount(existingCustomerFromDb, Money(BigDecimal(100L), "USD"))

        assertThat(response.accountId).isNotNull()
    }

    @Test
    @DisplayName("Should return account information given accountId")
    fun getAccount() {
        val existingCustomerFromDb = "156f6516-33e3-41b6-9335-bbbff54d9094"
        val createdAccount = createAccount(existingCustomerFromDb, Money(BigDecimal(100L), "USD"))
        requestBuilder.url("${appUrl}/api/v1/accounts/${createdAccount.accountId}")

        val response = runBlocking { getAccountInformation() }

        assertThat(response.accountInformation.accountId).isEqualTo(createdAccount.accountId)
        assertThat(response.accountInformation.active).isTrue()
        assertThat(response.accountInformation.customerId).isNotNull()
        assertThat(response.accountInformation.moneyDto.amount).isGreaterThan(BigDecimal.ZERO)
        assertThat(response.accountInformation.moneyDto.currency).isNotNull()
    }


    @Test
    @DisplayName("Should transfer money given 2 existing accounts with the same currency")
    fun transferAmount() {
        val existingCustomerFromDb1 = "156f6516-33e3-41b6-9335-bbbff54d9094"
        val existingCustomerFromDb2 = "156f6516-33e3-41b6-9335-bbbff54d9095"
        val fromAccount = createAccount(existingCustomerFromDb1, Money(BigDecimal(100L), "USD"))
        val toAccount = createAccount(existingCustomerFromDb2, Money(BigDecimal(100L), "USD"))

        val transferAmount = BigDecimal.valueOf(50L)
        val transferRequest  = CreateMoneyTransferRequest(toAccount.accountId, transferAmount)
        requestBuilder.url("${appUrl}/api/v1/accounts/${fromAccount.accountId}/transfer")
        requestBuilder.body = transferRequest

        val response = runBlocking { transferMoney() }

        assertThat(response.transactionId).isNotNull()
        assertThat(response.fromAccountId).isEqualTo(fromAccount.accountId)
        assertThat(response.toAccountId).isEqualTo(toAccount.accountId)
        assertThat(response.transferredAmount.amount).isEqualTo(transferAmount)
        assertThat(response.transferredAmount.currency).isEqualTo("USD")
    }

    @Test
    @DisplayName("Should transfer money given 2 existing accounts with different currency")
    fun transferAmountDifferentCurrency() {
        val existingCustomerFromDb1 = "156f6516-33e3-41b6-9335-bbbff54d9094"
        val existingCustomerFromDb2 = "156f6516-33e3-41b6-9335-bbbff54d9095"
        val fromAccount = createAccount(existingCustomerFromDb1, Money(BigDecimal(100L), "USD"))
        val toAccount = createAccount(existingCustomerFromDb2, Money(BigDecimal(100L), "CLP"))
        val transferAmount = BigDecimal.valueOf(50L)
        val expectedTransferredAmount = BigDecimal.valueOf(36045L)
        val transferRequest  = CreateMoneyTransferRequest(toAccount.accountId, transferAmount)
        requestBuilder.url("${appUrl}/api/v1/accounts/${fromAccount.accountId}/transfer")
        requestBuilder.body = transferRequest

        val response = runBlocking { transferMoney() }

        assertThat(response.transactionId).isNotNull()
        assertThat(response.fromAccountId).isEqualTo(fromAccount.accountId)
        assertThat(response.toAccountId).isEqualTo(toAccount.accountId)
        assertThat(response.transferredAmount.amount).isEqualTo(expectedTransferredAmount)
        assertThat(response.transferredAmount.currency).isEqualTo("CLP")
    }

    private suspend fun getAccountInformation(): AccountInformationResponse {
        return httpClient.get<AccountInformationResponse>(requestBuilder)
    }

    private suspend fun createAccount(account: CreateAccountRequest): AccountCreatedResponse {
        requestBuilder.body = account
        return httpClient.post<AccountCreatedResponse>(requestBuilder)
    }

    private suspend fun transferMoney(): MoneyTransferResponse {
        return httpClient.post<MoneyTransferResponse>(requestBuilder)
    }

    private fun createAccount(existingCustomerFromDb: String, balance: Money): AccountCreatedResponse {
        val account = CreateAccountRequest(balance, UUID.fromString(existingCustomerFromDb))
        requestBuilder.url("${appUrl}/api/v1/accounts")

        return runBlocking { createAccount(account) }
    }
}
