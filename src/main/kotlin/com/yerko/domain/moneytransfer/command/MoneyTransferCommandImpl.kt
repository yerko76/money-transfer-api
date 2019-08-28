package com.yerko.domain.moneytransfer.command

import com.yerko.domain.account.Account
import com.yerko.domain.exchangerate.MoneyConverter
import com.yerko.domain.moneytransfer.CreateMoneyTransfer
import com.yerko.domain.moneytransfer.Money
import com.yerko.domain.moneytransfer.MoneyTransferDetail
import com.yerko.domain.moneytransfer.validator.MoneyTransferValidator
import java.math.BigDecimal

class MoneyTransferCommandImpl(private val moneyTransferValidator: MoneyTransferValidator,
                               private val moneyConverter: MoneyConverter) : MoneyTransferCommand {

    override fun transferAmount(createTransfer: CreateMoneyTransfer): MoneyTransferDetail {
        val originAccount = createTransfer.originAccount
        val destinationAccount = createTransfer.destinationAccount
        val transferAmount = createTransfer.transferAmount
        this.moneyTransferValidator.validate(originAccount.balance, transferAmount)
        return executeMoneyTransfer(originAccount, transferAmount, destinationAccount)
    }

    private fun executeMoneyTransfer(originAccount: Account, transferAmount: BigDecimal, destinationAccount: Account): MoneyTransferDetail {
        val origin = withDrawMoney(originAccount, transferAmount)
        val destination = transferMoney(destinationAccount, transferAmount, originAccount.balance.currency)
        return MoneyTransferDetail(origin, destination)
    }

    private fun withDrawMoney(originAccount: Account, transferAmount: BigDecimal) : Account {
        val money = originAccount.balance.amount.minus(transferAmount)
        return Account(
            originAccount.accountId,
            Money(money, originAccount.balance.currency)
        )
    }

    private fun transferMoney(destinationAccount: Account, transferAmount: BigDecimal, originAccountCurrency: String) : Account {
        if(destinationAccount.balance.currency != originAccountCurrency){
            val convertedAmount = moneyConverter
                .convert(Money(transferAmount, originAccountCurrency), destinationAccount.balance.currency)
            val money = destinationAccount.balance.amount.plus(convertedAmount.amount)
            return Account(destinationAccount.accountId, Money(money, destinationAccount.balance.currency))
        }
        return Account(destinationAccount.accountId,
            Money(destinationAccount.balance.amount.plus(transferAmount), destinationAccount.balance.currency)
        )

    }
}
