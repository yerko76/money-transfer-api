package com.yerko.domain.account.command

import java.util.*

interface UpdateAccountCommand {
    suspend fun update(updateAccount: UpdateAccount) : UUID
}
