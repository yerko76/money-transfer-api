package com.yerko.application.rest.account

import com.yerko.domain.moneytransfer.Money
import java.util.*

data class AccountInformationResponse(val accountId: UUID, val balance: Money, val customerId: String)
data class AccountCreatedResponse(val accountId: UUID)
