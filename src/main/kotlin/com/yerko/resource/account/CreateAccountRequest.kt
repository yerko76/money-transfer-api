package com.yerko.resource.account

import com.yerko.domain.moneytransfer.Money

data class CreateAccountRequest(val balance: Money, val customerId: String)
