package com.yerko.domain.account.query

import com.yerko.application.account.entity.AccountDto
import java.util.*

interface AccountQuery {
    suspend fun findById(accountId:UUID): AccountDto?
    suspend fun findByIdCustomerId(customerId:UUID): AccountDto?
}
