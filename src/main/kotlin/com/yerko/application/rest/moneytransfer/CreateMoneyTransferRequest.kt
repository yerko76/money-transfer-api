package com.yerko.application.rest.moneytransfer

import java.math.BigDecimal
import java.util.*

data class CreateMoneyTransferRequest(val fromAccountId: UUID, val toAccountId: UUID, val amount: BigDecimal)
