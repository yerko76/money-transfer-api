package domain.moneytransfer

import domain.account.Account
import domain.moneytransfer.validator.IMoneyTransferValidator

class MoneyTransfer(moneyTransferValidator: IMoneyTransferValidator) {
    private val moneyTransferValidator : IMoneyTransferValidator = moneyTransferValidator

    fun transfer(originAccount: Account, destinationAccount: Account, transferAmount: Money) : TransferDetail{
        this.moneyTransferValidator.validate(originAccount.balance, transferAmount)
        return TransferDetail(originAccount, destinationAccount)
    }



}
