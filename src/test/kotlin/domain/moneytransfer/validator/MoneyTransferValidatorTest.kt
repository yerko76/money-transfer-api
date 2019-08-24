package domain.moneytransfer.validator

import domain.account.Account
import domain.moneytransfer.Money
import domain.moneytransfer.exception.DifferentCurrencyException
import domain.moneytransfer.exception.InsufficientBalanceException
import org.assertj.core.api.Assertions
import org.junit.Test
import java.math.BigDecimal
import java.util.*

class MoneyTransferValidatorTest {

    private val moneyTransferValidator = MoneyTransferValidator()

    @Test
    fun `Should NOT transfer money from one account without enought balance to another account`() {
        val originAccount = Account(UUID.randomUUID(), Money(BigDecimal.valueOf(100L), "USD"))
        val transferAmount = Money(BigDecimal.valueOf(150L), "USD")

        val exception = Assertions.catchThrowable {
            moneyTransferValidator.validate(originAccount.balance, transferAmount)
        }

        Assertions.assertThat(exception)
            .isInstanceOf(InsufficientBalanceException::class.java)
            .hasMessageContaining("Account does not have enough balance for transfer operation")
    }

    @Test
    fun `Should NOT transfer money from one account to another account when transfer amount is in different currency`() {
        val originAccount = Account(UUID.randomUUID(), Money(BigDecimal.valueOf(100L), "USD"))
        val transferAmount = Money(BigDecimal.valueOf(50L), "CLP")

        val exception = Assertions.catchThrowable {
            moneyTransferValidator.validate(originAccount.balance, transferAmount)
        }

        Assertions.assertThat(exception)
            .isInstanceOf(DifferentCurrencyException::class.java)
            .hasMessageContaining("Currency from origin account and transfer amount are different")
    }
}
