package com.yerko.application.account.command

import com.yerko.application.account.entity.AccountDto
import com.yerko.application.account.entity.AccountWriteRepository
import com.yerko.application.account.entity.MoneyDto
import com.yerko.domain.account.command.UpdateAccount
import com.yerko.domain.account.command.UpdateAccountCommand
import kotlinx.coroutines.runBlocking
import java.util.*

class UpdateAccountCommandHandler(private val accountWriteRepository: AccountWriteRepository) : UpdateAccountCommand{

    override fun update(updateAccount: UpdateAccount): UUID {
        val account = AccountDto(
            updateAccount.accountId,
            MoneyDto(updateAccount.balance.amount, updateAccount.balance.currency),
            null,
            null
        )
        return runBlocking { accountWriteRepository.update(account) }
    }

}

