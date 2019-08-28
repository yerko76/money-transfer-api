package com.yerko.application.moneytransfer.entity

import com.yerko.domain.moneytransfer.Money
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import java.math.BigDecimal
import java.util.*


object MoneyTransactionEntity: Table("money_transaction"){
    val id: Column<Int> = integer("id").autoIncrement().primaryKey()
    val fromAccountId: Column<UUID> = uuid("from_account_id")
    val toAccountId: Column<UUID> = uuid("to_account_id")
    val amount: Column<BigDecimal> = decimal("amount",15, 6)
    val currency: Column<String> = varchar("currency", 3)
}

data class MoneyTransferDto(val id:Int,
                            val fromAccount: UUID,
                            val toAccount: UUID,
                            val transferredAmount: Money)
