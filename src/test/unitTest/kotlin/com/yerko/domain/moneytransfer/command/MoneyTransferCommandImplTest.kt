package com.yerko.domain.moneytransfer.command

import com.yerko.domain.account.Account
import com.yerko.domain.moneytransfer.CreateMoneyTransfer
import com.yerko.domain.moneytransfer.Money
import com.yerko.domain.moneytransfer.validator.MoneyTransferValidator
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.util.*

class MoneyTransferCommandImplTest {
    private lateinit var moneyTransferCommand: MoneyTransferCommand
    private lateinit var moneyTransferValidator: MoneyTransferValidator
    private lateinit var withDrawCommand: WithDrawMoneyCommand
    private lateinit var transferMoneyCommand: TransferMoneyCommand
    private val clpCurrency = "CLP"
    private val usdCurrency = "USD"

    @BeforeEach
    fun setUp() {
        moneyTransferValidator = mockk(relaxed = true)
        withDrawCommand = mockk(relaxed = true)
        transferMoneyCommand = mockk(relaxed = true)
        moneyTransferCommand = MoneyTransferCommandImpl(moneyTransferValidator, withDrawCommand, transferMoneyCommand)
    }

    @Test
    fun `Should transfer money from one account to another account with the same currency`() {
        val originAccount = Account(
            UUID.randomUUID(),
            Money(BigDecimal.valueOf(100L), usdCurrency)
        )
        val destinationAccount = Account(
            UUID.randomUUID(),
            Money(BigDecimal.valueOf(100L), usdCurrency)
        )
        val transferAmount = BigDecimal.valueOf(50L)
        val moneyTransfer = CreateMoneyTransfer(originAccount, destinationAccount, transferAmount)

         moneyTransferCommand.transferAmount(moneyTransfer)

        verify(atLeast = 1) { moneyTransferValidator.validate(any(),any()) }
        verify(atLeast = 1) { withDrawCommand.withDrawMoney(any(),any()) }
        verify(atLeast = 1) { transferMoneyCommand.transferMoney(any(),any(), any()) }
    }

}
