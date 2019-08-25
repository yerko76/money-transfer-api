package com.yerko.domain.moneytransfer.command

import com.yerko.domain.moneytransfer.MoneyTransferDetail
import com.yerko.domain.moneytransfer.CreateMoneyTransfer

interface MoneyTransferCommand {
    fun transferAmount(createTransfer: CreateMoneyTransfer) : MoneyTransferDetail
}
