package com.yerko.infrastructure.persistance

import com.yerko.application.account.entity.AccountDto
import com.yerko.application.account.entity.AccountEntity
import com.yerko.application.account.entity.AccountReadRepository
import com.yerko.application.account.entity.AccountWriteRepository
import com.yerko.infrastructure.configuration.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import java.util.*

class AccountRepository : AccountWriteRepository, AccountReadRepository {
    override suspend fun save(account: AccountDto): UUID {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun findById(id: UUID): AccountDto? = dbQuery {
        AccountEntity
            .select{ (AccountEntity.accountId eq id and (AccountEntity.active eq true))}
            .mapNotNull { toAccountDto(it) }
    }.singleOrNull()

    private fun toAccountDto(row: ResultRow): AccountDto =
         AccountDto(
            accountId = row[AccountEntity.accountId],
            balance = row[AccountEntity.balance],
            currency = row[AccountEntity.currency],
            customerId = row[AccountEntity.customerId],
            active = row[AccountEntity.active]
        )

}
