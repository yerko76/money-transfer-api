package com.yerko.domain.moneytransfer.validator

import com.yerko.domain.account.Account
import com.yerko.domain.moneytransfer.Money
import com.yerko.domain.moneytransfer.validator.exception.MoneyTransferValidationException
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThatCode
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal
import java.util.*

class MoneyTransferValidatorTest {

    private lateinit var moneyTransferValidator : MoneyTransferValidator

    @BeforeEach
    fun setUp() {
        moneyTransferValidator = MoneyTransferValidatorImpl()
    }

    @Test
    fun `Should throw MoneyTransferValidationException when transfer amount is greater than balance`() {
        val originAccount = Account(
            UUID.randomUUID(),
            Money(BigDecimal.valueOf(100L), "USD")
        )
        val transferAmount = Money(BigDecimal.valueOf(150L), "USD")

        val exception = Assertions.catchThrowable {
            moneyTransferValidator.validate(originAccount.balance, transferAmount)
        }

        Assertions.assertThat(exception)
            .isInstanceOf(MoneyTransferValidationException::class.java)
            .hasMessageContaining("Account does not have enough balance for transferAmount operation")
    }

    @Test
    fun `Should throw MoneyTransferValidationException when transfer amount is negative`() {
        val originAccount = Account(
            UUID.randomUUID(),
            Money(BigDecimal.valueOf(100L), "USD")
        )
        val transferAmount = Money(BigDecimal.valueOf(-50L), "USD")

        val exception = Assertions.catchThrowable {
            moneyTransferValidator.validate(originAccount.balance, transferAmount)
        }

        Assertions.assertThat(exception)
            .isInstanceOf(MoneyTransferValidationException::class.java)
            .hasMessageContaining("Invalid transfer amount")
    }

    @Test
    fun `Should throw MoneyTransferValidationException when transfer amount is zero`() {
        val originAccount = Account(
            UUID.randomUUID(),
            Money(BigDecimal.valueOf(100L), "USD")
        )
        val transferAmount = Money(BigDecimal.valueOf(0L), "USD")

        val exception = Assertions.catchThrowable {
            moneyTransferValidator.validate(originAccount.balance, transferAmount)
        }

        Assertions.assertThat(exception)
            .isInstanceOf(MoneyTransferValidationException::class.java)
            .hasMessageContaining("Invalid transfer amount")
    }

    @Test
    fun `Should trow MoneyTransferValidationException when transfer amount is in different currency than account currency`() {
        val originAccount = Account(
            UUID.randomUUID(),
            Money(BigDecimal.valueOf(100L), "USD")
        )
        val transferAmount = Money(BigDecimal.valueOf(50L), "CLP")

        val exception = Assertions.catchThrowable {
            moneyTransferValidator.validate(originAccount.balance, transferAmount)
        }

        Assertions.assertThat(exception)
            .isInstanceOf(MoneyTransferValidationException::class.java)
            .hasMessageContaining("Currency from origin account and transferAmount amount are different")
    }

    @Test
    fun `Should pass validation`() {
        val originAccount = Account(
            UUID.randomUUID(),
            Money(BigDecimal.valueOf(100L), "USD")
        )
        val transferAmount = Money(BigDecimal.valueOf(50L), "USD")

        assertThatCode {  moneyTransferValidator.validate(originAccount.balance, transferAmount)}.doesNotThrowAnyException()
    }
}
