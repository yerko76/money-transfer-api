package command

import domain.account.Account
import domain.moneytransfer.Money
import domain.moneytransfer.TransferDetail

interface TransferMoneyCommand {
    fun transferAmount(originAccount: Account, destinationAccount: Account, transferAmount: Money) : TransferDetail
}
