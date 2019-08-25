package domain.moneytransfer.command

import domain.account.Account
import domain.moneytransfer.Money
import domain.moneytransfer.TransferDetail
import domain.moneytransfer.validator.MoneyTransferValidator
import java.util.*

class TransferMoneyCommandImpl(private val moneyTransferValidator:MoneyTransferValidator) : TransferMoneyCommand {

    override fun transferAmount(originAccount:Account, destinationAccount:Account, transferAmount:Money): TransferDetail {
        this.moneyTransferValidator.validate(originAccount.balance, transferAmount)
        val origin = withDrawMoney(originAccount, transferAmount)
        val destination = depositMoney(destinationAccount, transferAmount)
        return TransferDetail(UUID.randomUUID(), origin, destination)
    }

    private fun withDrawMoney(originAccount:Account, transferAmount:Money) :Account {
        val money = originAccount.balance.amount - transferAmount.amount
        return Account(originAccount.accountId, Money(money, originAccount.balance.currency))
    }

    private fun depositMoney(destinationAccount: Account, transferAmount: Money) :Account {
        val money = destinationAccount.balance.amount + transferAmount.amount
        return Account(destinationAccount.accountId, Money(money, destinationAccount.balance.currency))
    }
}
