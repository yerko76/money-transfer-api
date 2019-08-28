package com.yerko.domain.moneytransfer.command

import com.yerko.domain.account.Account
import com.yerko.domain.exchangerate.MoneyConverter
import com.yerko.domain.moneytransfer.Money
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.util.*

class TransferMoneyCommandImplTest {
    private lateinit var transferCommandImpl: TransferMoneyCommandImpl
    private lateinit var moneyConverter: MoneyConverter

    @BeforeEach
    fun setUp() {
        moneyConverter = mockk()
        transferCommandImpl = TransferMoneyCommandImpl(moneyConverter)
    }

    @Test
    fun `Should transfer money with the same currency`() {
        val destinationAccount = Account(
            UUID.randomUUID(),
            Money(BigDecimal.valueOf(100L), "USD")
        )
        val transferAmount = BigDecimal.valueOf(50L)

        val transferDetail = transferCommandImpl.transferMoney(destinationAccount, transferAmount, "USD")

        assertThat(transferDetail.balance.amount).isEqualTo(BigDecimal.valueOf(150L))
    }

    @Test
    fun `Should transfer money with different currency`() {
        val destinationAccount = Account(
            UUID.randomUUID(),
            Money(BigDecimal.valueOf(10000L), "USD")
        )
        val transferAmount = BigDecimal.valueOf(50L)
        val convertedAmount = Money(BigDecimal.valueOf(35162L), "CLP")
        every { moneyConverter.convert(Money(transferAmount, "CLP"), "USD") } returns convertedAmount

        val transferDetail = transferCommandImpl.transferMoney(destinationAccount, transferAmount, "CLP")

        assertThat(transferDetail.balance.amount).isEqualTo(BigDecimal.valueOf(45162L))
    }

}
