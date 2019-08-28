package com.yerko.application.account.command

import com.yerko.application.account.entity.AccountDto
import com.yerko.application.account.entity.AccountReadRepository
import com.yerko.application.account.entity.AccountWriteRepository
import com.yerko.application.account.entity.MoneyDto
import com.yerko.domain.account.command.CreateAccount
import com.yerko.domain.account.command.UpdateAccount
import com.yerko.domain.account.command.UpdateAccountCommand
import com.yerko.domain.moneytransfer.Money
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.util.*

class UpdateAccountCommandHandlerTest {
    private lateinit var updateCommand: UpdateAccountCommandHandler
    private lateinit var writeRepository: AccountWriteRepository
    private lateinit var readRepository: AccountReadRepository

    @BeforeEach
    fun setUp() {
        writeRepository = mockk()
        updateCommand = UpdateAccountCommandHandler(writeRepository, readRepository)
    }

    @Test
    fun `Should update account`() {
        val accountId = UUID.randomUUID()
        val moneyDto = MoneyDto(BigDecimal.TEN, "USD")
        val updateCommand = UpdateAccount(accountId, Money(BigDecimal.ONE,"USD"))
        val accountFromDb = AccountDto(accountId, moneyDto, UUID.randomUUID(), true)
        every { runBlocking { readRepository.findById(any()) } } returns accountFromDb
        every { runBlocking{ writeRepository.update(any()) } } returns expectedAccountId

        val response = updateCommand.update(createAccount)

        Assertions.assertThat(response).isEqualTo(accountId)
    }
}
