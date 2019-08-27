package com.yerko.domain.moneytransfer.validator

import com.yerko.domain.moneytransfer.Money
import com.yerko.domain.moneytransfer.validator.exception.MoneyTransferValidationException
import java.math.BigDecimal

class MoneyTransferValidatorImpl : MoneyTransferValidator {
    override fun validate(currentBalance: Money, transferAmount: Money) {
        validateTransferAmount(transferAmount.amount)
        validateCurrency(currentBalance.currency, transferAmount.currency)
        validateBalance( currentBalance, transferAmount)
    }

    private fun validateTransferAmount(amount: BigDecimal) {
        if (amount <= BigDecimal.ZERO){
            throw MoneyTransferValidationException("Invalid transfer amount")
        }
    }

    private fun validateCurrency(currency:String, transferCurrency: String){
        if(currency != transferCurrency){
            throw MoneyTransferValidationException("Currency from origin account and transferAmount amount are different")
        }
    }

    private fun validateBalance(balance: Money, transferAmount: Money) {
        if(balance.amount < transferAmount.amount){
            throw MoneyTransferValidationException("Account does not have enough balance for transferAmount operation")
        }
    }

}
