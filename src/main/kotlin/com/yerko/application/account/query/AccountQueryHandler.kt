package com.yerko.application.account.query

import com.yerko.application.account.entity.AccountDto
import com.yerko.application.account.entity.AccountReadRepository
import java.util.*
import javax.security.auth.login.AccountNotFoundException

class AccountQueryHandler(private val accountReadRepository: AccountReadRepository) {

    suspend fun findById(accountId: UUID): AccountDto {
        return accountReadRepository.findById(accountId) ?: throw AccountNotFoundException("Account not found for id: $accountId")
    }
}
