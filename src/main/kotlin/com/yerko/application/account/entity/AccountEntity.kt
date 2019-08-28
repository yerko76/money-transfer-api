package com.yerko.application.account.entity

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.joda.time.DateTime
import java.math.BigDecimal
import java.util.*

object AccountEntity: Table("account"){
    val accountId: Column<UUID> = uuid("account_id").primaryKey()
    val balance: Column<BigDecimal> = decimal("balance",15, 6)
    val currency: Column<String> = varchar("currency", 3)
    val customerId: Column<UUID> = uuid("customer_id")
    val createdAt: Column<DateTime> = datetime("created_at")
    val active: Column<Boolean> = bool("active")
}

data class AccountDto(
    val accountId:UUID,
    val balance: BigDecimal,
    val currency: String,
    val customerId: UUID,
    val active: Boolean)
