package com.yerko.application.rest.moneytransfer

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal
import java.util.*

data class CreateMoneyTransferRequest @JsonCreator constructor(
    @JsonProperty("originId") val fromAccountId: UUID,
    @JsonProperty("destinationId") val toAccountId: UUID,
    @JsonProperty("amount") val amount: BigDecimal)


data class TestRequest(val accountId:UUID, val someId:UUID, val amount:BigDecimal)
