package com.yerko.application.rest.account

import com.yerko.domain.moneytransfer.Money
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal

class AccountResourceTest {
    private lateinit var accountResource: AccountResource

    @Before
    fun setUp() {
        accountResource = AccountResource()
    }

    @Test
    fun `Should create account`() {
        val initialBalance = Money(BigDecimal.valueOf(100L), "USD")
        val accountRequest = CreateAccountRequest(initialBalance, "customerId")

        val response = accountResource.create(accountRequest)
        val accountResponse = response.body() as AccountResponse

        assertThat(response.status.code).isEqualTo(200)
        assertThat(accountResponse.accountId).isNotNull()
    }
}
