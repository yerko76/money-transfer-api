package com.yerko.application.rest.moneytransfer

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import java.math.BigDecimal
import java.util.*

@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class CreateMoneyTransferRequest constructor(val toAccountId: UUID, val amount: BigDecimal){
    @JsonIgnore
    lateinit var fromAccountId: UUID

    constructor(fromAccountId: UUID, toAccountId: UUID, amount: BigDecimal) : this(toAccountId, amount){
        this.fromAccountId = fromAccountId
    }
}
