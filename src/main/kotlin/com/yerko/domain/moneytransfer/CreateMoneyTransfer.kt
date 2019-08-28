package com.yerko.domain.moneytransfer

import com.yerko.domain.account.Account
import java.math.BigDecimal


data class CreateMoneyTransfer(val originAccount: Account, val destinationAccount: Account, val transferAmount: BigDecimal)
