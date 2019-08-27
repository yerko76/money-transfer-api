package com.yerko.domain.account.query

import com.yerko.application.account.entity.AccountEntity
import java.util.*

interface AccountQuery {
    fun findById(accountId:UUID): AccountEntity?
}
