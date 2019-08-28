package com.yerko.application.moneytransfer.command

import com.yerko.application.account.query.AccountQueryHandler
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach

class TransferMoneyCommandHandlerTest {
    private lateinit var moneyCommandHandler: TransferMoneyCommandHandler
    private lateinit var accountQueryHandler: AccountQueryHandler

    @BeforeEach
    fun setUp() {
        accountQueryHandler = mockk()
        moneyCommandHandler = TransferMoneyCommandHandler(accountQueryHandler)
    }
}
