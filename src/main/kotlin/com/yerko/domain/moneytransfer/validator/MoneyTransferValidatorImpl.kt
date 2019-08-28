package com.yerko.domain.moneytransfer.validator

import com.yerko.domain.moneytransfer.Money
import com.yerko.domain.moneytransfer.validator.exception.MoneyTransferValidationException
import java.math.BigDecimal

class MoneyTransferValidatorImpl : MoneyTransferValidator {
    override fun validate(currentBalance: Money, transferAmount: BigDecimal) {
        validateTransferAmount(transferAmount)
        validateBalance( currentBalance.amount, transferAmount)
    }

    private fun validateTransferAmount(amount: BigDecimal) {
        if (amount <= BigDecimal.ZERO){
            throw MoneyTransferValidationException("Invalid transfer amount")
        }
    }

    private fun validateBalance(balance: BigDecimal, transferAmount: BigDecimal) {
        if(balance < transferAmount){
            throw MoneyTransferValidationException("Account does not have enough balance for transferAmount operation")
        }
    }

}
