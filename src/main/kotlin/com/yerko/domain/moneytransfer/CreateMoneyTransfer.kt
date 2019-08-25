package com.yerko.domain.moneytransfer

import com.yerko.domain.account.Account


data class CreateMoneyTransfer(val originAccount: Account, val destinationAccount: Account, val transferAmount: Money)
