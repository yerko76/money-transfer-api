package com.yerko.application.account.command

import com.yerko.application.account.entity.AccountDto
import com.yerko.application.account.entity.AccountReadRepository
import com.yerko.application.account.entity.AccountWriteRepository
import com.yerko.application.account.entity.MoneyDto
import com.yerko.domain.account.command.CreateAccount
import com.yerko.domain.account.command.CreateAccountCommand
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import java.util.*

class CreateAccountCommandHandler(private val accountWriteRepository: AccountWriteRepository,
                                  private val accountReadRepository: AccountReadRepository) : CreateAccountCommand {
    private val log = LoggerFactory.getLogger(CreateAccountCommandHandler::class.java)

    override fun create(createAccount: CreateAccount): UUID {
        val account = AccountDto(
            UUID.randomUUID(),
            MoneyDto(createAccount.balance.amount, createAccount.balance.currency),
            createAccount.customerId,
            true
        )

        return runBlocking {
            val accountFromDb = accountReadRepository.findByIdCustomerId(createAccount.customerId)
            if(accountFromDb != null){
                log.error("Unable to create account for customer: {} due to account already exists", account.customerId)
                throw UnableToCreateAccountException("Unable to create account for customer: ${account.customerId} due to account already exists")
            }

            accountWriteRepository.save(account)
        }
    }
}


