package com.yerko.application.account.query

import com.yerko.application.account.entity.AccountEntity
import com.yerko.application.account.entity.AccountReadRepository
import java.util.*

class AccountQuery(private val accountReadRepository: AccountReadRepository): AccountReadRepository {
    override fun findById(id: UUID): AccountEntity? {
        return accountReadRepository.findById(id)
    }

    override fun findByCustomerId(customerId: String): AccountEntity? {
        return accountReadRepository.findByCustomerId(customerId)
    }

}
