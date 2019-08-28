package com.yerko.domain.moneytransfer.command

import com.yerko.domain.account.Account
import com.yerko.domain.exchangerate.MoneyConverter
import com.yerko.domain.moneytransfer.Money
import java.math.BigDecimal

interface WithDrawMoneyCommand{
    fun withDrawMoney(originAccount: Account, amount: BigDecimal) : Account
}

interface TransferMoneyCommand{
    fun transferMoney(destinationAccount: Account, transferAmount: BigDecimal, originAccountCurrency: String) : Account
}

class WithdrawMoneyCommandImpl : WithDrawMoneyCommand{
    override fun withDrawMoney(originAccount: Account, amount: BigDecimal) : Account {
        val money = originAccount.balance.amount.minus(amount)
        return Account(
            originAccount.accountId,
            Money(money, originAccount.balance.currency)
        )
    }
}

class TransferMoneyCommandImpl(private val moneyConverter: MoneyConverter): TransferMoneyCommand{
    override fun transferMoney(destinationAccount: Account, transferAmount: BigDecimal, originAccountCurrency: String) : Account {
        if(originAccountCurrency != destinationAccount.balance.currency){
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
