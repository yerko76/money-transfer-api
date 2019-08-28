package com.yerko.application.account.entity

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import java.math.BigDecimal
import java.util.*

object AccountEntity: Table("account"){
    val accountId: Column<UUID> = uuid("account_id").primaryKey()
    val balance: Column<BigDecimal> = decimal("balance",15, 6)
    val currency: Column<String> = varchar("currency", 3)
    val customerId: Column<UUID> = uuid("customer_id")
    val active: Column<Boolean> = bool("active")
}

data class AccountDto @JsonCreator constructor(
    @JsonProperty("accountId") val accountId:UUID,
    @JsonProperty("balance") val moneyDto: MoneyDto,
    @JsonProperty("customerId") val customerId: UUID?,
    @JsonProperty("active") val active: Boolean?
)

data class MoneyDto @JsonCreator constructor(
    @JsonProperty("amount") val amount: BigDecimal,
    @JsonProperty("currency") val currency: String
)
