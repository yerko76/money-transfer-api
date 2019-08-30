package com.yerko.domain.moneytransfer.command

import com.yerko.domain.account.Account
import com.yerko.domain.moneytransfer.Money
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.util.*

class WithDrawCommandImplTest {
    private lateinit var withDrawCommand: WithdrawMoneyCommandImpl

    @BeforeEach
    fun setUp() {
        withDrawCommand = WithdrawMoneyCommandImpl()
    }

    @Test
    fun `Should withdraw money from account`() {
        val originAccount = Account(
            UUID.randomUUID(),
            Money(BigDecimal.valueOf(100L), "USD")
        )
        val amount = BigDecimal.valueOf(50L)

        val account = withDrawCommand.withDrawMoney(originAccount, amount)

        assertThat(account.amount).isEqualTo(BigDecimal.valueOf(50L))
    }

}
