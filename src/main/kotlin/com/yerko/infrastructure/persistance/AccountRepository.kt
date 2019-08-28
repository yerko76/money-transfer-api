package com.yerko.infrastructure.persistance

import com.yerko.application.account.command.UnableToCreateAccountException
import com.yerko.application.account.entity.*
import com.yerko.infrastructure.configuration.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.slf4j.LoggerFactory
import java.util.*

class AccountRepository : AccountWriteRepository, AccountReadRepository {
    private val log = LoggerFactory.getLogger(AccountRepository::class.java)

    override suspend fun save(account: AccountDto): UUID = dbQuery {
        try{
            AccountEntity.insert {
                    it[accountId] = account.accountId
                    it[balance] = account.moneyDto.amount
                    it[currency] = account.moneyDto.currency
                    it[customerId] = account.customerId
                    it[active] = true }
            return@dbQuery UUID.randomUUID()
        } catch (e: Exception){
            log.error("Unable to create account for customer: {} due to {}", account.customerId, e.message)
            throw UnableToCreateAccountException("Unable to create account for customer: ${account.customerId}")
        }

    }

    override suspend fun findById(id: UUID): AccountDto? = dbQuery {
        AccountEntity
            .select{ (AccountEntity.accountId eq id and (AccountEntity.active eq true))}
            .mapNotNull { toAccountDto(it) }
    }.singleOrNull()

    private fun toAccountDto(row: ResultRow): AccountDto =
         AccountDto(
             accountId = row[AccountEntity.accountId],
             moneyDto = MoneyDto(row[AccountEntity.balance], row[AccountEntity.currency]),
             customerId = row[AccountEntity.customerId],
             active = row[AccountEntity.active]
        )

}
