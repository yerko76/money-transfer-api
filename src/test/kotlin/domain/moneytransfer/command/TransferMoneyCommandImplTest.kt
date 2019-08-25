package domain.moneytransfer.command

import domain.account.Account
import domain.exchangerate.MoneyConverter
import domain.moneytransfer.Money
import domain.moneytransfer.validator.MoneyTransferValidator
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal
import java.util.*

class TransferMoneyCommandImplTest {
    private lateinit var transferMoneyCommand: TransferMoneyCommand
    private lateinit var moneyTransferValidator: MoneyTransferValidator
    private lateinit var moneyConverter: MoneyConverter
    private val clpCurrency = "CLP"
    private val usdCurrency = "USD"

    @Before
    fun setUp() {
        moneyTransferValidator = mockk(relaxed = true)
        moneyConverter = mockk()
        transferMoneyCommand = TransferMoneyCommandImpl(moneyTransferValidator, moneyConverter)
    }

    @Test
    fun `Should transfer money from one account to another account with the same currency`() {
        val originAccount = Account(UUID.randomUUID(), Money(BigDecimal.valueOf(100L), usdCurrency))
        val destinationAccount = Account(UUID.randomUUID(), Money(BigDecimal.valueOf(100L), usdCurrency))
        val transferAmount = Money(BigDecimal.valueOf(50L), usdCurrency)
        every { moneyConverter.convert(transferAmount, usdCurrency) } returns transferAmount

        val transferDetail = transferMoneyCommand.transferAmount(originAccount, destinationAccount,transferAmount)

        assertThat(transferDetail.originAccount.balance.amount).isEqualTo(BigDecimal.valueOf(50L))
        assertThat(transferDetail.destinationAccount.balance.amount).isEqualTo(BigDecimal.valueOf(150L))
    }

    @Test
    fun `Should transfer money from one account to another account with different currency`() {
        val originAccount = Account(UUID.randomUUID(), Money(BigDecimal.valueOf(100L), usdCurrency))
        val destinationAccount = Account(UUID.randomUUID(), Money(BigDecimal.valueOf(10000L), clpCurrency))
        val transferAmount = Money(BigDecimal.valueOf(50L), usdCurrency)
        val convertedAmount = Money(BigDecimal.valueOf(35162L), clpCurrency)
        every { moneyConverter.convert(transferAmount, clpCurrency) } returns convertedAmount

        val transferDetail = transferMoneyCommand.transferAmount(originAccount, destinationAccount,transferAmount)

        assertThat(transferDetail.originAccount.balance.amount).isEqualTo(BigDecimal.valueOf(50L))
        assertThat(transferDetail.destinationAccount.balance.amount).isEqualTo(BigDecimal.valueOf(45162L))
    }
}
