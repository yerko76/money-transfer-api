package domain.moneytransfer.validator

import domain.moneytransfer.Money

interface IMoneyTransferValidator{
    fun validate(currentBalance: Money, transferAmount: Money)
}
