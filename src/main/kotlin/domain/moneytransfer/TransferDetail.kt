package domain.moneytransfer

import domain.account.Account


data class TransferDetail(val originAccount: Account, val destinationAccount: Account)
