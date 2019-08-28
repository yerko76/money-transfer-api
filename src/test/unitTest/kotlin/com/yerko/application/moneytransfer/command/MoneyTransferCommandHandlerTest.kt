package com.yerko.application.moneytransfer.command

import com.yerko.application.account.command.CreateAccountCommandHandler
import com.yerko.application.account.entity.AccountReadRepository
import com.yerko.application.account.query.AccountQueryHandler
import com.yerko.domain.exchangerate.query.ExchangeRateQuery
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach

class MoneyTransferCommandHandlerTest {
    private lateinit var commandHandler: MoneyTransferCommandHandlerTest
    private lateinit var accountQueryHandler: AccountQueryHandler

    @BeforeEach
    fun setUp() {
        accountQueryHandler = mockk()
        commandHandler = MoneyTransferCommandHandlerTest(accountQueryHandler, accountReadRepository)
    }
}
