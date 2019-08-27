package com.yerko.domain.account.command

import com.yerko.domain.moneytransfer.Money

data class CreateAccount(val balance: Money, val customerId: String)
