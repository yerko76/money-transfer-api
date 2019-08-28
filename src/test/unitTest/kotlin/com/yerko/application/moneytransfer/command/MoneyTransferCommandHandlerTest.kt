package com.yerko.application.moneytransfer.command

import com.yerko.application.account.entity.AccountDto
import com.yerko.application.account.entity.MoneyDto
import com.yerko.application.account.query.AccountQueryHandler
import com.yerko.application.rest.moneytransfer.CreateMoneyTransferRequest
import com.yerko.domain.account.Account
import com.yerko.domain.moneytransfer.Money
import com.yerko.domain.moneytransfer.MoneyTransferDetail
import com.yerko.domain.moneytransfer.command.MoneyTransferCommand
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.util.*

class MoneyTransferCommandHandlerTest {
    private lateinit var moneyTransferCommandHandler: MoneyTransferCommandHandler
    private lateinit var accountQueryHandler: AccountQueryHandler
    private lateinit var moneyTransferCommand: MoneyTransferCommand
    private lateinit var moneyTransferService: MoneyTransferService

    @BeforeEach
    fun setUp() {
        accountQueryHandler = mockk()
        moneyTransferCommand = mockk()
        moneyTransferService = mockk()
        moneyTransferCommandHandler = MoneyTransferCommandHandler(accountQueryHandler, moneyTransferCommand, moneyTransferService)
    }

    @Test
    fun `Should transfer money`() {
        val transactionId = 29812
        val originAccount = UUID.randomUUID()
        val destinationAccount = UUID.randomUUID()
        val createMoneyTransfer = CreateMoneyTransferRequest(originAccount, destinationAccount, BigDecimal.TEN)
        val firstAccount = AccountDto(UUID.randomUUID(), MoneyDto(BigDecimal.TEN,"USD"), UUID.randomUUID(), true)
        val second = AccountDto(UUID.randomUUID(), MoneyDto(BigDecimal.TEN,"USD"), UUID.randomUUID(), true)
        val detail = MoneyTransferDetail(convertToAccount(firstAccount), convertToAccount(second),Money(BigDecimal.TEN,"USD"))
        every { runBlocking { accountQueryHandler.findById(any()) } } returns firstAccount
        every { runBlocking { accountQueryHandler.findById(any()) } } returns second
        every { runBlocking { moneyTransferService.saveTransaction(any()) } } returns transactionId
        every { moneyTransferCommand.transferAmount(any()) } returns detail

        val transferResult = runBlocking { moneyTransferCommandHandler.transferMoney(createMoneyTransfer) }

        assertThat(transferResult.transactionId).isNotNull()
        assertThat(transferResult.fromAccountId).isNotNull()
        assertThat(transferResult.toAccountId).isNotNull()
        assertThat(transferResult.transferredAmount).isNotNull
    }

    private fun convertToAccount(accountDto: AccountDto) : Account {
        return Account(accountDto.accountId, Money(accountDto.moneyDto.amount, accountDto.moneyDto.currency))
    }
}
