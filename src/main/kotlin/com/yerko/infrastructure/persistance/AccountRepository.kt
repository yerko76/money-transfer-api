package com.yerko.infrastructure.persistance

import com.yerko.application.account.entity.AccountDto
import com.yerko.application.account.entity.AccountReadRepository
import com.yerko.application.account.entity.AccountWriteRepository
import com.yerko.application.account.entity.AccountsEntity
import com.yerko.infrastructure.configuration.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import java.util.*

class AccountRepository : AccountWriteRepository, AccountReadRepository {
    override suspend fun save(account: AccountDto): UUID {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun findById(id: UUID): AccountDto? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun findByCustomerId(customerId: UUID): AccountDto? = dbQuery {
        AccountsEntity
            .select{ (AccountsEntity.customerId eq customerId)}
            .mapNotNull { toAccountDto(it) }}
        .singleOrNull()

    private fun toAccountDto(row: ResultRow): AccountDto =
         AccountDto(
            accountId = row[AccountsEntity.accountId],
            balance = row[AccountsEntity.balance],
            currency = row[AccountsEntity.currency],
            customerId = row[AccountsEntity.customerId],
            active = row[AccountsEntity.active]
        )

}
