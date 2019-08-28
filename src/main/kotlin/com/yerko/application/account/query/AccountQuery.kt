package com.yerko.application.account.query

import com.yerko.application.account.entity.AccountDto
import com.yerko.application.account.entity.AccountReadRepository
import java.util.*
import javax.security.auth.login.AccountNotFoundException

class AccountQuery(private val accountReadRepository: AccountReadRepository): AccountReadRepository {

    override suspend fun findById(id: UUID): AccountDto {
        return accountReadRepository.findById(id)
            ?: throw AccountNotFoundException("Account not found for id: $id")
    }
}
