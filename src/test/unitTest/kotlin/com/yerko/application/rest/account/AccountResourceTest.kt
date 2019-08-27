package com.yerko.application.rest.account

import com.yerko.domain.moneytransfer.Money
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal

class AccountResourceTest {
    private lateinit var accountResource: AccountResource

    @BeforeEach
    fun setUp() {
        accountResource = AccountResource()
    }

    @Test
    fun `Should create account`() {
        val initialBalance = Money(BigDecimal.valueOf(100L), "USD")
        val accountRequest = CreateAccountRequest(initialBalance, "customerId")

        val response = accountResource.create(accountRequest)

        assertThat(response.accountId).isNotNull()
    }
}
