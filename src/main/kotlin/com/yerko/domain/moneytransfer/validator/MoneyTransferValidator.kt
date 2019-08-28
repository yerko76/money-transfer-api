package com.yerko.domain.moneytransfer.validator

import com.yerko.domain.moneytransfer.Money
import java.math.BigDecimal

interface MoneyTransferValidator {
    fun validate(currentBalance: Money, transferAmount: BigDecimal)
}
