package com.yerko.application.rest.moneytransfer

import java.math.BigDecimal
import java.util.*

data class CreateMoneyTransferRequest constructor(val toAccountId: UUID, val amount: BigDecimal){
    lateinit var fromAccountId: UUID

    constructor(fromAccountId: UUID, toAccountId: UUID, amount: BigDecimal) : this(toAccountId, amount){
        this.fromAccountId = fromAccountId
    }
}
