package com.yerko.domain.moneytransfer

import com.yerko.domain.account.Account
import java.util.*


data class MoneyTransferDetail(val id:UUID, val originAccount: Account, val destinationAccount: Account)
