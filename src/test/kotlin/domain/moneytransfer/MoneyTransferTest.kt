package domain.moneytransfer

import domain.account.Account
import domain.moneytransfer.validator.IMoneyTransferValidator
import domain.moneytransfer.validator.MoneyTransferValidator
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal
import java.util.*

class MoneyTransferTest {


    private lateinit var moneyTransfer: MoneyTransfer
    private lateinit var moneyTransferValidator: IMoneyTransferValidator

    @Before
    fun setUp() {
        moneyTransferValidator = MoneyTransferValidator()
        moneyTransfer = MoneyTransfer(moneyTransferValidator)
    }

    @Test
    fun `Should transfer money from one account to another account with the same currency`() {
        val originAccount = Account(UUID.randomUUID(), Money(BigDecimal.valueOf(100L), "USD"))
        val destinationAccount = Account(UUID.randomUUID(), Money(BigDecimal.valueOf(100L), "USD"))
        val transferAmount = Money(BigDecimal.valueOf(50L), "USD")

        val transferDetail = moneyTransfer.transfer(originAccount, destinationAccount,transferAmount)

        assertThat(transferDetail.originAccount.balance.amount).isEqualTo(BigDecimal.valueOf(50L))
        assertThat(transferDetail.destinationAccount.balance.amount).isEqualTo(BigDecimal.valueOf(150L))

    }


}
