package com.yerko.application.account.command

import com.yerko.application.account.entity.AccountDto
import com.yerko.application.account.entity.AccountReadRepository
import com.yerko.application.account.entity.AccountWriteRepository
import com.yerko.application.account.entity.MoneyDto
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
    private lateinit var accountReadRepository: AccountReadRepository

    @BeforeEach
    fun setUp() {
        repository = mockk()
        accountReadRepository = mockk()
        createAccountCommand = CreateAccountCommandHandler(repository, accountReadRepository)
    }

    @Test
    fun `Should create account`() {
        val expectedAccountId = UUID.randomUUID()
        val createAccount = CreateAccount(
            Money(BigDecimal.TEN, "USD"),
            UUID.randomUUID())
        every { runBlocking { accountReadRepository.findByIdCustomerId(any()) } } returns null
        every { runBlocking{ repository.save(any()) } } returns expectedAccountId

        val response = createAccountCommand.create(createAccount)

        assertThat(response).isEqualTo(expectedAccountId    )
    }

    @Test
    fun `Should throw exception when account is not generated`() {
        val createAccount = CreateAccount(Money(BigDecimal.TEN, "USD"), UUID.randomUUID())
        every { runBlocking { accountReadRepository.findByIdCustomerId(any()) } } returns null
        every { runBlocking { repository.save(any()) } } throws UnableToCreateAccountException("Unable to create account for customer: ${createAccount.customerId}")

        val exception = Assertions.catchThrowable {
            createAccountCommand.create(createAccount)
        }

        assertThat(exception)
            .isInstanceOf(UnableToCreateAccountException::class.java)
            .hasMessageContaining("Unable to create account for customer: ${createAccount.customerId}")
    }

    @Test
    fun `Should throw exception when account exists`() {
        val createAccount = CreateAccount(Money(BigDecimal.TEN, "USD"), UUID.randomUUID())
        val accountFromDb = AccountDto(UUID.randomUUID(), MoneyDto(BigDecimal.ZERO,"USD"), UUID.randomUUID(), true)
        every { runBlocking { accountReadRepository.findByIdCustomerId(any()) } } returns accountFromDb

        val exception = Assertions.catchThrowable {
            createAccountCommand.create(createAccount)
        }

        assertThat(exception)
            .isInstanceOf(UnableToCreateAccountException::class.java)
            .hasMessageContaining("Unable to create account for customer: ${createAccount.customerId} due to account already exists")
    }

}
