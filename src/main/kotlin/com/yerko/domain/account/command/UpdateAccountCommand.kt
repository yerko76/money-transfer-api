package com.yerko.domain.account.command

import java.util.*

interface UpdateAccountCommand {
    fun update(updateAccountCommand: UpdateAccountCommand) : UUID
}
