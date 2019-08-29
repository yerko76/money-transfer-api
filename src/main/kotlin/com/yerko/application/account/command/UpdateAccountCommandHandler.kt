package com.yerko.application.account.command

import com.yerko.application.account.entity.AccountDto
import com.yerko.application.account.entity.AccountWriteRepository
import com.yerko.application.account.entity.MoneyDto
import com.yerko.domain.account.command.UpdateAccount
import com.yerko.domain.account.command.UpdateAccountCommand
import java.util.*

class UpdateAccountCommandHandler(private val accountWriteRepository: AccountWriteRepository) : UpdateAccountCommand{

    override suspend fun update(updateAccount: UpdateAccount): UUID {
        val account = AccountDto(
            updateAccount.accountId,
            MoneyDto(updateAccount.balance.amount, updateAccount.balance.currency),
            UUID.randomUUID(),
            null
        )
        return accountWriteRepository.update(account)
    }

}

