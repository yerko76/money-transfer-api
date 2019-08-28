package com.yerko.domain.account.command

import com.yerko.domain.moneytransfer.Money
import java.util.*

data class CreateAccount(val balance: Money, val customerId: UUID)
