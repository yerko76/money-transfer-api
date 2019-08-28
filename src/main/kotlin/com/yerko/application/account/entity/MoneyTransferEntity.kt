package com.yerko.application.account.entity

import com.yerko.domain.moneytransfer.Money
import java.util.*

class MoneyTransferDto(val id:Integer, val fromAccount: UUID, val toAccount: UUID, val transferedAmount: Money)
