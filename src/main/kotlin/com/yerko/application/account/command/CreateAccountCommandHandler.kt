package com.yerko.application.account.command

import com.yerko.application.account.entity.AccountDto
import com.yerko.application.account.entity.AccountWriteRepository
import com.yerko.application.account.entity.MoneyDto
import com.yerko.domain.account.command.CreateAccount
import com.yerko.domain.account.command.CreateAccountCommand
import kotlinx.coroutines.runBlocking
import java.util.*

class CreateAccountCommandHandler(private val accountRepository: AccountWriteRepository) : CreateAccountCommand {

    override fun create(createAccount: CreateAccount): UUID {
        val account = AccountDto(
            UUID.randomUUID(),
            MoneyDto(createAccount.balance.amount, createAccount.balance.currency),
            createAccount.customerId,
            true
        )
            return runBlocking { accountRepository.save(account) }
        }
}


