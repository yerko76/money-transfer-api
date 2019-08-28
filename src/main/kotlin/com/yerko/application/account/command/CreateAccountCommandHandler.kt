package com.yerko.application.account.command

import com.yerko.application.account.entity.AccountDto
import com.yerko.application.account.entity.AccountWriteRepository
import com.yerko.application.rest.account.AccountResource
import com.yerko.domain.account.command.CreateAccount
import com.yerko.domain.account.command.CreateAccountCommand
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import java.util.*

class CreateAccountCommandHandler(private val accountRepository: AccountWriteRepository) : CreateAccountCommand {
    private val log = LoggerFactory.getLogger(AccountResource::class.java)

    override fun create(createAccount: CreateAccount): UUID {
        val account = AccountDto(
            UUID.randomUUID(),
            createAccount.balance.amount,
            createAccount.balance.currency,
            createAccount.customerId,
            true
        )
        try {
            return runBlocking { accountRepository.save(account) }
        }
        catch (e: Exception) {
            log.error("Account already exists for customer: {}", account.customerId, e)
            throw AccountAlreadyExistException("Account already exists for customer: ${account.customerId}")
        }

    }

}
