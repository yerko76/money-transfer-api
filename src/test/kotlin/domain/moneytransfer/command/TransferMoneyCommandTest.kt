package domain.moneytransfer.command

import command.TransferMoneyCommand
import command.TransferMoneyCommandImpl
import domain.account.Account
import domain.moneytransfer.Money
import domain.moneytransfer.validator.MoneyTransferValidator
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal
import java.util.*

class TransferMoneyCommandTest {
    private lateinit var transferMoneyCommand: TransferMoneyCommand
    private lateinit var moneyTransferValidator: MoneyTransferValidator

    @Before
    fun setUp() {
        moneyTransferValidator = mockk<MoneyTransferValidator>(relaxed = true)
        transferMoneyCommand = TransferMoneyCommandImpl(moneyTransferValidator)
    }

    @Test
    fun `Should transfer money from one account to another account with the same currency`() {
        val originAccount = Account(UUID.randomUUID(), Money(BigDecimal.valueOf(100L), "USD"))
        val destinationAccount = Account(UUID.randomUUID(), Money(BigDecimal.valueOf(100L), "USD"))
        val transferAmount = Money(BigDecimal.valueOf(50L), "USD")

        val transferDetail = transferMoneyCommand.transferAmount(originAccount, destinationAccount,transferAmount)

        assertThat(transferDetail.originAccount.balance.amount).isEqualTo(BigDecimal.valueOf(50L))
        assertThat(transferDetail.destinationAccount.balance.amount).isEqualTo(BigDecimal.valueOf(150L))

    }
}
