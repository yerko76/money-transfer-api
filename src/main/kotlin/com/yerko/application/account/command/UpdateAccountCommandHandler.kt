package com.yerko.application.account.command

import com.yerko.application.account.entity.AccountReadRepository
import com.yerko.application.account.entity.AccountWriteRepository
import com.yerko.domain.account.command.UpdateAccountCommand
import java.util.*

class UpdateAccountCommandHandler(private val accountWriteRepository: AccountWriteRepository,
                                  private val accountReadRepository: AccountReadRepository) : UpdateAccountCommand{
    override fun update(updateAccountCommand: UpdateAccountCommand): UUID {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

