package com.yerko.domain.moneytransfer.command

import com.yerko.domain.account.Account
import com.yerko.domain.exchangerate.MoneyConverter
import com.yerko.domain.moneytransfer.Money
import java.math.BigDecimal

interface WithDrawMoneyCommand{
    fun withDrawMoney(originAccount: Account, amount: BigDecimal) : Money
}

interface TransferMoneyCommand{
    fun transferMoney(destinationAccount: Account, transferAmount: BigDecimal, originAccountCurrency: String) : Money
}

class WithdrawMoneyCommandImpl : WithDrawMoneyCommand{
    override fun withDrawMoney(originAccount: Account, amount: BigDecimal) : Money {
        val money = originAccount.balance.amount.minus(amount)
        return Money(money, originAccount.balance.currency)
    }
}

class TransferMoneyCommandImpl(private val moneyConverter: MoneyConverter): TransferMoneyCommand{
    override fun transferMoney(destinationAccount: Account, transferAmount: BigDecimal, originAccountCurrency: String) : Money {
        if(originAccountCurrency != destinationAccount.balance.currency){
            val convertedAmount = moneyConverter.convert(Money(transferAmount, originAccountCurrency), destinationAccount.balance.currency)
            val money = destinationAccount.balance.amount.plus(convertedAmount.amount)
            return Money(money, destinationAccount.balance.currency)
        }
        return Money(destinationAccount.balance.amount.plus(transferAmount), destinationAccount.balance.currency)
    }
}
