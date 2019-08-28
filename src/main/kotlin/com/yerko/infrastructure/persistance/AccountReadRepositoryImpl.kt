package com.yerko.infrastructure.persistance

import com.yerko.application.account.entity.AccountDto
import com.yerko.application.account.entity.AccountEntity
import com.yerko.application.account.entity.AccountReadRepository
import com.yerko.application.account.entity.MoneyDto
import com.yerko.infrastructure.configuration.DatabaseFactory
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import java.util.*

class AccountReadRepositoryImpl: AccountReadRepository {
    override suspend fun findByIdCustomerId(customerId: UUID): AccountDto? = DatabaseFactory.dbQuery {
        AccountEntity
            .select { (AccountEntity.customerId eq customerId and (AccountEntity.active eq true)) }
            .mapNotNull { toAccountDto(it) }
    }.singleOrNull()

    override suspend fun findById(accountId: UUID): AccountDto? = DatabaseFactory.dbQuery {
        AccountEntity
            .select { (AccountEntity.accountId eq accountId and (AccountEntity.active eq true)) }
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
