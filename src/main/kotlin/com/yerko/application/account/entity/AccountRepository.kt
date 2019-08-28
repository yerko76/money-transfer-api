package com.yerko.application.account.entity

import com.yerko.domain.account.query.AccountQuery
import java.util.*

interface AccountWriteRepository {
    suspend fun save(account: AccountDto): UUID
}

interface AccountReadRepository: AccountQuery {

}
