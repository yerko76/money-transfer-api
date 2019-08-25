package com.yerko.domain.moneytransfer.command

import com.yerko.domain.account.Account
import com.yerko.domain.exchangerate.MoneyConverter
import com.yerko.domain.moneytransfer.CreateMoneyTransfer
import com.yerko.domain.moneytransfer.Money
import com.yerko.domain.moneytransfer.MoneyTransferDetail
import com.yerko.domain.moneytransfer.validator.MoneyTransferValidator
import java.util.*

class MoneyTransferCommandImpl(private val moneyTransferValidator: MoneyTransferValidator,
                               private val moneyConverter: MoneyConverter) : MoneyTransferCommand {

    override fun transferAmount(createTransfer: CreateMoneyTransfer): MoneyTransferDetail {
        val originAccount = createTransfer.originAccount
        val destinationAccount = createTransfer.destinationAccount
        val transferAmount = createTransfer.transferAmount
        this.moneyTransferValidator.validate(originAccount.balance, transferAmount)
        return executeMoneyTransfer(originAccount, transferAmount, destinationAccount)
    }

    private fun executeMoneyTransfer(originAccount: Account, transferAmount: Money, destinationAccount: Account): MoneyTransferDetail {
        val origin = withDrawMoney(originAccount, transferAmount)
        val destination = transferMoney(destinationAccount, transferAmount)
        return MoneyTransferDetail(UUID.randomUUID(), origin, destination)
    }

    private fun withDrawMoney(originAccount: Account, transferAmount: Money) : Account {
        val money = originAccount.balance.amount.minus(transferAmount.amount)
        return Account(
            originAccount.accountId,
            Money(money, originAccount.balance.currency)
        )
    }

    private fun transferMoney(destinationAccount: Account, transferAmount: Money) : Account {
        val convertedAmount = moneyConverter.convert(transferAmount, destinationAccount.balance.currency)
        val money = destinationAccount.balance.amount.plus(convertedAmount.amount)
        return Account(
            destinationAccount.accountId,
            Money(money, destinationAccount.balance.currency)
        )
    }
}
