package com.yerko.domain.moneytransfer.command

import com.yerko.domain.moneytransfer.CreateMoneyTransfer
import com.yerko.domain.moneytransfer.MoneyTransferDetail

interface MoneyTransferCommand {
    fun transferAmount(createTransfer: CreateMoneyTransfer) : MoneyTransferDetail
}
