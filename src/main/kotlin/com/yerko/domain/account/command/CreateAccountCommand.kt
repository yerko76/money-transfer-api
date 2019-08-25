package com.yerko.domain.account.command

import java.util.*

interface CreateAccountCommand {
    fun create(createAccount : CreateAccount) : UUID
}
