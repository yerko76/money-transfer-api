package com.yerko.domain.account.command

import com.yerko.domain.moneytransfer.Money
import java.time.LocalDateTime
import java.util.*

data class CreateAccount(val accountId: UUID, val balance: Money, val customerId: String, val createdAt:LocalDateTime)