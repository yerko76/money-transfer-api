package domain.moneytransfer.validator

import domain.moneytransfer.Money

interface MoneyTransferValidator {
    fun validate(currentBalance: Money, transferAmount: Money)
}
