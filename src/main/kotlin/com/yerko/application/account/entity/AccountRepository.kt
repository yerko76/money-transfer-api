package com.yerko.application.account.entity

import java.util.*

interface AccountWriteRepository {
    fun save(account: AccountEntity) : UUID
}

interface AccountReadRepository {
    fun findById(id: UUID) : AccountEntity
    fun findByCustomerId(customerId: String): AccountEntity
}
