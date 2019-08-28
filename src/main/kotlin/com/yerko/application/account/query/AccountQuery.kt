package com.yerko.application.account.query

import com.yerko.application.account.entity.AccountDto
import com.yerko.application.account.entity.AccountReadRepository
import java.util.*

class AccountQuery(private val accountReadRepository: AccountReadRepository): AccountReadRepository {
    override suspend fun findByCustomerId(customerId: UUID): AccountDto? {
        return accountReadRepository.findByCustomerId(customerId)
    }

    override suspend fun findById(id: UUID): AccountDto? {
        return accountReadRepository.findById(id)
    }
}
