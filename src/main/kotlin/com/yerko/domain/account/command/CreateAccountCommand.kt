package com.yerko.domain.account.command

import java.util.*

interface CreateAccountCommand {
    suspend fun create(createAccount : CreateAccount) : UUID
}
