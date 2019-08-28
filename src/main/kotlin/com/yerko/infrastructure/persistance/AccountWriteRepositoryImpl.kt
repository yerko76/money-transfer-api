package com.yerko.infrastructure.persistance

import com.yerko.application.account.command.UnableToCreateAccountException
import com.yerko.application.account.entity.AccountDto
import com.yerko.application.account.entity.AccountEntity
import com.yerko.application.account.entity.AccountWriteRepository
import com.yerko.infrastructure.configuration.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.insert
import org.slf4j.LoggerFactory
import java.util.*

class AccountWriteRepositoryImpl : AccountWriteRepository {
    private val log = LoggerFactory.getLogger(AccountWriteRepositoryImpl::class.java)

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
}