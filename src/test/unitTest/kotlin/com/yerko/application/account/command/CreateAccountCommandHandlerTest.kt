package com.yerko.application.account.command

import com.yerko.application.account.entity.AccountWriteRepository
import com.yerko.domain.account.command.CreateAccount
import com.yerko.domain.moneytransfer.Money
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.util.*

class CreateAccountCommandHandlerTest {

    private lateinit var createAccountCommand: CreateAccountCommandHandler
    private lateinit var repository: AccountWriteRepository

    @BeforeEach
    fun setUp() {
        repository = mockk()
        createAccountCommand = CreateAccountCommandHandler(repository)
    }

    @Test
    fun `Should create account`() {
        val expectedAccountId = UUID.randomUUID()
        val createAccount = CreateAccount(
            Money(BigDecimal.TEN, "USD"),
            UUID.randomUUID())
        every { runBlocking{ repository.save(any()) } } returns expectedAccountId

        val response = createAccountCommand.create(createAccount)

        assertThat(response).isEqualTo(expectedAccountId    )
    }

    @Test
    fun `Should throw exception when account is not generated`() {
        val createAccount = CreateAccount(Money(BigDecimal.TEN, "USD"), UUID.randomUUID())
        every { runBlocking { repository.save(any()) } } throws UnableToCreateAccountException("Unable to create account for customer: ${createAccount.customerId}")

        val exception = Assertions.catchThrowable {
            createAccountCommand.create(createAccount)
        }

        assertThat(exception)
            .isInstanceOf(UnableToCreateAccountException::class.java)
            .hasMessageContaining("Unable to create account for customer: ${createAccount.customerId}")
    }

}
