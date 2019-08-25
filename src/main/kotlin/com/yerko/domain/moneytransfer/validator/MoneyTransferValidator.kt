package com.yerko.domain.moneytransfer.validator

import com.yerko.domain.moneytransfer.Money

interface MoneyTransferValidator {
    fun validate(currentBalance: Money, transferAmount: Money)
}
