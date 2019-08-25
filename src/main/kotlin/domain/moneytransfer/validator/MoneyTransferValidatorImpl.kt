package domain.moneytransfer.validator

import domain.moneytransfer.Money
import domain.moneytransfer.validator.exception.DifferentCurrencyException
import domain.moneytransfer.validator.exception.InsufficientBalanceException

class MoneyTransferValidatorImpl : MoneyTransferValidator {
    override fun validate(currentBalance: Money, transferAmount: Money) {
        validateCurrency(currentBalance.currency, transferAmount.currency)
        validateBalance( currentBalance, transferAmount)
    }

    private fun validateCurrency(currency:String, transferCurrency: String){
        if(currency != transferCurrency){
            throw DifferentCurrencyException("Currency from origin account and transferAmount amount are different")
        }
    }

    private fun validateBalance(balance: Money, transferAmount: Money) {
        if(balance.amount < transferAmount.amount){
            throw InsufficientBalanceException("Account does not have enough balance for transferAmount operation")
        }
    }

}
