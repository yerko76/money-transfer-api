package com.yerko.application.rest.account

import com.yerko.domain.moneytransfer.Money
import java.util.*

data class CreateAccountRequest(val balance: Money, val customerId: UUID)
