package com.yerko.application.account.entity

import java.util.*

interface AccountWriteRepository {
    suspend fun save(account: AccountDto) : UUID
}

interface AccountReadRepository {
    suspend fun findById(id: UUID) : AccountDto?
    suspend fun findByCustomerId(customerId: UUID): AccountDto?
}
