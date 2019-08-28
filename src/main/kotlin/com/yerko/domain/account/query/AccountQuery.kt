package com.yerko.domain.account.query

import com.yerko.application.account.entity.AccountDto
import java.util.*

interface AccountQuery {
    fun findById(accountId:UUID): AccountDto?
}
