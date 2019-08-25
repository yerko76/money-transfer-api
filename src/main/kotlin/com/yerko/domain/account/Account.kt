package com.yerko.domain.account

import com.yerko.domain.moneytransfer.Money
import java.util.*

data class Account(val accountId:UUID, val balance: Money)
