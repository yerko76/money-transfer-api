package com.yerko.infrastructure.persistance

import com.yerko.application.moneytransfer.entity.MoneyTransactionEntity
import com.yerko.application.moneytransfer.entity.MoneyTransferDto
import com.yerko.application.moneytransfer.entity.MoneyTransferWriteRepository
import com.yerko.infrastructure.configuration.DatabaseFactory
import org.jetbrains.exposed.sql.insert
import org.slf4j.LoggerFactory

class MoneyTransferWriteRepositoryImpl : MoneyTransferWriteRepository {
    private val log = LoggerFactory.getLogger(MoneyTransferWriteRepositoryImpl::class.java)

    override suspend fun save(moneyTransferDto: MoneyTransferDto) : Int? = DatabaseFactory.dbQuery {
        try {
             MoneyTransactionEntity.insert {
                it[fromAccountId] = moneyTransferDto.fromAccount
                it[toAccountId] = moneyTransferDto.toAccount
                it[amount] = moneyTransferDto.transferredAmount.amount
                it[currency] = moneyTransferDto.transferredAmount.currency
            } get (MoneyTransactionEntity.id)

        } catch (e: Exception) {
            log.error(
                "Unable to transfer amount from accountId: {} due to {}",
                moneyTransferDto.fromAccount,
                e.message,
                e
            )
            throw PersistanceException("Unable to transfer amount from account  ${moneyTransferDto.fromAccount}")
        }
    }

}
