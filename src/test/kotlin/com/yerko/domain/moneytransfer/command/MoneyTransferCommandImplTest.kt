package com.yerko.domain.moneytransfer.command

import com.yerko.domain.account.Account
import com.yerko.domain.exchangerate.MoneyConverter
import com.yerko.domain.moneytransfer.CreateMoneyTransfer
import com.yerko.domain.moneytransfer.Money
import com.yerko.domain.moneytransfer.validator.MoneyTransferValidator
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal
import java.util.*

class MoneyTransferCommandImplTest {
    private lateinit var moneyTransferCommand: MoneyTransferCommand
    private lateinit var moneyTransferValidator: MoneyTransferValidator
    private lateinit var moneyConverter: MoneyConverter
    private val clpCurrency = "CLP"
    private val usdCurrency = "USD"

    @Before
    fun setUp() {
        moneyTransferValidator = mockk(relaxed = true)
        moneyConverter = mockk()
        moneyTransferCommand =
            MoneyTransferCommandImpl(moneyTransferValidator, moneyConverter)
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
        val transferAmount = Money(BigDecimal.valueOf(50L), usdCurrency)
        val moneyTransfer = CreateMoneyTransfer(originAccount, destinationAccount, transferAmount)

        val transferDetail = moneyTransferCommand.transferAmount(moneyTransfer)

        verify(exactly = 0) { moneyConverter.convert(transferAmount, usdCurrency) }
        assertThat(transferDetail.originAccount.balance.amount).isEqualTo(BigDecimal.valueOf(50L))
        assertThat(transferDetail.destinationAccount.balance.amount).isEqualTo(BigDecimal.valueOf(150L))
    }

    @Test
    fun `Should transfer money from one account to another account with different currency`() {
        val originAccount = Account(
            UUID.randomUUID(),
            Money(BigDecimal.valueOf(100L), usdCurrency)
        )
        val destinationAccount = Account(
            UUID.randomUUID(),
            Money(BigDecimal.valueOf(10000L), clpCurrency)
        )
        val transferAmount = Money(BigDecimal.valueOf(50L), usdCurrency)
        val convertedAmount = Money(BigDecimal.valueOf(35162L), clpCurrency)
        val moneyTransfer = CreateMoneyTransfer(originAccount, destinationAccount, transferAmount)
        every { moneyConverter.convert(transferAmount, clpCurrency) } returns convertedAmount

        val transferDetail = moneyTransferCommand.transferAmount(moneyTransfer)

        assertThat(transferDetail.originAccount.balance.amount).isEqualTo(BigDecimal.valueOf(50L))
        assertThat(transferDetail.destinationAccount.balance.amount).isEqualTo(BigDecimal.valueOf(45162L))
    }
}
