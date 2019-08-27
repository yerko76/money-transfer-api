package com.yerko.application.account.entity

import com.yerko.domain.moneytransfer.Money
import java.time.LocalDateTime
import java.util.*

data class AccountEntity(val accountId:UUID, val balance: Money, val customerId:String, val createdAt: LocalDateTime)
