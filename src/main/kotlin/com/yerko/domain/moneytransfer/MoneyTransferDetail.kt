package com.yerko.domain.moneytransfer

import com.yerko.domain.account.Account


data class MoneyTransferDetail(val originAccount: Account, val destinationAccount: Account)
