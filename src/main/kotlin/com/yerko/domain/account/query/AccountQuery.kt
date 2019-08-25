package com.yerko.domain.account.query

import com.yerko.domain.account.Account
import java.util.*

interface AccountQuery {
    fun findById(accountId:UUID): Account?
}
