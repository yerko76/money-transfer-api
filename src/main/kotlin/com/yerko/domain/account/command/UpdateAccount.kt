package com.yerko.domain.account.command

import com.yerko.domain.moneytransfer.Money
import java.util.*

data class UpdateAccount(val accountId: UUID, val balance: Money)
